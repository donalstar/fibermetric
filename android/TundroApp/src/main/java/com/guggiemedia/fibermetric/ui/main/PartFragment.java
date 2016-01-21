package com.guggiemedia.fibermetric.ui.main;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.go_factory.tundro.R;

import com.guggiemedia.fibermetric.lib.chain.CommandFacade;
import com.guggiemedia.fibermetric.lib.db.ChainOfCustodyTable;
import com.guggiemedia.fibermetric.lib.db.ChartModel;
import com.guggiemedia.fibermetric.lib.db.ContentFacade;
import com.guggiemedia.fibermetric.lib.db.DataBaseTable;
import com.guggiemedia.fibermetric.lib.db.InventoryCategoryEnum;
import com.guggiemedia.fibermetric.lib.db.InventoryStatusEnum;
import com.guggiemedia.fibermetric.lib.db.PartModel;
import com.guggiemedia.fibermetric.lib.db.PartTable;
import com.guggiemedia.fibermetric.lib.db.SiteModel;
import com.guggiemedia.fibermetric.ui.chart.ChartActivity;
import com.guggiemedia.fibermetric.ui.component.FloatingActionMenu;
import com.guggiemedia.fibermetric.ui.utility.FloatingActionButtonHelper;
import com.guggiemedia.fibermetric.ui.utility.ToastHelper;


import java.text.SimpleDateFormat;
import java.util.List;

public class PartFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final String FRAGMENT_TAG = "FRAGMENT_PART";

    public static final String LOG_TAG = PartFragment.class.getName();

    public static final String ARG_PARAM_ROW_ID = "PARAM_ROW_ID";
    public static final String ARG_PARAM_ITEM_NAME = "PARAM_ITEM_NAME";
    public static final String ARG_PARAM_PARENT = "PARAM_PARENT";
    public static final String ARG_PARAM_PART = "ARG_PARAM_PART";

    public static final String SYSTEM_CUSTODIAN = "System";
    public static final String SYSTEM_SITE_NAME = "Equipment Shelter1";

    public static final int COC_LOADER_ID = 271922;
    public static final int BEACON_IN_RANGE_LOADER_ID = 271923;

    private static SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("MM/dd/yyyy");

    private PartAdapter _adapter;
    private PartHeaderAdapter _partHeaderAdapter;

    private MainActivityListener _listener;

    private TextView _partName;
    private ImageView _headerImage;
    private ImageView _statusImage;
    private TextView _status;
    private TextView _serialNumber;
    private TextView _siteName;
    private TextView _location;
    private ImageView _locationImage;
    private TextView _description;
    private TextView _manufacturer;
    private TextView _modelNumber;
    private ImageView _beaconIndicator;

    private FrameLayout _fabFrame;
    private android.support.design.widget.FloatingActionButton _fab;
    private FloatingActionMenu _fabMenu;

    private android.support.design.widget.FloatingActionButton _transferFab;

    private RecyclerView _partHeaderView;
    private RecyclerView _chainOfCustodyItemsView;

    private MainActivityFragmentEnum _parentType;

    private PartModel _model;
    private SiteModel _siteModel;

    private final ContentFacade _contentFacade = new ContentFacade();

    // LoaderCallback
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.d(LOG_TAG, "onCreateLoader:" + id);

        Loader<Cursor> cursorLoader;

        if (id == COC_LOADER_ID) {
            DataBaseTable table = new ChainOfCustodyTable();

            String[] projection = table.getDefaultProjection();
            String orderBy = table.getDefaultSortOrder();

            String selection = "part_id=?";

            String[] selectionArgs = {String.valueOf(args.getLong(PartFragment.ARG_PARAM_ROW_ID))};

            cursorLoader = new CursorLoader(getActivity(), ChainOfCustodyTable.CONTENT_URI, projection, selection, selectionArgs, orderBy);
        } else {
            DataBaseTable table = new PartTable();

            String[] projection = table.getDefaultProjection();
            String orderBy = table.getDefaultSortOrder();

            String selection = "_id=?";

            String[] selectionArgs = {String.valueOf(args.getLong(PartFragment.ARG_PARAM_ROW_ID))};

            cursorLoader = new CursorLoader(getActivity(), PartTable.CONTENT_URI, projection, selection, selectionArgs, orderBy);
        }

        return cursorLoader;
    }

    // LoaderCallback
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        if (cursor != null) {
            Log.i(LOG_TAG, "onLoadFinished - id = " + loader.getId());

            if (loader.getId() == COC_LOADER_ID) {
                _adapter.setCursor(cursor);
                _adapter.notifyDataSetChanged();

                setChainOfCustodyViewHeight();
            } else {
                _partHeaderAdapter.setCursor(cursor);
                _partHeaderAdapter.notifyDataSetChanged();
            }
        }
    }

    // LoaderCallback
    public void onLoaderReset(Loader<Cursor> loader) {

        if (loader.getId() == COC_LOADER_ID) {
            _adapter.setCursor(null);
        } else {
            _partHeaderAdapter.setCursor(null);
        }
    }

    public static PartFragment newInstance() {
        return new PartFragment();
    }


    public void onAttach(Activity activity) {
        super.onAttach(activity);
        _listener = (MainActivityListener) activity;
        _adapter = new PartAdapter(activity);
        _partHeaderAdapter = new PartHeaderAdapter(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_part, container, false);

        _headerImage = (ImageView) view.findViewById(R.id.headerImage);

        _partHeaderView = (RecyclerView) view.findViewById(R.id.partHeaderView);
        _partHeaderView.setAdapter(_partHeaderAdapter);
        _partHeaderView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        _serialNumber = (TextView) view.findViewById(R.id.serialNumber);

        _manufacturer = (TextView) view.findViewById(R.id.manufacturer);
        _modelNumber = (TextView) view.findViewById(R.id.modelNumber);

        _siteName = (TextView) view.findViewById(R.id.siteName);
        _location = (TextView) view.findViewById(R.id.location);

        // for now, hide site & location fields
        TextView locationHeader = (TextView) view.findViewById(R.id.locationHeader);

        _siteName.setVisibility(View.GONE);
        _location.setVisibility(View.GONE);
        locationHeader.setVisibility(View.GONE);


        _locationImage = (ImageView) view.findViewById(R.id.locationImage);
        _description = (TextView) view.findViewById(R.id.description);

        _description = (TextView) view.findViewById(R.id.description);
        _description = (TextView) view.findViewById(R.id.description);

        _chainOfCustodyItemsView = (RecyclerView) view.findViewById(R.id.chainOfCustodyItems);
        _chainOfCustodyItemsView.setAdapter(_adapter);
        _chainOfCustodyItemsView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        _parentType = (MainActivityFragmentEnum) getArguments().getSerializable(
                PartFragment.ARG_PARAM_PARENT);

        _fabFrame = (FrameLayout) view.findViewById(R.id.fabFrame);

        _transferFab = (android.support.design.widget.FloatingActionButton) view.findViewById(R.id.transferFab);

        _transferFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastHelper.show("Released custody...", getActivity());

                releaseCustody();

                _listener.fragmentSelect(_parentType, new Bundle());
            }
        });

        _fab = (android.support.design.widget.FloatingActionButton) view.findViewById(R.id.fab);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        Long id = getArguments().getLong(PartFragment.ARG_PARAM_ROW_ID);

        _model = CommandFacade.partSelectByRowId(id, getActivity());

        _siteModel = CommandFacade.siteSelectByRowId(_model.getSiteId(), getActivity());

        _serialNumber.setText(_model.getSerial());

        _manufacturer.setText(_model.getManufacturer());
        _modelNumber.setText(_model.getModelNumber());

        Long siteId = _model.getSiteId();

        if (siteId != 0) {
            _siteName.setVisibility(View.VISIBLE);
            _siteName.setText(_siteModel.getName());
        } else {
            _siteName.setVisibility(View.GONE);
        }

        Location location = _model.getLocation();

        String locationName = "";

        locationName += location.getLatitude() + ", " + location.getLongitude();

        _location.setText(locationName);

        View.OnClickListener locationClickListener = new LocationClickListener();

        _siteName.setOnClickListener(locationClickListener);
        _locationImage.setOnClickListener(locationClickListener);

        _description.setText(_model.getDescription());

        if (_model.getStatus().equals(InventoryStatusEnum.inCustody)) {
            _transferFab.setVisibility(View.VISIBLE);
        } else {
            _transferFab.setVisibility(View.GONE);
        }

        _fab.setVisibility(View.VISIBLE);

        int fabImageResourceId = (_model.getStatus().equals(InventoryStatusEnum.inCustody))
                ? R.drawable.ic_refresh_red : R.drawable.ic_add_custody_white;

        _fab.setImageResource(fabImageResourceId);

        int fabTintColor = (_model.getStatus().equals(InventoryStatusEnum.inCustody))
                ? R.color.bright_white : R.color.tomato;

        _fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(fabTintColor)));

        int headerImageId = _model.getCategory().equals(InventoryCategoryEnum.tools)
                ? R.drawable.img_tool_default
                : R.drawable.img_part_default;

        _headerImage.setImageResource(headerImageId);

        createFabMenu(_fabFrame);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getLoaderManager().initLoader(COC_LOADER_ID, this.getArguments(), this);
        getLoaderManager().initLoader(BEACON_IN_RANGE_LOADER_ID, this.getArguments(), this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.part_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(LOG_TAG, "onOptionsItemSelected:" + item);

        switch (item.getItemId()) {
            case android.R.id.home:
                _listener.fragmentSelect(_parentType, new Bundle());
                break;
            case R.id.actionSearch:
                break;
            case R.id.actionMore:
                break;
            case R.id.actionReport:

                Bundle bundle = new Bundle();
                bundle.putLong(PartFragment.ARG_PARAM_ROW_ID, getArguments().getLong(PartFragment.ARG_PARAM_ROW_ID));

                _listener.fragmentPush(MainActivityFragmentEnum.PART_VIEW, bundle, MainActivityFragmentEnum.REPORT_VIEW, bundle);
                break;
            default:
                throw new IllegalArgumentException("unknown menu option");
        }

        return true;
    }


    private void createFabMenu(ViewGroup view) {
        int fabMenuButtonImages[] = {R.drawable.ic_barcode, R.drawable.ic_beacon};

        int beaconActionLabelId = (_model.getStatus().equals(InventoryStatusEnum.pickUp))
                ? R.string.inventory_add_by_beacon : R.string.part_replace_beacon;

        int barcodeActionLabelId = (_model.getStatus().equals(InventoryStatusEnum.pickUp))
                ? R.string.inventory_add_by_barcode : R.string.part_replace_barcode;

        int fabMenuButtonLabels[] = {barcodeActionLabelId, beaconActionLabelId};

        FloatingActionButtonHelper floatingActionButtonHelper = new FloatingActionButtonHelper();

        _fabMenu = floatingActionButtonHelper.createFloatingActionButtonMenu(
                fabMenuButtonImages,
                fabMenuButtonLabels, getActivity(),
                view, _fab);


        floatingActionButtonHelper.addOnButtonClickListener(new FloatingActionButtonHelper.ButtonClickListener() {
            @Override
            public void handleButtonClick(int imageId) {
                switch (imageId) {
                    case R.drawable.ic_beacon: {
                        Bundle bundle = new Bundle();

                        bundle.putSerializable(PartFragment.ARG_PARAM_PARENT, _parentType);

                        bundle.putSerializable(PartFragment.ARG_PARAM_PART, _model);

                        if (_model.getStatus().equals(InventoryStatusEnum.inCustody)) {
                            _listener.fragmentSelect(MainActivityFragmentEnum.REPLACE_BEACON_VIEW, bundle);
                        } else {
                            _listener.fragmentSelect(MainActivityFragmentEnum.ADD_BEACON_VIEW, bundle);
                        }

                        break;
                    }
                    case R.drawable.ic_barcode: {
                        _fabMenu.close(false);

                        Bundle bundle = new Bundle();

                        bundle.putLong(PartFragment.ARG_PARAM_ROW_ID,
                                getArguments().getLong(PartFragment.ARG_PARAM_ROW_ID));

                        MainActivityEnum targetActivity
                                = (_model.getStatus().equals(InventoryStatusEnum.inCustody))
                                ? MainActivityEnum.REPLACE_THING_BARCODE
                                : MainActivityEnum.DISCOVER_THING_BARCODE;

                        ((MainActivity) getActivity()).activitySelect(targetActivity, bundle);

                        break;
                    }
                }
            }
        });
    }

    /**
     *
     */
    private void setChainOfCustodyViewHeight() {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) _chainOfCustodyItemsView.getLayoutParams();

        params.height = _adapter.getItemCount() * getResources().getDimensionPixelSize(R.dimen.chain_of_custody_row_height);
        _chainOfCustodyItemsView.setLayoutParams(params);
        _chainOfCustodyItemsView.requestLayout();
    }

    /**
     *
     */
    private void releaseCustody() {
        _model.setStatus(InventoryStatusEnum.pickUp);

        CommandFacade.partStatusUpdate(_model, SYSTEM_CUSTODIAN, getSystemSite().getId(), getActivity());
    }

    /**
     * @return
     */
    private SiteModel getSystemSite() {
        List<SiteModel> jobSites = _contentFacade.selectSiteAll(getActivity());

        SiteModel systemSite = null;

        for (SiteModel model : jobSites) {
            if (model.getName().equals(SYSTEM_SITE_NAME)) {
                systemSite = model;
                break;
            }
        }

        return systemSite;
    }

    private class LocationClickListener implements View.OnClickListener {
        public void onClick(View v) {
            MainActivityFragmentEnum parent
                    = (MainActivityFragmentEnum) getArguments().getSerializable(
                    PartFragment.ARG_PARAM_PARENT);

            Long partId = getArguments().getLong(PartFragment.ARG_PARAM_ROW_ID);

            Bundle oldBundle = new Bundle();

            oldBundle.putLong(PartFragment.ARG_PARAM_ROW_ID, partId);
            oldBundle.putSerializable(PartFragment.ARG_PARAM_PARENT, parent);
            oldBundle.putString(PartFragment.ARG_PARAM_ITEM_NAME, _model.getName());

            Bundle newBundle = new Bundle();

            newBundle.putSerializable(ChartFragment.ARG_PARAM_PARENT,
                    MainActivityFragmentEnum.PART_VIEW);
            newBundle.putLong(ChartFragment.ARG_PARAM_ROW_ID, partId);
            newBundle.putSerializable(ChartFragment.ARG_PARAM_CHART_TYPE, ChartModel.ChartType.part);

            Bundle bundle = new Bundle();

            bundle.putBundle(ChartActivity.ARG_OLD_BUNDLE, oldBundle);
            bundle.putBundle(ChartActivity.ARG_NEW_BUNDLE, newBundle);

            _listener.activitySelect(MainActivityEnum.CHART_ACTIVITY, bundle);

        }
    }
}




