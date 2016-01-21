package com.guggiemedia.fibermetric.ui.main;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import net.go_factory.tundro.R;



import com.guggiemedia.fibermetric.lib.chain.CommandFacade;
import com.guggiemedia.fibermetric.lib.db.ChartModel;
import com.guggiemedia.fibermetric.lib.db.JobStateEnum;
import com.guggiemedia.fibermetric.lib.db.JobTaskModel;
import com.guggiemedia.fibermetric.lib.db.SiteModel;
import com.guggiemedia.fibermetric.lib.db.TaskActionModel;
import com.guggiemedia.fibermetric.lib.db.TaskActionModelList;
import com.guggiemedia.fibermetric.lib.db.TaskActionStateEnum;
import com.guggiemedia.fibermetric.lib.db.TaskDetailModelList;
import com.guggiemedia.fibermetric.ui.chart.ChartActivity;
import com.guggiemedia.fibermetric.ui.utility.ToastHelper;


/**
 * display specified task
 */
public class TaskViewFragment extends Fragment {

    public static final String FRAGMENT_TAG = "FRAGMENT_TASK_VIEW";

    public static final String LOG_TAG = TaskViewFragment.class.getName();

    public static final String ARG_PARAM_TASK_ID = "PARAM_TASK_ID";

    private MainActivityListener _listener;

    private MainActivityFragmentEnum _paramParent = MainActivityFragmentEnum.UNKNOWN;

    private long _paramTaskId = 0L;

    private View _view;

    private ImageView _taskStatusImage;
    private ImageView _imageView2;

    private android.support.design.widget.FloatingActionButton _fab;

    private ListView _requiredActionList;
    private ListView _lvDetail;

    private TextView _tvAsset;

    private TextView _tvJobId;
    private TextView _tvTaskName;
    private TextView _tvTaskStatus;
    private TextView _tvSite;

    private JobTaskModel _taskModel;
    private TaskActionModelList _actionList;
    private TaskDetailModelList _detailList;

    private SiteModel _siteModel;

    /**
     * Use this factory method to create a new instance of fragment.
     *
     * @param args
     * @return A new instance of fragment TaskViewFragment.
     */
    public static TaskViewFragment newInstance(Bundle args) {
        TaskViewFragment fragment = new TaskViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public TaskViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        _listener = (MainActivityListener) getActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate");

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            _paramTaskId = getArguments().getLong(ARG_PARAM_TASK_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_task_view, container, false);

        _view = view;

        _taskStatusImage = (ImageView) view.findViewById(R.id.taskStatusImage);
        _imageView2 = (ImageView) view.findViewById(R.id.imageView2);
        _imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle oldBundle = new Bundle();

                oldBundle.putLong(JobViewFragment.ARG_PARAM_JOB_ID, _taskModel.getId());

                Bundle newBundle = new Bundle();

                newBundle.putSerializable(ChartFragment.ARG_PARAM_CHART_TYPE, ChartModel.ChartType.jobTask);
                newBundle.putLong(ChartFragment.ARG_PARAM_ROW_ID, _taskModel.getId());
                newBundle.putSerializable(ChartFragment.ARG_PARAM_PARENT, _paramParent);

                Bundle bundle = new Bundle();


                bundle.putBundle(ChartActivity.ARG_OLD_BUNDLE, oldBundle);

                bundle.putBundle(ChartActivity.ARG_NEW_BUNDLE, newBundle);

                _listener.activitySelect(MainActivityEnum.CHART_ACTIVITY, bundle);
            }
        });

        _tvTaskName = (TextView) view.findViewById(R.id.tvTaskName);

        _tvTaskStatus = (TextView) view.findViewById(R.id.tvTaskStatus);
        _tvJobId = (TextView) view.findViewById(R.id.tvJobId);
        _tvAsset = (TextView) view.findViewById(R.id.tvAsset);
        _tvSite = (TextView) view.findViewById(R.id.tvSite);

        _requiredActionList = (ListView) view.findViewById(R.id.requiredActionList);
        _requiredActionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TaskActionModel model = _actionList.get(position);

                Bundle bundle = new Bundle();
                bundle.putLong(TaskPagerFragment.ARG_PARAM_ACTION_ID, model.getId());
                bundle.putLong(TaskPagerFragment.ARG_PARAM_JOB_ID, _taskModel.getParent());
                bundle.putLong(TaskPagerFragment.ARG_PARAM_TASK_ID, _taskModel.getId());

                switch (model.getAction()) {
                    case AUDIO_RECORD:
                        break;
                    case CAMERA:
                        _listener.activitySelect(MainActivityEnum.IMAGE_ACTIVITY, bundle);
                        break;
                    case CAPTURE_INTEGER:
                        bundle.putString(CaptureDialog.ARG_PARAM_BANNER, model.getDescription());
                        bundle.putString(CaptureDialog.ARG_PARAM_TITLE, "Capture Usage");
                        bundle.putSerializable(CaptureDialog.ARG_PARAM_DIALOG, MainActivityDialogEnum.CAPTURE_INTEGER);
                        _listener.dialogSelect(MainActivityDialogEnum.CAPTURE_INTEGER, bundle);
                        break;
                    case CAPTURE_NOTE:
                        bundle.putString(CaptureDialog.ARG_PARAM_BANNER, model.getDescription());
                        bundle.putString(CaptureDialog.ARG_PARAM_TITLE, "Capture Usage");
                        bundle.putSerializable(CaptureDialog.ARG_PARAM_DIALOG, MainActivityDialogEnum.CAPTURE_NOTE);
                        _listener.dialogSelect(MainActivityDialogEnum.CAPTURE_NOTE, bundle);
                        break;
                    case SCAN_BARCODE:
                        _listener.activitySelect(MainActivityEnum.ADD_BARCODE, bundle);
                        break;
                    default:
                        Log.i(LOG_TAG, "unknown action:" + model.getAction());
                }
            }
        });

        _lvDetail = (ListView) view.findViewById(R.id.lvTaskDetail);

        _fab = (android.support.design.widget.FloatingActionButton) view.findViewById(R.id.fab);

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();

        Log.d(LOG_TAG, "onResume w/task:" + _paramTaskId);

        _taskModel = CommandFacade.jobTaskSelectByRowId(_paramTaskId, getActivity());
        _siteModel = CommandFacade.siteSelectByRowId(_taskModel.getSite(), getActivity());

        _actionList = CommandFacade.taskActionSelectByParent(_paramTaskId, getActivity());

        _requiredActionList.setAdapter(new TaskActionArrayAdapter(_actionList, getActivity()));

        _detailList = CommandFacade.taskDetailSelectByParent(_paramTaskId, getActivity());
        _lvDetail.setAdapter(new TaskDetailArrayAdapter(_detailList, getActivity()));

        setListViewHeight(_requiredActionList);
        setListViewHeight(_lvDetail);

        _tvAsset.setText(_taskModel.getAsset());

        _tvJobId.setText(_taskModel.getTicket());
        _tvTaskName.setText(_taskModel.getName());
        _tvTaskStatus.setText(_taskModel.getState().toString());

        _tvSite.setText(_siteModel.getName());

        _fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check  that required actions are attempted/completed
                if (!actionsCompleted()) {
                    ToastHelper.show(_view,
                            getActivity().getString(R.string.toastRequiredActionsNotCompleted));
                } else {
                    CommandFacade.jobTaskComplete(_taskModel.getId(), getActivity());

                    Bundle bundle = new Bundle();
                    bundle.putLong(TaskPagerFragment.ARG_PARAM_JOB_ID, _taskModel.getParent());

                    bundle.putLong(TaskPagerFragment.ARG_PARAM_COMPLETED_TASK_ID, _paramTaskId);

                    _listener.fragmentSelect(MainActivityFragmentEnum.TASK_PAGER, bundle);
                }
            }
        });

        if (_taskModel.getState().equals(JobStateEnum.COMPLETE)) {
            _fab.hide();
        }

        Integer taskImageResourceId = null;

        switch (_taskModel.getState()) {
            case ACTIVE:
                taskImageResourceId = R.drawable.ic_task_active;
                break;
            case COMPLETE:
                taskImageResourceId = R.drawable.ic_task_completed;
                break;
            case SCHEDULE:
                taskImageResourceId = R.drawable.ic_task_scheduled;
                break;
            case SUSPEND:
                taskImageResourceId = R.drawable.ic_task_suspended;
                break;
            default:
                Log.i(LOG_TAG, "unknown state:" + _taskModel.getState() + ":row:" + _taskModel.getId());
        }

        if (taskImageResourceId != null) {
            _taskStatusImage.setImageBitmap(BitmapFactory.decodeResource(getActivity().getResources(), taskImageResourceId));
        }
    }

    /**
     * @return
     */
    private boolean actionsCompleted() {
        boolean completed = true;

        if (!_actionList.isEmpty()) {
            for (TaskActionModel model : _actionList) {
                if (!model.getState().equals(TaskActionStateEnum.COMPLETE)
                        && !model.getState().equals(TaskActionStateEnum.CANCELED)) {
                    completed = false;
                    break;
                }
            }
        }

        return completed;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy:" + _paramTaskId);
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
