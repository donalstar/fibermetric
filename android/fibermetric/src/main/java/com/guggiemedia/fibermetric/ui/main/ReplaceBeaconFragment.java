package com.guggiemedia.fibermetric.ui.main;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.guggiemedia.fibermetric.R;
import com.guggiemedia.fibermetric.lib.chain.CommandEnum;
import com.guggiemedia.fibermetric.lib.chain.CommandFactory;
import com.guggiemedia.fibermetric.lib.chain.ContextFactory;
import com.guggiemedia.fibermetric.lib.chain.PartUpdateCtx;
import com.guggiemedia.fibermetric.lib.db.PartModel;
import com.guggiemedia.fibermetric.lib.utility.AnalyticHelper;
import com.guggiemedia.fibermetric.ui.utility.ToastHelper;


import java.util.HashMap;
import java.util.Map;

public class ReplaceBeaconFragment extends Fragment {
    public static final String FRAGMENT_TAG = "FRAGMENT_REPLACE_BEACON_VIEW";

    public static final Long SCAN_DURATION = 8000L;
    private Handler _handler;
    private ReplaceBeaconAdapter _adapter;

    private TextView _scanningMessageText;
    private RelativeLayout _waitingImage;

    private BluetoothAdapter _btAdapter;

    private Map<String, String> _devices;

    public static final String LOG_TAG = ReplaceBeaconFragment.class.getName();

    private MainActivityListener _listener;

    private MatrixCursor _cursor;
    private PartModel _model;
    private PartUpdateCtx _partUpdateCtx;

    public static ReplaceBeaconFragment newInstance() {
        return new ReplaceBeaconFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        _listener = (MainActivityListener) activity;

        _model = (PartModel)getArguments().getSerializable(PartFragment.ARG_PARAM_PART);

        _adapter = new ReplaceBeaconAdapter(activity, this, _model.getName());

        String columns[] = { "name", "address" };

        _cursor = new MatrixCursor(columns);

        _adapter.setCursor(_cursor);

        _partUpdateCtx = (PartUpdateCtx) ContextFactory.factory(CommandEnum.PART_UPDATE, activity);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        _handler = new Handler();
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.i(LOG_TAG, "onResume");

        scanLeDevice();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.fragment_replace_beacon, container, false);

        RecyclerView recyclerView = (RecyclerView) mainView.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(_adapter);

        _scanningMessageText = (TextView) mainView.findViewById(R.id.scanningMessageText);
        _scanningMessageText.setText("Scanning for beacons");

        _waitingImage = (RelativeLayout) mainView.findViewById(R.id.waitingIndicator);

        _waitingImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastHelper.show("Re-scanning...", getActivity());

                scanLeDevice();
            }
        });

        return mainView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.stub_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(LOG_TAG, "onOptionsItemSelected:" + item);

        switch (item.getItemId()) {
            case android.R.id.home:
                _listener.navDrawerOpen(true);
                break;
            case R.id.actionSearch:
                break;
            default:
                throw new IllegalArgumentException("unknown menu option");
        }

        return true;
    }

    public void replaceBeacon(String address) {
        Log.i(LOG_TAG, "Replace beacon with " + address);

        _model.setBleAddress(address);

        _partUpdateCtx.setModel(_model);

        CommandFactory.execute(_partUpdateCtx);

        Bundle bundle = new Bundle();

        if (_model != null) {
            bundle.putLong(PartFragment.ARG_PARAM_ROW_ID, _model.getId());

            bundle.putSerializable(PartFragment.ARG_PARAM_PARENT, getArguments().getSerializable(PartFragment.ARG_PARAM_PARENT));
        }

        _listener.fragmentSelect(MainActivityFragmentEnum.PART_VIEW, bundle);
    }

    private void scanLeDevice() {
        AnalyticHelper ah = AnalyticHelper.getInstance(getActivity());
        ah.eventLogger("BLE_SCAN", "ble_scan");

        BluetoothManager manager = (BluetoothManager) getActivity().getSystemService(Context.BLUETOOTH_SERVICE);
        _btAdapter = manager.getAdapter();

        _devices = new HashMap<>();

        if (_btAdapter == null || !_btAdapter.isEnabled()) {
            // prompt user to enable bluetooth
            ToastHelper.show("BL NOT ENABLED!!", getActivity());

//            _listener.enableBlueToothDialog();
        } else {
            _scanningMessageText.setVisibility(View.VISIBLE);

            _waitingImage.setVisibility(View.INVISIBLE);

            _btAdapter.startLeScan(_scanCallback);

            _handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (_btAdapter != null) {
                        _btAdapter.stopLeScan(_scanCallback);
                    }

                    for (String device : _devices.keySet()) {
                        String name = _devices.get(device);

                        Object values[] = { name, device };

                        _cursor.addRow(values);
                    }

                    _adapter.notifyDataSetChanged();

                    _scanningMessageText.setVisibility(View.INVISIBLE);
                    _waitingImage.setVisibility(View.VISIBLE);

                    ToastHelper.show("SCAN COMPLETE", getActivity());

                }
            }, SCAN_DURATION);

        }
    }


    private BluetoothAdapter.LeScanCallback _scanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, final int rssi, byte[] scanRecord) {
            Log.i(LOG_TAG, "Next BLE device: " + device.getName() + " " + device.getAddress());

            String name = (device.getName() == null) ? "unknown" : device.getName();

            _devices.put(device.getAddress(), name);
        }
    };
}
