package com.guggiemedia.fibermetric.ui.main;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.guggiemedia.fibermetric.lib.chain.CommandFacade;

import net.go_factory.tundro.R;



/**
 * SuspendDialog1 handles the use case of suspending the current job.
 * SuspendDialog2 supports use case of suspending a job because another job has been selected.
 * There can only be zero or one active jobs known to the device.
 */
public class SuspendDialog2 extends DialogFragment {
    public static final String FRAGMENT_TAG = "FRAGMENT_SUSPEND2";

    public static final String LOG_TAG = SuspendDialog2.class.getName();

    public static final String ARG_PARAM_JOB_ID = "PARAM_JOB_ID";
    public static final String ARG_PARAM_SUSPEND_JOB_ID = "PARAM_SUSPEND_JOB_ID";

    private long _paramJobId = 0L;
    private long _paramSuspendJobId = 0L;

    private MainActivityListener _listener;

    public static SuspendDialog2 newInstance(Bundle bundle)  {
        SuspendDialog2 fragment = new SuspendDialog2();
        fragment.setArguments(bundle);
        return fragment;
    }

    public SuspendDialog2() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        _listener = (MainActivityListener) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            _paramJobId = getArguments().getLong(ARG_PARAM_JOB_ID);
            _paramSuspendJobId = getArguments().getLong(ARG_PARAM_SUSPEND_JOB_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.dialog_suspend2, container, false);

        getDialog().setTitle(R.string.labelJobSuspend1);

        Button buttonNo = (Button) view.findViewById(R.id.buttonNo);
        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();  //invokes onDismiss()
            }
        });

        Button buttonYes = (Button) view.findViewById(R.id.buttonYes);
        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommandFacade.jobTaskSuspend(_paramSuspendJobId, getActivity());
                CommandFacade.jobTaskActive(_paramJobId, getActivity());

                dismiss();  //invokes onDismiss()

                Bundle args = getArguments();
                if (args.containsKey(ChartFragment.ARG_PARAM_SITE_NAME)) {
                    _listener.fragmentSelect(MainActivityFragmentEnum.TURN_BY_TURN_VIEW, args);
                } else {
                    args = new Bundle();
                    args.putLong(TaskPagerFragment.ARG_PARAM_JOB_ID, _paramJobId);
  //                  args.putLong(TaskPagerFragment.ARG_PARAM_TASK_ID, CommandFacade.jobTaskNext(_paramJobId, getActivity()).getId());
                    _listener.fragmentSelect(MainActivityFragmentEnum.TASK_PAGER, args);
                }
            }
        });

        return view;
    }

    /*
    public void onDismiss(DialogInterface arg) {
        System.out.println("dismiss:" + arg);
    }
    */
}
