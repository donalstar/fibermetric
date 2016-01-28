package com.guggiemedia.fibermetric.ui.main;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.guggiemedia.fibermetric.R;
import com.guggiemedia.fibermetric.lib.db.DataBaseTable;
import com.guggiemedia.fibermetric.lib.db.ItemTable;


public class StatusFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String FRAGMENT_TAG = "FRAGMENT_STATUS";

    public static final String LOG_TAG = StatusFragment.class.getName();

    // display only jobs matching today date else all known jobs
    public static final String ARG_PARAM_TODAY = "PARAM_TODAY";

    private MainActivityListener _listener;

    private StatusAdapter _adapter;

    private ProgressBar _progressBar;
    private TextView _progressValue;

    public static final int LOADER_ID = 271828;

    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        DataBaseTable table = new ItemTable();

        String[] projection = table.getDefaultProjection();

        String orderBy = ItemTable.Columns._ID + " DESC";

        return new CursorLoader(getActivity(), ItemTable.CONTENT_URI, projection, null, null, orderBy);
    }


    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor != null) {
            _adapter.setCursor(cursor);
            _adapter.notifyDataSetChanged();
        }

    }

    public void onLoaderReset(Loader<Cursor> loader) {
        _adapter.setCursor(null);
    }

    public static Fragment newInstance(Bundle args) {
        StatusFragment fragment = new StatusFragment();
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

        _adapter = new StatusAdapter(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_status, container, false);

        _progressBar = (ProgressBar) view.findViewById(R.id.pbJobProgress);
        _progressValue = (TextView) view.findViewById(R.id.tvProgressValue);


        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(_adapter);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(LOADER_ID, null, this);
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

            case R.id.actionSearch:

                break;
            default:
                throw new IllegalArgumentException("unknown menu option");
        }

        return true;
    }

    // set progress bar value, see github issue 107
    private void setProgressValue() {

        int progress = 25;

        _progressBar.setProgress(progress);
        _progressValue.setText(Integer.toString(progress) + "%");
    }
}