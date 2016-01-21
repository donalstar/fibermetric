package com.guggiemedia.fibermetric.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import net.go_factory.tundro.R;

import com.guggiemedia.fibermetric.lib.chain.CommandFacade;
import com.guggiemedia.fibermetric.lib.chain.JobTaskDurationCtx;
import com.guggiemedia.fibermetric.lib.db.ChartModel;
import com.guggiemedia.fibermetric.lib.db.JobTaskModelList;
import com.guggiemedia.fibermetric.ui.chart.ChartActivity;
import com.guggiemedia.fibermetric.ui.component.RecyclerViewEmptySupport;


public class JobListFragment extends Fragment {
    public static final String FRAGMENT_TAG = "FRAGMENT_JOB_LIST";

    public static final String LOG_TAG = JobListFragment.class.getName();

    // display only jobs matching today date else all known jobs
    public static final String ARG_PARAM_TODAY = "PARAM_TODAY";

    private boolean _paramToday = false;

    private JobListAdapter _adapter;
    private MainActivityListener _listener;

    private ProgressBar _progressBar;
    private TextView _progressValue;

    private JobTaskModelList _jobList = new JobTaskModelList();

    public static final int LOADER_ID = 271828;


    public static Fragment newInstance(Bundle args) {
        JobListFragment fragment = new JobListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        JobTaskModelList temp = CommandFacade.jobTaskSelectToday(getActivity());

        _adapter = new JobListAdapter(temp, MainActivityFragmentEnum.JOB_TODAY_LIST, getActivity());
        _listener = (MainActivityListener) getActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            _paramToday = getArguments().getBoolean(ARG_PARAM_TODAY);
        }

        if (_paramToday) {
            _jobList = CommandFacade.jobTaskSelectToday(getActivity());
        }

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_job_list, container, false);

        _progressBar = (ProgressBar) view.findViewById(R.id.pbJobProgress);
        _progressValue = (TextView) view.findViewById(R.id.tvProgressValue);

        RecyclerViewEmptySupport recyclerView = (RecyclerViewEmptySupport)view.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setEmptyView(view.findViewById(R.id.list_empty));

        recyclerView.setAdapter(_adapter);

        final SwipeRefreshLayout swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                _adapter.refresh(CommandFacade.jobTaskSelectToday(getActivity()));
                swipeRefresh.setRefreshing(false);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setProgressValue();
    }


    @Override
    public void onDetach() {
        super.onDetach();
        _listener = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.job_list_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(LOG_TAG, "onOptionsItemSelected:" + item);

        switch (item.getItemId()) {
            case android.R.id.home:
                _listener.navDrawerOpen(true);
                break;
            case R.id.actionChart:
                Bundle newBundle = new Bundle();

                newBundle.putSerializable(ChartFragment.ARG_PARAM_CHART_TYPE, ChartModel.ChartType.jobSites);

                newBundle.putBoolean(JobListFragment.ARG_PARAM_TODAY, _paramToday);

                Bundle bundle = new Bundle();

                bundle.putBundle(ChartActivity.ARG_NEW_BUNDLE, newBundle);

                _listener.activitySelect(MainActivityEnum.CHART_ACTIVITY, bundle);
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
            default:
                throw new IllegalArgumentException("unknown menu option");
        }

        return true;
    }

    // set progress bar value, see github issue 107
    private void setProgressValue() {
        JobTaskDurationCtx durationCtx = CommandFacade.jobTaskDuration(_paramToday, getActivity());

        int progress = 0;
        if (durationCtx.getTotalMinutes() > 0) {
            int delta = durationCtx.getTotalMinutes() - durationCtx.getCompleteMinutes();
            progress = 100 - 100 * delta / durationCtx.getTotalMinutes();
        }

        _progressBar.setProgress(progress);
        _progressValue.setText(Integer.toString(progress) + "%");
    }
}