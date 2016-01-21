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
public class SuspendDialog1 extends DialogFragment {
    public static final String FRAGMENT_TAG = "FRAGMENT_SUSPEND1";

    public static final String LOG_TAG = SuspendDialog1.class.getName();

    public static final String ARG_PARAM_JOB_ID = "PARAM_JOB_ID";

    private long _paramJobId = 0L;

    private MainActivityListener _listener;

    public static SuspendDialog1 newInstance(Bundle bundle)  {
        SuspendDialog1 fragment = new SuspendDialog1();
        fragment.setArguments(bundle);
        return fragment;
    }

    public SuspendDialog1() {
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.dialog_suspend1, container, false);

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
                CommandFacade.jobTaskSuspend(_paramJobId, getActivity());
                dismiss();  //invokes onDismiss()

                // redirect to force view refresh w/suspended icon
                _listener.fragmentSelect(MainActivityFragmentEnum.JOB_TODAY_LIST, new Bundle());
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
