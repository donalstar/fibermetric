package com.guggiemedia.fibermetric.ui.main;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import com.guggiemedia.fibermetric.lib.db.ContentFacade;
import com.guggiemedia.fibermetric.lib.db.DataBaseTable;
import com.guggiemedia.fibermetric.lib.db.ItemTable;


public class HomeFragment extends Fragment implements FragmentContext, LoaderManager.LoaderCallbacks<Cursor> {
    public static final String LOG_TAG = HomeFragment.class.getName();

    public static final String ARG_PARAM_TODAY = "PARAM_TODAY";

    private static final String TITLE = "Home";

    private MainActivityListener _listener;

    private HomeListAdapter _adapter;

    private ProgressBar _progressBar;
    private TextView _progressValue;

    public static final int LOADER_ID = 271828;


    private ContentFacade _contentFacade;

    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        DataBaseTable table = new ItemTable();

        String[] projection = table.getDefaultProjection();

        return new CursorLoader(getActivity(), ItemTable.ADDED_ITEMS_CONTENT_URI, projection, null, null, null);
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
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        _listener = (MainActivityListener) getActivity();

        _contentFacade = new ContentFacade();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        _adapter = new HomeListAdapter(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        _progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        _progressValue = (TextView) view.findViewById(R.id.progressValue);


        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(_adapter);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _listener.fragmentSelect(MainActivityFragmentEnum.FOOD_SELECTOR_VIEW, new Bundle());
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public void onResume() {
        Log.i(LOG_TAG, "onResume");

        super.onResume();

        getLoaderManager().restartLoader(LOADER_ID, null, this);

        setProgressValue();
    }


    @Override
    public void onDetach() {
        super.onDetach();
        _listener = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.home_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public String getName() {
        return TITLE;
    }

    @Override
    public int getHomeIndicator() {
        return R.drawable.ic_menu_white;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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

        Double progress = _contentFacade.getGetDailyProgress(getActivity());


        int progressValue = (int) (progress * 100);

        _progressBar.setProgress(progressValue);
        _progressValue.setText(Integer.toString(progressValue) + "%");
    }


}