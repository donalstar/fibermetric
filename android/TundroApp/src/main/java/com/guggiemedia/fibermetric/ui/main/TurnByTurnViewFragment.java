package com.guggiemedia.fibermetric.ui.main;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import net.go_factory.tundro.R;

import com.guggiemedia.fibermetric.lib.Personality;
import com.guggiemedia.fibermetric.lib.chain.CommandFacade;
import com.guggiemedia.fibermetric.lib.db.TurnByTurnDirectionsModel;
import com.guggiemedia.fibermetric.ui.utility.ToastHelper;


import java.util.List;

/**
 * Created by donal on 9/22/15.
 */
public class TurnByTurnViewFragment extends Fragment {

    public static TurnByTurnViewFragment newInstance() {
        return new TurnByTurnViewFragment();
    }

    private MainActivityListener _listener;
    private MapView _mapView;
    private android.support.design.widget.FloatingActionButton _micFab;

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
        View view = inflater.inflate(R.layout.fragment_turnbyturn_view, container, false);

        _mapView = (MapView) view.findViewById(R.id.mapView);
        _mapView.onCreate(savedInstanceState);

        _mapView.onResume();// needed to get the map to display immediately

        MapsInitializer.initialize(getActivity().getApplicationContext());

        TurnByTurnDirectionsModel turnByTurnDirectionsModel
                = (TurnByTurnDirectionsModel) getArguments().getSerializable(ChartFragment.ARG_DIRECTIONS);

        TextView distance = (TextView) view.findViewById(R.id.distance);
        distance.setText(turnByTurnDirectionsModel.getDistance());

        TextView duration = (TextView) view.findViewById(R.id.duration);
        duration.setText(turnByTurnDirectionsModel.getDuration());

        ActionBar actionBar = ((AppCompatActivity) this.getActivity()).getSupportActionBar();

        if (actionBar != null) {
            if (getArguments() != null) {
                String step1 = turnByTurnDirectionsModel.getSteps().get(0);

                actionBar.setTitle(Html.fromHtml(step1));
            }
        }

        GoogleMap googleMap = _mapView.getMap();

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(Personality.currentLocation.getLatitude(),
                        Personality.currentLocation.getLongitude()), 16.0f));

        drawPath(googleMap, turnByTurnDirectionsModel);

        ImageView listButton = (ImageView) view.findViewById(R.id.listButton);

        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();

                bundle.putSerializable(ChartFragment.ARG_PARAM_SITE_LAT,
                        getArguments().getSerializable(ChartFragment.ARG_PARAM_SITE_LAT));

                bundle.putSerializable(ChartFragment.ARG_PARAM_SITE_LONG,
                        getArguments().getSerializable(ChartFragment.ARG_PARAM_SITE_LONG));

                bundle.putSerializable(ChartFragment.ARG_PARAM_SITE_NAME,
                        getArguments().getSerializable(ChartFragment.ARG_PARAM_SITE_NAME));

                _listener.fragmentSelect(MainActivityFragmentEnum.TURN_BY_TURN_LIST, bundle);
            }
        });

        _micFab = (android.support.design.widget.FloatingActionButton) view.findViewById(R.id.fab);

        _micFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastHelper.show("Starting first task...", getActivity());

                Long jobId = getArguments().getLong(JobViewFragment.ARG_PARAM_JOB_ID);

                CommandFacade.jobTaskActive(jobId, getActivity());

                Bundle bundle = new Bundle();
                bundle.putLong(TaskPagerFragment.ARG_PARAM_JOB_ID, jobId);

                _listener.fragmentSelect(MainActivityFragmentEnum.TASK_PAGER, bundle);

            }
        });

        return view;
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


    public void drawPath(GoogleMap map, TurnByTurnDirectionsModel result) {
        List<LatLng> list = result.getPolyline();

        map.addMarker(new MarkerOptions().position(list.get(0)).icon(
                BitmapDescriptorFactory.fromResource(R.drawable.ic_location_red)));

        for (int z = 0; z < 1; z++) {
            LatLng src = list.get(z);
            LatLng dest = list.get(z + 1);

            map.addPolyline(new PolylineOptions()
                    .add(new LatLng(src.latitude, src.longitude),
                            new LatLng(dest.latitude, dest.longitude))

                    .width(10).color(Color.RED).geodesic(true));
        }
    }
}





