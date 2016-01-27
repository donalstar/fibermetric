package com.guggiemedia.fibermetric.ui.main;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.guggiemedia.fibermetric.R;
import com.guggiemedia.fibermetric.lib.chain.CommandFacade;
import com.guggiemedia.fibermetric.lib.db.ChartModel;
import com.guggiemedia.fibermetric.lib.db.JobTaskModel;
import com.guggiemedia.fibermetric.lib.db.JobTaskModelList;
import com.guggiemedia.fibermetric.lib.db.SiteModel;
import com.guggiemedia.fibermetric.lib.db.TurnByTurnDirectionsModel;
import com.guggiemedia.fibermetric.ui.utility.DirectionsLookup;


import java.util.List;

public class JobViewFragment extends Fragment {
    public static final String FRAGMENT_TAG = "FRAGMENT_JOB_VIEW";

    public static final String LOG_TAG = JobViewFragment.class.getName();

    public static final String ARG_PARAM_JOB_ID = "PARAM_JOB_ID";

    private MainActivityListener _listener;

    private long _paramJobId = 0L;

    private MainActivityFragmentEnum _paramParent = MainActivityFragmentEnum.UNKNOWN;

    private ImageView _imageView1;
    private ImageView _locationImage;

    private ListView _lvTasks;

    private TextView _tvAsset;
    private TextView _tvAssignedBy;
    private TextView _tvDeadLine;
    private TextView _tvDescription;
    private TextView _tvJobDuration;
    private TextView _tvJobId;
    private TextView _tvJobName;
    private TextView _tvJobStatus;
    private TextView _tvJobType;
    private TextView _tvProgress;
    private TextView _tvSite;

    private JobTaskModel _jobModel;
    private JobTaskModelList _taskList;
    private SiteModel _siteModel;
    private List<ChartModel.MarkerData> _markerData;
    private TurnByTurnDirectionsModel _turnByTurnDirectionsModel;

    private Button _moreLessButton;
    private boolean _bogusToggle;
    private RelativeLayout _bogusLayout1;
    private RelativeLayout _bogusLayout2;

    private android.support.design.widget.FloatingActionButton _fab;

    /**
     * view jobTask detail
     *
     * @param args jobTask to display
     * @return A new instance of fragment JobViewFragment.
     */
    public static JobViewFragment newInstance(Bundle args) {
        JobViewFragment fragment = new JobViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        _listener = (MainActivityListener) getActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        if (getArguments() != null) {
            _paramJobId = getArguments().getLong(ARG_PARAM_JOB_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_job_view, container, false);

        _imageView1 = (ImageView) view.findViewById(R.id.taskStatusImage);
        _locationImage = (ImageView) view.findViewById(R.id.locationImage);


        _tvJobName = (TextView) view.findViewById(R.id.tvJobName);
        _tvJobStatus = (TextView) view.findViewById(R.id.tvJobStatus);
        _tvJobId = (TextView) view.findViewById(R.id.tvJobId);
        _tvAssignedBy = (TextView) view.findViewById(R.id.tvAssignedBy);
        _tvDeadLine = (TextView) view.findViewById(R.id.tvDeadLine);
        _tvAsset = (TextView) view.findViewById(R.id.tvAsset);
        _tvJobType = (TextView) view.findViewById(R.id.tvJobType);
        _tvJobDuration = (TextView) view.findViewById(R.id.tvJobDuration);
        _tvSite = (TextView) view.findViewById(R.id.tvSite);
        _tvDescription = (TextView) view.findViewById(R.id.tvDescriptionText);
        _tvProgress = (TextView) view.findViewById(R.id.tvProgress);

        _fab = (android.support.design.widget.FloatingActionButton) view.findViewById(R.id.fab);

        _fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "job model get state:" + _jobModel.getState());

                JobTaskModelList activeList = new JobTaskModelList();
                Bundle args = new Bundle();

                // FAB action is job state dependent
                switch (_jobModel.getState()) {


                    default:
                        Log.i(LOG_TAG, "unknown state:" + _jobModel.getState() + ":row:" + _jobModel.getId());
                }

            }
        });

        _lvTasks = (ListView) view.findViewById(R.id.lvTasks);
        _lvTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                JobTaskModel task = _taskList.get(position);

                Bundle newBundle = new Bundle();


                Bundle oldBundle = new Bundle();
                oldBundle.putLong(JobViewFragment.ARG_PARAM_JOB_ID, task.getParent());

                _listener.fragmentPush(MainActivityFragmentEnum.JOB_VIEW, oldBundle, MainActivityFragmentEnum.TASK_PAGER, newBundle);
            }
        });

        _bogusLayout1 = (RelativeLayout) view.findViewById(R.id.bogusContent1);
        _bogusLayout2 = (RelativeLayout) view.findViewById(R.id.bogusContent2);

        _moreLessButton = (Button) view.findViewById(R.id.moreLessButton);
        _moreLessButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (_bogusToggle) {
                    _bogusToggle = false;
                    _bogusLayout1.setVisibility(View.VISIBLE);
                    _bogusLayout2.setVisibility(View.VISIBLE);
                    _moreLessButton.setText(getString(R.string.labelLess));
                } else {
                    _bogusToggle = true;
                    _bogusLayout1.setVisibility(View.GONE);
                    _bogusLayout2.setVisibility(View.GONE);
                    _moreLessButton.setText(getString(R.string.labelMore));
                }
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(LOG_TAG, "onActivityCreated");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.job_view_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(LOG_TAG, "onOptionsItemSelected:" + item);

        Bundle oldBundle = new Bundle();
        oldBundle.putLong(JobViewFragment.ARG_PARAM_JOB_ID, _jobModel.getId());

        Bundle newBundle = new Bundle();

        switch (item.getItemId()) {
            case android.R.id.home:
                _listener.fragmentPop();
                break;
            case R.id.actionChat:
                _listener.fragmentPush(MainActivityFragmentEnum.JOB_VIEW, oldBundle, MainActivityFragmentEnum.ESCALATION_FORM, newBundle);
                break;
            case R.id.actionToolBox:
                newBundle.putLong(ARG_PARAM_JOB_ID, _paramJobId);
                _listener.fragmentPush(MainActivityFragmentEnum.JOB_VIEW, oldBundle, MainActivityFragmentEnum.REQUIRED_INVENTORY_VIEW, newBundle);
                break;
            case R.id.actionHelp:
                _listener.fragmentPush(MainActivityFragmentEnum.JOB_VIEW, oldBundle, MainActivityFragmentEnum.HELP_VIEW, newBundle);
                break;
            case R.id.actionFeedBack:
                _listener.fragmentPush(MainActivityFragmentEnum.JOB_VIEW, oldBundle, MainActivityFragmentEnum.ESCALATION_FORM, newBundle);
                break;
            default:
                throw new IllegalArgumentException("unknown menu option");
        }

        return true;
    }

    @Override
    public void onResume() {
        super.onResume();

        _jobModel = CommandFacade.jobTaskSelectByRowId(_paramJobId, getActivity());
        _taskList = CommandFacade.jobTaskSelectByParent(_paramJobId, getActivity());
        _siteModel = CommandFacade.siteSelectByRowId(_jobModel.getSite(), getActivity());

        Log.d(LOG_TAG, "onResume w/task:" + _paramJobId + ":" + _jobModel.getState());

        _lvTasks.setAdapter(new JobTaskArrayAdapter(_taskList, getActivity()));

        setListViewHeight(_lvTasks);

        _tvAsset.setText(_jobModel.getAsset());
        _tvAssignedBy.setText(_jobModel.getAssignedBy());
        _tvDeadLine.setText(_jobModel.formatter2(_jobModel.getDeadLine()));
        _tvDescription.setText(_jobModel.getDescription());
        _tvJobDuration.setText(_jobModel.formatter3(_jobModel.getDuration()));
        _tvJobId.setText(_jobModel.getTicket());
        _tvJobName.setText(_jobModel.getName());
        _tvJobStatus.setText(_jobModel.getState().toString());
        _tvJobType.setText(_jobModel.getType().toString());
        _tvProgress.setText("101%");  //FIXME someday Allan will tell me what this means

        _tvSite.setText(_siteModel.getName());

        switch (_jobModel.getState()) {
            case ACTIVE:
                _fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.tomato)));
                _fab.setImageBitmap(BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.ic_pause_white));
                _imageView1.setImageBitmap(BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.ic_job_active_list));
                break;
            case COMPLETE:
                _fab.hide();
                _imageView1.setImageBitmap(BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.ic_task_completed_list40x40));
                break;
            case SCHEDULE:
                _fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.mid_green)));
                _imageView1.setImageBitmap(BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.ic_task_scheduled_list_40x40));
                break;
            case SUSPEND:
                _fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.mid_green)));
                _imageView1.setImageBitmap(BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.ic_job_suspended_list));
                break;
            default:
                Log.i(LOG_TAG, "unknown state:" + _jobModel.getState() + ":row:" + _jobModel.getId());
        }

        // for maps & navigation
        _markerData = CommandFacade.getChartMarkerData(_jobModel.getId(), ChartModel.ChartType.jobTask, null, getActivity());

        Location location = _markerData.get(0).location;

        Double locationLatLng[] = {location.getLatitude(), location.getLongitude()};

        new DirectionsLookup(getActivity(), new DirectionsLookup.DirectionsLookupListener() {
            @Override
            public void setResult(TurnByTurnDirectionsModel result) {
                _turnByTurnDirectionsModel = result;
            }
        }).execute(locationLatLng);
    }

    /**
     * ListView is wrapped inside ScrollView which only displays one row (in ListView)
     * Must calculate true ListView height to display all rows
     *
     * @param listView
     */
    private void setListViewHeight(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            throw new NullPointerException("missing list adapter");
        }

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) listView.getLayoutParams();
        params.height = listAdapter.getCount() * getResources().getDimensionPixelSize(R.dimen.job_task_row_height);
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}
