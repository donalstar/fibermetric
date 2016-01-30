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

import com.guggiemedia.fibermetric.R;
import com.guggiemedia.fibermetric.lib.db.DataBaseTable;
import com.guggiemedia.fibermetric.lib.db.ItemTable;

public class FoodSelectorFragment extends Fragment implements FragmentContext, LoaderManager.LoaderCallbacks<Cursor> {
    public static final String LOG_TAG = FoodSelectorFragment.class.getName();

    private static final String TITLE = "Add Food";

    private SelectorListAdapter _adapter;
    private MainActivityListener _listener;

    public static final int LOADER_ID = 1;

    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        DataBaseTable table = new ItemTable();

        String[] projection = table.getDefaultProjection();

        String orderBy = ItemTable.DEFAULT_SORT_ORDER;

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

    public static FoodSelectorFragment newInstance() {
        return new FoodSelectorFragment();
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

        _adapter = new SelectorListAdapter(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food_selector, container, false);

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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.food_selector_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public String getName() {
        return TITLE;
    }

    @Override
    public int getHomeIndicator() {
        return R.drawable.ic_close_white;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(LOG_TAG, "onOptionsItemSelected:" + item);

        switch (item.getItemId()) {
            case android.R.id.home:
                _listener.fragmentPop();
                return true;

            case R.id.actionSearch:
                break;
            default:
                throw new IllegalArgumentException("unknown menu option");
        }

        return true;
    }


}
