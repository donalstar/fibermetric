package com.guggiemedia.fibermetric.ui.main;


import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.guggiemedia.fibermetric.R;
import com.guggiemedia.fibermetric.lib.Personality;
import com.guggiemedia.fibermetric.lib.db.DataBaseTable;
import com.guggiemedia.fibermetric.lib.db.InventoryCategoryEnum;
import com.guggiemedia.fibermetric.lib.db.InventoryStatusEnum;
import com.guggiemedia.fibermetric.lib.db.JobTaskModel;
import com.guggiemedia.fibermetric.lib.db.JobTaskTable;
import com.guggiemedia.fibermetric.lib.db.PartModel;
import com.guggiemedia.fibermetric.lib.db.PartTable;
import com.guggiemedia.fibermetric.lib.db.PersonPartTable;


import java.util.Date;
import java.util.HashMap;

/**
 * inventory tabbed list (single tab view)
 */
public class InventoryViewFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final String FRAGMENT_TAG = "FRAGMENT_INVENTORY_VIEW";

    public static final String LOG_TAG = InventoryViewFragment.class.getName();

    public static final String ARG_PARAM_TAB_ID = "PARAM_TAB_ID";
    public static final String ARG_PARAM_VIEW_TYPE = "PARAM_VIEW_TYPE";
    public static final String ARG_PARAM_PARENT = "PARAM_PARENT";

    private MainActivityListener _listener;

    private InventoryViewAdapter _adapter;

    private long _tabId = 0L;
    private InventoryCategoryEnum _category;
    private InventoryPagerFragment.ViewType _viewType;

    private MainActivityFragmentEnum _paramParent = MainActivityFragmentEnum.UNKNOWN;

    private HashMap<Long, String> _partsForPickup;

    public static final int LOADER_ID = 271938;

    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        DataBaseTable table = new PartTable();

        String[] projection = table.getDefaultProjection();

        String orderBy = PartTable.Columns.STATUS + " DESC";

        String selection;
        String[] selectionArgs;
        Uri uri;

        switch (_viewType) {
            case requiredInventory: {
                selection = PartTable.Columns.CATEGORY + "=? and "
                        + JobTaskTable.TABLE_NAME + "." + JobTaskTable.Columns.DEADLINE + "=? and "
                        + JobTaskTable.TABLE_NAME + "." + JobTaskTable.Columns._ID + "=?";

                String today = JobTaskModel.formatter2(new Date());

                Long jobId = getArguments().getLong(JobViewFragment.ARG_PARAM_JOB_ID);

                selectionArgs = new String[]{
                        _category.toString(),
                        today,
                        String.valueOf(jobId)
                };

                uri = Uri.parse(PartTable.QUERY_BY_JOB_DEADLINE_CONTENT_URI);
                break;
            }

            case myInventory: {
                selection = PartTable.Columns.CATEGORY + "=? and "
                        + PersonPartTable.TABLE_NAME + "." + PersonPartTable.Columns.PERSON_ID + "=? ";

                selection += "and status = 'inCustody'";

                selectionArgs = new String[]{
                        _category.toString(),
                        String.valueOf(Personality.personSelf.getId())
                };

                uri = Uri.parse(PartTable.QUERY_BY_PERSON_CONTENT_URI);
                break;
            }
            default: // today's inventory
            {
                selection = PartTable.Columns.CATEGORY + "=? and "
                        + JobTaskTable.TABLE_NAME + "." + JobTaskTable.Columns.DEADLINE + "=?";

                String today = JobTaskModel.formatter2(new Date());

                selectionArgs = new String[]{
                        _category.toString(),
                        today
                };

                uri = Uri.parse(PartTable.QUERY_BY_JOB_DEADLINE_CONTENT_URI);
                break;
            }
        }


        return new CursorLoader(getActivity(), uri, projection, selection, selectionArgs, orderBy);
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor != null) {
            _adapter.setCursor(cursor);
            _adapter.notifyDataSetChanged();
            _adapter.setCategory(_category);

            _partsForPickup = new HashMap<>();

            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);

                final PartModel model = new PartModel();
                model.setDefault();
                model.fromCursor(cursor);

                if (model.getStatus().equals(InventoryStatusEnum.pickUp)) {
                    _partsForPickup.put(model.getId(), model.getName());
                }
            }
        }

    }

    public void onLoaderReset(Loader<Cursor> loader) {
        _adapter.setCursor(null);
    }

    /**
     * Use this factory method to create a new instance of fragment.
     *
     * @param args
     * @return A new instance of fragment TaskViewFragment.
     */
    public static InventoryViewFragment newInstance(Bundle args) {
        InventoryViewFragment fragment = new InventoryViewFragment();
        fragment.setArguments(args);
        return fragment;
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
            _tabId = getArguments().getLong(ARG_PARAM_TAB_ID);

            _paramParent = (MainActivityFragmentEnum) getArguments().getSerializable(ARG_PARAM_PARENT);

            _category = getCategory(_tabId);
            _viewType
                    = InventoryPagerFragment.ViewType.getForValue(
                    getArguments().getString(ARG_PARAM_VIEW_TYPE));
        }

        _adapter = new InventoryViewAdapter(getActivity(), _viewType);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inventory_view, container, false);

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
    public void onDetach() {
        super.onDetach();
        _listener = null;
    }

    private InventoryCategoryEnum getCategory(long tabId) {
        InventoryCategoryEnum category = null;

        switch ((int) tabId) {
            case 0:
                category = InventoryCategoryEnum.parts;
                break;
            case 1:
                category = InventoryCategoryEnum.tools;
                break;
            case 2:
                category = InventoryCategoryEnum.vehicles;
                break;
            default:
                break;
        }

        return category;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(LOG_TAG, "onOptionsItemSelected:" + item);

        switch (item.getItemId()) {
            case android.R.id.home:
                _listener.fragmentSelect(_paramParent, new Bundle());
                break;
            default:
                throw new IllegalArgumentException("unknown menu option");
        }

        return true;
    }
}
