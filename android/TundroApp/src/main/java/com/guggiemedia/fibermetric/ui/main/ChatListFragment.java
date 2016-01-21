package com.guggiemedia.fibermetric.ui.main;


import android.app.Activity;
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
import net.go_factory.tundro.R;

import com.guggiemedia.fibermetric.lib.db.ChatTable;
import com.guggiemedia.fibermetric.lib.db.DataBaseTable;
import com.guggiemedia.fibermetric.ui.utility.ToastHelper;


/**
 * Created by donal on 9/28/15.
 */
public class ChatListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final String FRAGMENT_TAG = "FRAGMENT_CHAT_LIST";

    public static final String LOG_TAG = ChatListFragment.class.getName();

    private ChatListAdapter _adapter;
    private MainActivityListener _listener;

    public static final int LOADER_ID = 271928;

    // LoaderCallback
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.d(LOG_TAG, "onCreateLoader:" + id);

        DataBaseTable table = new ChatTable();

        String[] projection = table.getDefaultProjection();
        String orderBy = table.getDefaultSortOrder();

        String selection = null;
        String[] selectionArgs = null;

        return new CursorLoader(getActivity(), ChatTable.CONTENT_URI, projection, selection, selectionArgs, orderBy);
    }

    // LoaderCallback
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor != null) {
            _adapter.setCursor(cursor);
            _adapter.notifyDataSetChanged();
        }
    }

    // LoaderCallback
    public void onLoaderReset(Loader<Cursor> loader) {
        _adapter.setCursor(null);
    }

    public static ChatListFragment newInstance() {
        return new ChatListFragment();
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        _listener = (MainActivityListener) activity;
        _adapter = new ChatListAdapter(activity);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_chat_list, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(_adapter);

        android.support.design.widget.FloatingActionButton fab
                = (android.support.design.widget.FloatingActionButton) view.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastHelper.show(view, "Create new chat conversation");
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.chat_list_fragment, menu);
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
            case R.id.actionMore:
                break;
            default:
                throw new IllegalArgumentException("unknown menu option");
        }

        return true;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        _listener = null;
    }
}





