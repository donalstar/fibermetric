package com.guggiemedia.fibermetric.ui.main;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.go_factory.tundro.R;

import com.guggiemedia.fibermetric.lib.chain.CommandFacade;
import com.guggiemedia.fibermetric.lib.db.PartModel;
import com.guggiemedia.fibermetric.ui.utility.ToastHelper;


public class ReportFragment extends Fragment {
    public static final String FRAGMENT_TAG = "FRAGMENT_REPORT_VIEW";

    public static final String LOG_TAG = ReportFragment.class.getName();

    private MainActivityListener _listener;
    private PartModel _model;

    public static ReportFragment newInstance() {
        return new ReportFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        _listener = (MainActivityListener) activity;

        Long id = getArguments().getLong(PartFragment.ARG_PARAM_ROW_ID);

        _model = CommandFacade.partSelectByRowId(id, getActivity());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report, container, false);

        TextView partTitle = (TextView) view.findViewById(R.id.partName);

        partTitle.setText(_model.getName());

        ImageView photo = (ImageView) view.findViewById(R.id.photo);

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastHelper.show("Add photo!", getActivity());
            }
        });

        TextView damageInfo = (TextView) view.findViewById(R.id.damageInfo);

        damageInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastHelper.show("Add damage info...", getActivity());
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.report_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(LOG_TAG, "onOptionsItemSelected:" + item);

        switch (item.getItemId()) {
            case android.R.id.home:
                _listener.fragmentPop();
                break;
            case R.id.actionSearch:
                break;
            default:
                throw new IllegalArgumentException("unknown menu option");
        }

        return true;
    }
}
