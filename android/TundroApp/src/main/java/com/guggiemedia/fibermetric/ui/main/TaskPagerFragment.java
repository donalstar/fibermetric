package com.guggiemedia.fibermetric.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import net.go_factory.tundro.R;

import com.guggiemedia.fibermetric.lib.chain.CommandFacade;
import com.guggiemedia.fibermetric.lib.db.JobTaskModel;
import com.guggiemedia.fibermetric.lib.db.KeyList;
import com.guggiemedia.fibermetric.ui.utility.ToastHelper;


/**
 *
 */
public class TaskPagerFragment extends Fragment {
    public static final String FRAGMENT_TAG = "FRAGMENT_TASK_PAGER";

    public static final String LOG_TAG = TaskPagerFragment.class.getName();

    public static final String ARG_PARAM_ACTION_ID = "PARAM_ACTION_ID";
    public static final String ARG_PARAM_JOB_ID = "PARAM_JOB_ID";
    public static final String ARG_PARAM_TASK_ID = "PARAM_TASK_ID";
    public static final String ARG_PARAM_COMPLETED_TASK_ID = "PARAM_COMPLETED_TASK_ID";

    private MainActivityListener _listener;

    private long _paramJobId = 0L;
    private long _paramTaskId = 0L;

    private TaskPagerAdapter _taskPagerAdapter;
    private PagerTabStrip _pagerTabStrip;
    private ViewPager _viewPager;

    private KeyList _keyList;

    /**
     * Use this factory method to create a new fragment instance
     *
     * @param args
     * @return new instance of TaskPagerFragment
     */
    public static TaskPagerFragment newInstance(Bundle args) {
        TaskPagerFragment fragment = new TaskPagerFragment();
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
            _keyList = CommandFacade.jobTaskSelectByJobId(_paramJobId, getActivity());
            _paramTaskId = getArguments().getLong(ARG_PARAM_TASK_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_pager, container, false);

        _pagerTabStrip = (PagerTabStrip) view.findViewById(R.id.taskTabStrip);
        _viewPager = (ViewPager) view.findViewById(R.id.taskPager);

        _taskPagerAdapter = new TaskPagerAdapter(_keyList, getActivity());
        _viewPager.setAdapter(_taskPagerAdapter);

        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.task_pager_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(LOG_TAG, "onOptionsItemSelected:" + item);

        Bundle oldBundle = new Bundle();
        oldBundle.putLong(TaskPagerFragment.ARG_PARAM_JOB_ID, _paramJobId);

        Bundle newBundle = new Bundle();

        switch (item.getItemId()) {
            case android.R.id.home:
                Bundle bundle = new Bundle();
                bundle.putLong(JobViewFragment.ARG_PARAM_JOB_ID, _paramJobId);

                _listener.fragmentSelect(MainActivityFragmentEnum.JOB_VIEW, bundle);
                break;
            case R.id.actionChat:
                _listener.fragmentPush(MainActivityFragmentEnum.JOB_VIEW, oldBundle, MainActivityFragmentEnum.ESCALATION_FORM, newBundle);
                break;
            case R.id.actionToolBox:
                _listener.fragmentPush(MainActivityFragmentEnum.JOB_VIEW, oldBundle, MainActivityFragmentEnum.STUB_VIEW, newBundle);
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

        Log.d(LOG_TAG, "onResume w/task:" + _paramTaskId);

        if (_paramTaskId > 0L) {
            // navigate directly to specified task
            _viewPager.setCurrentItem(tabNdxFromRowId(_paramTaskId));
        } else {
            // discover next task and navigate
            final JobTaskModel nextModel = CommandFacade.jobTaskNext(_paramJobId, getActivity());
            if (nextModel.getId() < 1L) {
                // next task not found
                Bundle bundle = new Bundle();
                bundle.putLong(TaskEndFragment.ARG_PARAM_JOB_ID, _paramJobId);

                // note that PARAM_PARENT means navigation breadcrumb to JOB_VIEW and not TASK_PAGER
                //    bundle.putSerializable(TaskEndFragment.ARG_PARAM_PARENT, _paramParent);

                // note that TASK_END will fragmentPop
                _listener.fragmentSelect(MainActivityFragmentEnum.TASK_END, bundle);
            } else {
                _viewPager.setCurrentItem(tabNdxFromRowId(nextModel.getId()));
            }

            Long currentTaskId
                    = getArguments().getLong(TaskPagerFragment.ARG_PARAM_COMPLETED_TASK_ID);

            final JobTaskModel jobTaskModel = CommandFacade.jobTaskSelectByRowId(currentTaskId, getActivity());

            String message = String.format(
                    getActivity().getResources().getString(R.string.taskCompleted), jobTaskModel.getName());

            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CommandFacade.jobTaskScheduled(nextModel.getId(), getActivity());

                    CommandFacade.jobTaskActive(jobTaskModel.getId(), getActivity());

                    String message = String.format(
                            getActivity().getResources().getString(R.string.taskInProgress), jobTaskModel.getName());

                    _taskPagerAdapter.notifyDataSetChanged();
                    _viewPager.setCurrentItem(tabNdxFromRowId(jobTaskModel.getId()));

                    ToastHelper.showSnackbar(getActivity(), message);
                }
            };

            int messageDuration = 3000; // millis

            ToastHelper.showSnackbar(getActivity(), message,
                    getResources().getString(R.string.undo_button),
                    listener, null, messageDuration);
        }
    }

    /**
     * discover tab position from database row ID
     *
     * @param arg database row ID
     * @return tab position
     */
    private int tabNdxFromRowId(long arg) {
        int tabNdx = 0;

        for (long candidate : _keyList) {
            if (candidate == arg) {
                break;
            } else {
                ++tabNdx;
            }
        }

        return tabNdx;
    }
}
