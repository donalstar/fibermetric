package com.guggiemedia.fibermetric.ui.main;

import android.app.Activity;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RatingBar;

import com.guggiemedia.fibermetric.R;
import com.guggiemedia.fibermetric.lib.chain.CommandFacade;
import com.guggiemedia.fibermetric.lib.db.JobStateEnum;
import com.guggiemedia.fibermetric.lib.db.JobTaskModel;
import com.guggiemedia.fibermetric.ui.utility.ToastHelper;



import java.util.Date;

/**
 * rate completed task
 */
public class TaskEndFragment extends Fragment {
    public static final String FRAGMENT_TAG = "FRAGMENT_TASK_END";

    public static final String LOG_TAG = TaskEndFragment.class.getName();

    public static final String ARG_PARAM_JOB_ID = "PARAM_JOB_ID";

    private MainActivityListener _listener;

    private long _paramJobId = 0L;

    private android.support.design.widget.FloatingActionButton _fabTaskEnd;

    private boolean _completeJob = true;

    /**
     * Use this factory method to create a new instance of fragment.
     *
     * @param args
     * @return A new instance of fragment TaskViewFragment.
     */
    public static TaskEndFragment newInstance(Bundle args) {
        TaskEndFragment fragment = new TaskEndFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public TaskEndFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        _listener = (MainActivityListener) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate");

        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        if (getArguments() != null) {
            _paramJobId = getArguments().getLong(ARG_PARAM_JOB_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_task_end, container, false);

        final RatingBar ratingBar = (RatingBar) view.findViewById(R.id.rbTaskEnd);

        Drawable drawable = ratingBar.getProgressDrawable();

        int ratingsStarColor = R.color.marigold;

        drawable.setColorFilter(getResources().getColor(ratingsStarColor), PorterDuff.Mode.SRC_ATOP);

        final EditText commentEdit = (EditText) view.findViewById(R.id.commentEdit);

        _fabTaskEnd = (android.support.design.widget.FloatingActionButton) view.findViewById(R.id.fabTaskEnd);
        _fabTaskEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String commentText = commentEdit.getText().toString();

                JobTaskModel jobTaskModel = CommandFacade.jobTaskSelectByRowId(_paramJobId, getActivity());

                jobTaskModel.setState(JobStateEnum.COMPLETE);
                jobTaskModel.setUpdateTime(new Date());

                jobTaskModel.setComment(commentText);

                jobTaskModel.setStarRating((int) ratingBar.getRating());

                getActivity().getWindow().setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

                imm.hideSoftInputFromWindow(commentEdit.getWindowToken(), 0);

                commentEdit.setText("");

                String message = String.format(
                        getActivity().getResources().getString(R.string.taskCompleted), jobTaskModel.getName());

                final String inProgressMessage = String.format(
                        getActivity().getResources().getString(R.string.taskInProgress), jobTaskModel.getName());

                final JobTaskModel savedJobTaskModel = jobTaskModel;

                View.OnClickListener listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        resetCompleteStatus();

                        ToastHelper.showSnackbar(getActivity(), inProgressMessage);
                    }
                };


                ToastHelper.showSnackbar(getActivity(), message,
                        getResources().getString(R.string.undo_button),
                        listener, null);
            }
        });

        return view;
    }

    /**
     * Should we *really* complete the job
     */
    private boolean getCompleteStatus() {
        return _completeJob;
    }

    private void resetCompleteStatus() {
        _completeJob = false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(LOG_TAG, "onOptionsItemSelected:" + item);

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
