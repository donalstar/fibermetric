package com.guggiemedia.fibermetric.ui.main;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.guggiemedia.fibermetric.lib.chain.CommandFacade;
import com.guggiemedia.fibermetric.lib.db.TaskActionStateEnum;

import net.go_factory.tundro.R;


/**
 *
 */
public class CaptureDialog extends DialogFragment {
    public static final String FRAGMENT_TAG = "FRAGMENT_CAPTURE";

    public static final String LOG_TAG = CaptureDialog.class.getName();

    public static final String ARG_PARAM_BANNER = "PARAM_BANNER";
    public static final String ARG_PARAM_TITLE = "PARAM_TITLE";
    public static final String ARG_PARAM_DIALOG = "PARAM_DIALOG";

    private EditText _editText;
    private TextView _tvBanner;

    private String _paramBanner;
    private String _paramTitle;
    private MainActivityDialogEnum _dialogEnum;

    private MainActivityListener _listener;

    public static CaptureDialog newInstance(String title, String banner, MainActivityDialogEnum dialogEnum) {
        CaptureDialog fragment = new CaptureDialog();

        Bundle bundle = new Bundle();
        bundle.putString(ARG_PARAM_TITLE, title);
        bundle.putString(ARG_PARAM_BANNER, banner);
        bundle.putSerializable(ARG_PARAM_DIALOG, dialogEnum);
        fragment.setArguments(bundle);

        return fragment;
    }

    public static CaptureDialog newInstance(Bundle bundle) {
        CaptureDialog fragment = new CaptureDialog();
        fragment.setArguments(bundle);
        return fragment;
    }

    public CaptureDialog() {
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
            _paramBanner = getArguments().getString(ARG_PARAM_BANNER);
            _paramTitle = getArguments().getString(ARG_PARAM_TITLE);
            _dialogEnum = (MainActivityDialogEnum) getArguments().getSerializable(ARG_PARAM_DIALOG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.dialog_capture, container, false);

        getDialog().setTitle(_paramTitle);

        _tvBanner = (TextView) view.findViewById(R.id.tvBanner1);
        _tvBanner.setText(_paramBanner);

        _editText = (EditText) view.findViewById(R.id.editCapture);
        if (_dialogEnum == MainActivityDialogEnum.CAPTURE_INTEGER) {
            _editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        }

        Button buttonCancel = (Button) view.findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();

                updateTaskStatus(TaskActionStateEnum.CANCELED);
            }
        });

        Button buttonDone = (Button) view.findViewById(R.id.buttonDone);
        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();  //invokes onDismiss()

                //TODO save entered value
                updateTaskStatus(TaskActionStateEnum.COMPLETE);
            }
        });

        return view;
    }

    /**
     * @param status
     */
    private void updateTaskStatus(TaskActionStateEnum status) {
        long actionId = getArguments().getLong(TaskPagerFragment.ARG_PARAM_ACTION_ID);
        CommandFacade.taskActionUpdate(status, actionId, getActivity());

        // explicitly navigate back to task to force update
        _listener.fragmentSelect(MainActivityFragmentEnum.TASK_PAGER, getArguments());
    }
}

