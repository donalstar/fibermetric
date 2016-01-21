package com.guggiemedia.fibermetric.ui.main;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.guggiemedia.fibermetric.lib.chain.CommandEnum;
import com.guggiemedia.fibermetric.lib.chain.CommandFactory;
import com.guggiemedia.fibermetric.lib.chain.ContextFactory;
import com.guggiemedia.fibermetric.lib.chain.TurnByTurnDirectionsCtx;
import com.guggiemedia.fibermetric.lib.db.TurnByTurnDirectionsModel;

import net.go_factory.tundro.R;


import java.util.List;

/**
 * Created by donal on 9/22/15.
 */
public class TurnByTurnListFragment extends Fragment {

    public static final String FRAGMENT_TAG = "FRAGMENT_TURN_BY_TURN_LIST";

    public static TurnByTurnListFragment newInstance() {
        return new TurnByTurnListFragment();
    }

    private TurnByTurnDirectionsCtx _turnByTurnDirectionsCtx;
    private MainActivityListener _listener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        _listener = (MainActivityListener) activity;

        _turnByTurnDirectionsCtx = (TurnByTurnDirectionsCtx) ContextFactory.factory(CommandEnum.TURN_BY_TURN_DIRECTIONS, activity);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_turnbyturn_list, container, false);

        ActionBar actionBar = ((AppCompatActivity) this.getActivity()).getSupportActionBar();

        if (actionBar != null) {
            if (getArguments() != null) {
                String jobSiteName = (String) getArguments().getSerializable(ChartFragment.ARG_PARAM_SITE_NAME);

                actionBar.setTitle(jobSiteName);
            }
        }

        if (getArguments() != null) {
            Double jobSiteLatitude = (Double) getArguments().getSerializable(ChartFragment.ARG_PARAM_SITE_LAT);
            Double jobSiteLongitude = (Double) getArguments().getSerializable(ChartFragment.ARG_PARAM_SITE_LONG);

            Double location[] = {jobSiteLatitude, jobSiteLongitude};

            new DirectionsLookup(view).execute(location);
        }

        return view;
    }



    private class DirectionsLookup extends AsyncTask<Double[], Void, TurnByTurnDirectionsModel> {

        View _view;

        DirectionsLookup(View view) {
            _view = view;
        }

        @Override
        protected TurnByTurnDirectionsModel doInBackground(Double[]... location) {
            _turnByTurnDirectionsCtx.setJobSiteLocation(location[0]);

            CommandFactory.execute(_turnByTurnDirectionsCtx);

            return _turnByTurnDirectionsCtx.getInstructionSet();
        }

        @Override
        protected void onPostExecute(TurnByTurnDirectionsModel result) {

            List<String> steps = result.getSteps();

            if (steps != null) {
                for (int i = 0; i < steps.size(); i++) {
                    String line = steps.get(i);

                    addTableRow(_view, line, i);
                }
            } else {
                addTableRow(_view, "Error: no results", 0);
            }

            TextView distance = (TextView) _view.findViewById(R.id.distance);
            distance.setText(result.getDistance());

            TextView duration = (TextView) _view.findViewById(R.id.duration);
            duration.setText(result.getDuration());
        }

        private void addTableRow(View view, String line, int index) {
            TableLayout table = (TableLayout) view.findViewById(R.id.contentTable);

            TableRow row = new TableRow(getActivity());
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);

            TextView label = new TextView(getActivity());
            label.setGravity(Gravity.LEFT);

            label.setText(index + ":");

            TableRow.LayoutParams paramsExample =
                    new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.WRAP_CONTENT, 1.0f);

            TextView tv = new TextView(getActivity());

            tv.setLayoutParams(paramsExample);


            tv.setText(Html.fromHtml(line));

            row.addView(label);
            row.addView(tv);

            table.addView(row);
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                _listener.fragmentSelect(MainActivityFragmentEnum.NAVIGATION_PREVIEW, getArguments());
                break;
            default:
                throw new IllegalArgumentException("unknown menu option");
        }

        return true;
    }
}





