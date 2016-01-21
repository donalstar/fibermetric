package com.guggiemedia.fibermetric.ui.main;

import android.app.Activity;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import net.go_factory.tundro.R;

import com.guggiemedia.fibermetric.lib.db.JobStateEnum;
import com.guggiemedia.fibermetric.lib.db.JobTaskModel;
import com.guggiemedia.fibermetric.lib.db.TurnByTurnDirectionsModel;
import com.guggiemedia.fibermetric.ui.utility.DirectionsLookup;
import com.guggiemedia.fibermetric.ui.utility.ToastHelper;



import java.util.List;

/**
 * Created by donal on 9/22/15.
 */
public class NavigationPreviewFragment extends Fragment {
    public static final String LOG_TAG = NavigationPreviewFragment.class.getName();

    public static final String ARG_PARAM_JOB = "PARAM_JOB";

    public static NavigationPreviewFragment newInstance() {
        return new NavigationPreviewFragment();
    }

    private MainActivityListener _listener;
    private MapView _mapView;
    private View _view;
    private android.support.design.widget.FloatingActionButton _startJobFab;

    private TurnByTurnDirectionsModel _turnByTurnDirectionsModel;

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
        View view = inflater.inflate(R.layout.fragment_navigation_preview, container, false);

        _view = view;

        _mapView = (MapView) view.findViewById(R.id.mapView);
        _mapView.onCreate(savedInstanceState);

        _mapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        ActionBar actionBar = ((AppCompatActivity) this.getActivity()).getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayShowCustomEnabled(true);
        }

        JobTaskModel jobModel;

        if (getArguments() != null) {
            jobModel = (JobTaskModel) getArguments().getSerializable(ARG_PARAM_JOB);


            if (jobModel != null) {
                if (actionBar != null) {
                    actionBar.setTitle(jobModel.getName());
                }

                final JobTaskModel jobTaskModel = jobModel;

                _startJobFab = (android.support.design.widget.FloatingActionButton) view.findViewById(R.id.startJobFab);

                if (jobModel.getState().equals(JobStateEnum.COMPLETE)) {
                    _startJobFab.setVisibility(View.GONE);
                }

                _startJobFab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle newBundle = new Bundle();
                        newBundle.putLong(JobViewFragment.ARG_PARAM_JOB_ID, jobTaskModel.getId());
                        newBundle.putSerializable(ChartFragment.ARG_PARAM_SITE_LAT, getArguments().getSerializable(ChartFragment.ARG_PARAM_SITE_LAT));
                        newBundle.putSerializable(ChartFragment.ARG_PARAM_SITE_LONG, getArguments().getSerializable(ChartFragment.ARG_PARAM_SITE_LONG));

                        newBundle.putSerializable(ChartFragment.ARG_PARAM_SITE_NAME, jobTaskModel.getName());
                        newBundle.putSerializable(ChartFragment.ARG_DIRECTIONS, _turnByTurnDirectionsModel);

                        Bundle oldBundle = new Bundle();

                        oldBundle.putSerializable(ChartFragment.ARG_PARAM_SITE_LAT, getArguments().getSerializable(ChartFragment.ARG_PARAM_SITE_LAT));
                        oldBundle.putSerializable(ChartFragment.ARG_PARAM_SITE_LONG, getArguments().getSerializable(ChartFragment.ARG_PARAM_SITE_LONG));

                        oldBundle.putSerializable(ChartFragment.ARG_PARAM_SITE_NAME, jobTaskModel.getName());
                        oldBundle.putLong(JobViewFragment.ARG_PARAM_JOB_ID, jobTaskModel.getId());

                        _listener.fragmentPush(MainActivityFragmentEnum.NAVIGATION_PREVIEW, oldBundle,
                                MainActivityFragmentEnum.TURN_BY_TURN_VIEW, newBundle);
                    }
                });
            }
            else {
                ToastHelper.show("Invalid job", getActivity());
            }

        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (getArguments() != null) {
            Double jobSiteLatitude = (Double) getArguments().getSerializable(ChartFragment.ARG_PARAM_SITE_LAT);
            Double jobSiteLongitude = (Double) getArguments().getSerializable(ChartFragment.ARG_PARAM_SITE_LONG);

            Double location[] = {jobSiteLatitude, jobSiteLongitude};

            final GoogleMap googleMap = _mapView.getMap();

            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(jobSiteLatitude, jobSiteLongitude), 15.0f));

            new DirectionsLookup(getActivity(), new DirectionsLookup.DirectionsLookupListener() {
                @Override
                public void setResult(TurnByTurnDirectionsModel result) {
                    _turnByTurnDirectionsModel = result;

                    handleNavigationResults(result, googleMap);
                }
            }).execute(location);
        }
    }

    /**
     * @param result
     * @param googleMap
     */
    private void handleNavigationResults(TurnByTurnDirectionsModel result, GoogleMap googleMap) {
        TextView distance = (TextView) _view.findViewById(R.id.distance);

        distance.setText(result.getDistance());

        TextView duration = (TextView) _view.findViewById(R.id.duration);
        duration.setText(result.getDuration());

        Location homeLocation = new Location("");

        LatLng firstPoint = result.getPolyline().get(0);

        homeLocation.setLatitude(firstPoint.latitude);
        homeLocation.setLongitude(firstPoint.longitude);

        drawMap(googleMap, homeLocation, result);

        drawPath(googleMap, result);
    }

    /**
     * @param map
     * @param location
     */
    private void drawMap(GoogleMap map, Location location, TurnByTurnDirectionsModel result) {

        if (location != null) {
            LatLng locationValues = new LatLng(location.getLatitude(), location.getLongitude());

            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(locationValues);

            map.moveCamera(cameraUpdate);
        }

        List<LatLng> list = result.getPolyline();

        int padding = 20; // offset from edges of the map in pixels
        CameraUpdate zoom = CameraUpdateFactory.newLatLngBounds(getBounds(list), padding);

        map.animateCamera(zoom);

        map.setMyLocationEnabled(true);
    }


    private LatLngBounds getBounds(List<LatLng> list) {

        Double westLng = list.get(0).longitude;
        Double eastLng = list.get(0).longitude;
        Double northLat = list.get(0).latitude;
        Double southLat = list.get(0).latitude;

        for (LatLng latLng : list) {
            if (latLng.longitude < westLng) {
                westLng = latLng.longitude;
            }

            if (latLng.longitude > eastLng) {
                eastLng = latLng.longitude;
            }

            if (latLng.latitude > northLat) {
                northLat = latLng.latitude;
            }

            if (latLng.latitude < southLat) {
                southLat = latLng.latitude;
            }
        }

        LatLng nwPoint = new LatLng(northLat, westLng);
        LatLng sePoint = new LatLng(southLat, eastLng);

        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        builder.include(nwPoint);
        builder.include(sePoint);

        return builder.build();
    }

    public void drawPath(GoogleMap map, TurnByTurnDirectionsModel result) {
        List<LatLng> list = result.getPolyline();

        map.addMarker(new MarkerOptions().position(list.get(0)).icon(
                BitmapDescriptorFactory.fromResource(R.drawable.ic_location_red)));

        map.addMarker(new MarkerOptions().position(list.get(list.size() - 1)).icon(
                BitmapDescriptorFactory.fromResource(R.drawable.ic_map_view_white)));

        for (int z = 0; z < list.size() - 1; z++) {
            LatLng src = list.get(z);
            LatLng dest = list.get(z + 1);

            map.addPolyline(new PolylineOptions()
                    .add(new LatLng(src.latitude, src.longitude),
                            new LatLng(dest.latitude, dest.longitude))

                    .width(5).color(Color.BLUE).geodesic(true));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                _listener.fragmentPop();
                break;
            default:
                throw new IllegalArgumentException("unknown menu option");
        }

        return true;
    }
}





