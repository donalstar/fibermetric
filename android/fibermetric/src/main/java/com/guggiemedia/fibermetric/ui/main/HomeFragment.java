package com.guggiemedia.fibermetric.ui.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.guggiemedia.fibermetric.R;
import com.guggiemedia.fibermetric.db.AddedItemTable;
import com.guggiemedia.fibermetric.db.ContentFacade;
import com.guggiemedia.fibermetric.db.DataBaseTable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class HomeFragment extends Fragment implements FragmentContext, LoaderManager.LoaderCallbacks<Cursor> {
    public static final String LOG_TAG = HomeFragment.class.getName();

    public static final String ARG_PARAM_TODAY = "PARAM_TODAY";

    private static final String TITLE = "Today";

    private MainActivityListener _listener;

    private HomeListAdapter _adapter;

    private ProgressBar _progressBar;
    private TextView _progressValue;

    private Date _currentDate;

    private static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMM dd, yyyy");

    public static final int LOADER_ID = 271828;

    private ContentFacade _contentFacade;

    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        DataBaseTable table = new AddedItemTable();

        String[] projection = table.getDefaultProjection();

        String selection = "date > ? and date < ?";

        String[] selectionArgs = getDateRangeSelectionArgs(_currentDate);

        return new CursorLoader(getActivity(), AddedItemTable.ADDED_ITEMS_CONTENT_URI, projection, selection, selectionArgs, null);
    }

    /**
     * @param date
     * @return
     */
    private String[] getDateRangeSelectionArgs(Date date) {
        Date dateRange[] = getDateRangeForSelection(date);

        return new String[]{String.valueOf(dateRange[0].getTime()), String.valueOf(dateRange[1].getTime())};
    }

    /**
     * @param date
     * @return
     */
    private Date[] getDateRangeForSelection(Date date) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Date startDate = calendar.getTime();

        calendar.add(Calendar.DAY_OF_MONTH, 1);

        Date endDate = calendar.getTime();

        return new Date[]{startDate, endDate};
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

        listenForDateSelectBroadcast();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        _adapter = new HomeListAdapter(getActivity());

        _currentDate = new Date();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        _progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        _progressValue = (TextView) view.findViewById(R.id.progressValue);


        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(_adapter);

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback
                = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                HomeListAdapter.ViewHolder vh = (HomeListAdapter.ViewHolder) viewHolder;

                _contentFacade.deleteAddedItem(vh.id, getActivity());

                getLoaderManager().restartLoader(LOADER_ID, null, HomeFragment.this);

            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);

        itemTouchHelper.attachToRecyclerView(recyclerView);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _listener.fragmentSelect(Fragments.FOOD_SELECTOR_VIEW, new Bundle());
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
                _listener.dialogSelect(MainActivityDialogEnum.CALENDAR, null);
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


    private void listenForDateSelectBroadcast() {
        IntentFilter filter = new IntentFilter(CalendarDialog.DATE_SELECT_FILTER);

        final LoaderManager.LoaderCallbacks<Cursor> callback = this;

        getActivity().registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Long date = intent.getLongExtra("date", 0L);

                _currentDate = new Date(date);

                getLoaderManager().restartLoader(LOADER_ID, null, callback);

                ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

                String title = DATE_FORMAT.format(date);

                actionBar.setTitle(title);
            }
        }

                , filter);
    }
}