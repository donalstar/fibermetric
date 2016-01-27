package com.guggiemedia.fibermetric.ui.main;


import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.guggiemedia.fibermetric.R;
import com.guggiemedia.fibermetric.lib.db.ContentFacade;
import com.guggiemedia.fibermetric.lib.db.DataBaseTable;
import com.guggiemedia.fibermetric.lib.db.InventoryStatusEnum;
import com.guggiemedia.fibermetric.lib.db.PartModel;
import com.guggiemedia.fibermetric.lib.db.PartTable;
import com.guggiemedia.fibermetric.lib.utility.AnalyticHelper;
import com.guggiemedia.fibermetric.ui.component.RecyclerViewEmptySupport;
import com.guggiemedia.fibermetric.ui.utility.ToastHelper;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by donal on 9/28/15.
 */
public class AddByBeaconFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final String FRAGMENT_TAG = "FRAGMENT_ADD_BY_BEACON";

    public static final String LOG_TAG = AddByBeaconFragment.class.getName();

    private AddByBeaconViewAdapter _adapter;
    private MainActivityListener _listener;
    private Handler _handler;
    private BluetoothAdapter _btAdapter;

    private TextView _scanningMessageText;

    private List<String> _scannedDevices;

    private final ContentFacade _contentFacade = new ContentFacade();

    private Cursor _cursor;

    public static final Long SCAN_DURATION = 3333L;

    public static final int LOADER_ID = 271921;

    // LoaderCallback
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.d(LOG_TAG, "onCreateLoader:" + id);

        DataBaseTable table = new PartTable();

        String[] projection = table.getDefaultProjection();
        String orderBy = table.getDefaultSortOrder();

        String selection = PartTable.Columns.STATUS + "=? and " + PartTable.Columns.BLE_ADDRESS + " <> ?";

        String[] selectionArgs = new String[]{
                InventoryStatusEnum.pickUp.toString(),
                "0"
        };

        return new CursorLoader(getActivity(), PartTable.CONTENT_URI, projection, selection, selectionArgs, orderBy);
    }

    // LoaderCallback
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor != null) {
            _cursor = cursor;

            scanLeDevice();
        }
    }

    // LoaderCallback
    public void onLoaderReset(Loader<Cursor> loader) {
        _adapter.setCursor(null);
    }

    public static AddByBeaconFragment newInstance() {
        AddByBeaconFragment fragment = new AddByBeaconFragment();

        return fragment;
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        _listener = (MainActivityListener) activity;
        _adapter = new AddByBeaconViewAdapter(activity);

        _scannedDevices = new ArrayList<>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        _handler = new Handler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_by_beacon, container, false);

        _scanningMessageText = (TextView) view.findViewById(R.id.scanningMessageText);

        final RecyclerViewEmptySupport messagesView = (RecyclerViewEmptySupport) view.findViewById(R.id.container);

        messagesView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        messagesView.setEmptyView(view.findViewById(R.id.list_empty));

        messagesView.setAdapter(_adapter);

        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getLoaderManager().initLoader(LOADER_ID, this.getArguments(), this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.add_by_beacon_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                MainActivityFragmentEnum parent
                        = (MainActivityFragmentEnum) getArguments().getSerializable(PartFragment.ARG_PARAM_PARENT);

                _listener.fragmentSelect(parent, new Bundle());
                break;
            case R.id.actionRefresh:
                _scanningMessageText.setVisibility(View.VISIBLE);
                scanLeDevice();
                break;
            default:
                throw new IllegalArgumentException("unknown menu option");
        }

        return true;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        _listener = null;
    }

    /**
     * Scan for beacons
     */
    private void scanLeDevice() {
        AnalyticHelper ah = AnalyticHelper.getInstance(getActivity());
        ah.eventLogger("BLE_SCAN", "ble_scan");

        BluetoothManager manager = (BluetoothManager) getActivity().getSystemService(Context.BLUETOOTH_SERVICE);
        _btAdapter = manager.getAdapter();

        if (_btAdapter == null || !_btAdapter.isEnabled()) {
            // prompt user to enable bluetooth
            ToastHelper.show("BL NOT ENABLED!!", getActivity());

//            _listener.enableBlueToothDialog();
        } else {

            _btAdapter.startLeScan(_scanCallback);

            _handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    ToastHelper.show("SCAN DONE",
                            getActivity());

                    _btAdapter.stopLeScan(_scanCallback);

                    Cursor filteredResultCursor = getFilteredResultCursor(_cursor);

                    _adapter.setCursor(filteredResultCursor);

                    _adapter.notifyDataSetChanged();

                    _adapter.setJobSites(_contentFacade.selectSiteAll(getActivity()));

                    _scanningMessageText.setVisibility(View.INVISIBLE);
                }
            }, SCAN_DURATION);
        }
    }

    /**
     * @param cursor
     * @return
     */
    private Cursor getFilteredResultCursor(Cursor cursor) {

        String[] columns = new String[]{
                PartTable.Columns._ID,
                PartTable.Columns.NAME,
                PartTable.Columns.SERIAL,
                PartTable.Columns.SITE_ID,
                PartTable.Columns.STATUS,
                PartTable.Columns.MANUFACTURER,
                PartTable.Columns.MODEL_NUMBER,
                PartTable.Columns.BARCODE,
                PartTable.Columns.BLE_ADDRESS,
                PartTable.Columns.BLE_IN_RANGE,
                PartTable.Columns.OWNER,
                PartTable.Columns.PICKED_UP_DATE_TS,
                PartTable.Columns.CONDITION,
                PartTable.Columns.LATITUDE,
                PartTable.Columns.LONGITUDE,
                PartTable.Columns.DESCRIPTION,
                PartTable.Columns.CATEGORY
        };

        MatrixCursor matrixCursor = new MatrixCursor(columns);

        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);

            PartModel partModel = new PartModel();
            partModel.setDefault();
            partModel.fromCursor(cursor);

            if (_scannedDevices.contains(partModel.getBleAddress())) {
                matrixCursor.addRow(new Object[]{
                        partModel.getId(),
                        partModel.getName(),
                        partModel.getSerial(),
                        partModel.getSiteId(),
                        partModel.getStatus(),
                        partModel.getManufacturer(),
                        partModel.getModelNumber(),
                        partModel.getBarcode(),
                        partModel.getBleAddress(),
                        partModel.getBleInRange(),
                        partModel.getOwner(),
                        partModel.getPickedUpDate().getTime(),
                        partModel.getCondition(),
                        partModel.getLocation().getLatitude(),
                        partModel.getLocation().getLongitude(),
                        partModel.getDescription(),
                        partModel.getCategory()});
            }

        }

        return matrixCursor;
    }

    private BluetoothAdapter.LeScanCallback _scanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, final int rssi, byte[] scanRecord) {
            Log.i(LOG_TAG, "Next BLE device: " + device);

            if (!_scannedDevices.contains(device.getAddress())) {
                _scannedDevices.add(device.getAddress());
            }
        }
    };

}

