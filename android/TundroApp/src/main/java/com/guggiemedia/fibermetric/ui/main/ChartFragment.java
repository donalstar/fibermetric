package com.guggiemedia.fibermetric.ui.main;


import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.SphericalUtil;

import net.go_factory.tundro.R;

import com.guggiemedia.fibermetric.lib.Personality;
import com.guggiemedia.fibermetric.lib.chain.CommandFacade;
import com.guggiemedia.fibermetric.lib.db.ChartModel;
import com.guggiemedia.fibermetric.lib.db.JobStateEnum;
import com.guggiemedia.fibermetric.ui.utility.ToastHelper;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChartFragment extends SupportMapFragment implements GoogleMap.OnMarkerClickListener {
    public static final String FRAGMENT_TAG = "FRAGMENT_CHART";

    public static final String ARG_PARAM_PARENT = "PARAM_PARENT";

    public static final String ARG_PARAM_SITE_LAT = "siteLatitude";
    public static final String ARG_PARAM_SITE_LONG = "siteLongitude";
    public static final String ARG_PARAM_SITE_NAME = "siteName";

    public static final String ARG_PARAM_ROW_ID = "PARAM_ROW_ID";
    public static final String ARG_PARAM_CHART_TYPE = "PARAM_CHART_TYPE";
    public static final String ARG_DIRECTIONS = "DIRECTIONS";

    private static int DEFAULT_ZOOM_LEVEL = 10;

    public static final String LOG_TAG = ChartFragment.class.getName();

    private Map<String, ChartModel.MarkerData> _markers;
    private ChartModel.ChartType _chartType;
    MainActivityFragmentEnum _parentFragment;

    private MainActivityListener _listener;

    public static ChartFragment newInstance() {
        return new ChartFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        _listener = (MainActivityListener) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this.getActivity());

        Log.i(LOG_TAG, "google play status " + status);

        if (status == ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED){
            ToastHelper.show("You need to update Google Play Services in order to view maps", this.getActivity());

            return view;
        }

        _chartType = (ChartModel.ChartType) getArguments().get(ARG_PARAM_CHART_TYPE);
        _parentFragment = (MainActivityFragmentEnum) getArguments().getSerializable(ARG_PARAM_PARENT);

        Long rowId = getArguments().getLong(ARG_PARAM_ROW_ID);

        boolean isToday = getArguments().getBoolean(JobListFragment.ARG_PARAM_TODAY);

        _markers = new HashMap<>();

        List<ChartModel.MarkerData> markerData = getMarkerData(_chartType, rowId, isToday);

        ActionBar actionBar = ((AppCompatActivity) this.getActivity()).getSupportActionBar();

        if (actionBar != null) {
            ChartModel.ChartType type = _chartType;

            if (type != null) {
                switch (type) {
                    case jobSites:
                        actionBar.setTitle(getString(R.string.menuJobToday));
                        break;
                    default:
                        actionBar.setTitle(markerData.get(0).title);
                        break;
                }
            }

            int homeIconId = R.drawable.ic_menu_white;

            if (_parentFragment != null) {
                homeIconId = R.drawable.ic_arrow_back_white;
            }

            actionBar.setHomeAsUpIndicator(homeIconId);
        }

        LatLng latLng = getTargetLocation(markerData);

        int zoomLevel = getTargetZoomLevel(markerData);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(latLng);

        CameraUpdate zoom = CameraUpdateFactory.zoomTo(zoomLevel);

        GoogleMap map = getMap();

        map.setOnMarkerClickListener(this);

        map.moveCamera(cameraUpdate);

        map.animateCamera(zoom);

        map.setMyLocationEnabled(true);

        if (markerData != null) {
            for (ChartModel.MarkerData item : markerData) {
                MarkerOptions markerOptions
                        = new MarkerOptions().icon(
                        BitmapDescriptorFactory.fromResource(item.markerImageData.mainImageId));

                Location location = item.location;

                if (location != null) {
                    LatLng locationValues = new LatLng(location.getLatitude(), location.getLongitude());

                    Marker marker = map.addMarker(markerOptions
                            .position(locationValues)
                            .title(item.title));

                    _markers.put(marker.getId(), item);
                }
            }
        }

        return view;
    }

    private int getIconResource(JobStateEnum state) {
        int resource = -1;

        switch (state) {
            case COMPLETE:
                resource = R.drawable.ic_job_comleted_map;
                break;
            case SCHEDULE:
                resource = R.drawable.ic_job_scheduled_map;
                break;
            case ACTIVE:
                resource = R.drawable.ic_job_active_map;
                break;
            case SUSPEND:
                resource = R.drawable.ic_job_suspended_map;
                break;
            default:
                Log.i(LOG_TAG, "unknown state:" + state);
                break;

        }

        return resource;
    }

    // OnMarkerClickListener

    @Override
    public boolean onMarkerClick(Marker marker) {

        ChartModel.MarkerData markerData = _markers.get(marker.getId());

        if (_chartType.equals(ChartModel.ChartType.jobSites)) {
            Bundle bundle = new Bundle();
            bundle.putLong(JobViewFragment.ARG_PARAM_JOB_ID, markerData.rowId);

            _listener.fragmentPush(MainActivityFragmentEnum.CHART_VIEW, getArguments(),
                    MainActivityFragmentEnum.JOB_VIEW, bundle);
        } else {
            doTurnByTurn(markerData, marker.getTitle());
        }

        return false;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.chart_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(LOG_TAG, "onOptionsItemSelected:" + item);

        switch (item.getItemId()) {
            case android.R.id.home:
                if (_parentFragment == null) {
                    _listener.navDrawerOpen(true);
                } else {
                    _listener.fragmentPop();
                }

                break;
            case R.id.actionJobList:
                _listener.fragmentSelect(MainActivityFragmentEnum.JOB_TODAY_LIST, new Bundle());
                break;
            case R.id.actionToolBox:
                _listener.fragmentSelect(MainActivityFragmentEnum.TODAYS_INVENTORY_VIEW, new Bundle());
                break;
            case R.id.actionHelp:
                _listener.fragmentSelect(MainActivityFragmentEnum.HELP_VIEW, new Bundle());
                break;
            case R.id.actionFeedBack:
                _listener.fragmentSelect(MainActivityFragmentEnum.FEEDBACK_FORM, new Bundle());
                break;
            case R.id.actionSearch:
                break;
            default:
                throw new IllegalArgumentException("unknown menu option");
        }

        return true;
    }


    /**
     * @param markerData
     * @param name
     */
    private void doTurnByTurn(ChartModel.MarkerData markerData, String name) {
        if (markerData != null) {
            Bundle args = new Bundle();

            args.putSerializable(ARG_PARAM_SITE_LAT, markerData.location.getLatitude());
            args.putSerializable(ARG_PARAM_SITE_LONG, markerData.location.getLongitude());

            args.putSerializable(ARG_PARAM_SITE_NAME, name);

            _listener.fragmentSelect(MainActivityFragmentEnum.NAVIGATION_PREVIEW, args);
        }
    }

    /**
     * @param markerData
     * @return
     */
    private LatLng getTargetLocation(List<ChartModel.MarkerData> markerData) {
        LatLng targetLocation = null;

        Location currentLocation = Personality.currentLocation;

        if (currentLocation != null) {
            targetLocation = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        }

        if (markerData != null && !markerData.isEmpty()) {
            Double[] minMaxLatLng = getMinAndMaxLatAndLng(markerData);

            Double minLat = minMaxLatLng[0];
            Double maxLat = minMaxLatLng[1];
            Double minLong = minMaxLatLng[2];
            Double maxLong = minMaxLatLng[3];

            targetLocation = new LatLng(minLat + (maxLat - minLat) / 2,
                    minLong + (maxLong - minLong) / 2);
        }

        return targetLocation;
    }

    /**
     * @param markerData
     * @return
     */
    private int getTargetZoomLevel(List<ChartModel.MarkerData> markerData) {
        int zoomLevel = DEFAULT_ZOOM_LEVEL;

        if (markerData != null && markerData.size() > 1) {
            Double[] minMaxLatLng = getMinAndMaxLatAndLng(markerData);

            Double minLat = minMaxLatLng[0];
            Double maxLat = minMaxLatLng[1];
            Double minLong = minMaxLatLng[2];
            Double maxLong = minMaxLatLng[3];

            LatLng minLatLng = new LatLng(minLat, minLong);
            LatLng maxLatLng = new LatLng(maxLat, maxLong);

            double range = SphericalUtil.computeDistanceBetween(minLatLng, maxLatLng);

            zoomLevel = getZoomLevel(range);
        }

        return zoomLevel;
    }

    /**
     * @param markerData
     * @return
     */
    private Double[] getMinAndMaxLatAndLng(List<ChartModel.MarkerData> markerData) {
        Double minLat = markerData.get(0).location.getLatitude();
        Double maxLat = minLat;
        Double minLong = markerData.get(0).location.getLongitude();
        Double maxLong = minLong;

        for (int i = 0; i < markerData.size(); i++) {
            ChartModel.MarkerData marker = markerData.get(i);

            Double latitude = marker.location.getLatitude();
            Double longitude = marker.location.getLongitude();

            if (latitude < minLat) {
                minLat = latitude;
            }

            if (latitude > maxLat) {
                maxLat = latitude;
            }

            if (longitude < minLong) {
                minLong = longitude;
            }

            if (longitude > maxLong) {
                maxLong = longitude;
            }
        }

        return new Double[]{minLat, maxLat, minLong, maxLong};
    }

    private int getZoomLevel(double range) {
        int zoomLevel = -1;

        double scale = range / 500;
        zoomLevel = (int) (16 - Math.log(scale) / Math.log(2));

        return zoomLevel;
    }

    private List<ChartModel.MarkerData> getMarkerData(ChartModel.ChartType chartType, Long id, Boolean isToday) {
        List<ChartModel.MarkerData> markerData
                = CommandFacade.getChartMarkerData(id, chartType, isToday, getActivity());

        for (ChartModel.MarkerData marker : markerData) {
            switch (chartType) {
                case jobSites:
                    marker.markerImageData.mainImageId = getIconResource(marker.markerImageData.jobState);
                    break;
                default:
                    marker.markerImageData.mainImageId = R.drawable.ic_inventory_default;
                    break;
            }
        }

        return markerData;
    }
}





