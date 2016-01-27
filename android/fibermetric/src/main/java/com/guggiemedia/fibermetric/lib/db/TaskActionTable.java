package com.guggiemedia.fibermetric.lib.db;

import android.net.Uri;
import android.provider.BaseColumns;

import com.guggiemedia.fibermetric.lib.Constant;

import java.util.HashMap;
import java.util.Set;

/**
 *
 */
public class TaskActionTable implements DataBaseTable {
    public static final String TABLE_NAME = "task_action";

    public static final Uri CONTENT_URI = Uri.parse("content://" + Constant.AUTHORITY + "/" + TABLE_NAME);

    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.gofactory." + TABLE_NAME;

    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.gofactory." + TABLE_NAME;

    public static final String DEFAULT_SORT_ORDER = Columns.PARENT + " ASC";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + Columns._ID + " INTEGER PRIMARY KEY,"
            + Columns.PARENT + " INTEGER NOT NULL,"
            + Columns.ORDER_NDX + " INTEGER NOT NULL,"
            + Columns.ACTION + " TEXT NOT NULL,"
            + Columns.STATE + " TEXT NOT NULL,"
            + Columns.DESCRIPTION + " TEXT NOT NULL"
            + ");";

    public static final class Columns implements BaseColumns {
        public static final String ACTION = "action";
        public static final String DESCRIPTION = "description";
        public static final String ORDER_NDX = "order_ndx";
        public static final String PARENT = "parent";
        public static final String STATE = "state";
    }

    //
    public static HashMap<String, String> PROJECTION_MAP = new HashMap<>();

    static {
        PROJECTION_MAP.put(Columns._ID, Columns._ID);
        PROJECTION_MAP.put(Columns.PARENT, Columns.PARENT);
        PROJECTION_MAP.put(Columns.ORDER_NDX, Columns.ORDER_NDX);
        PROJECTION_MAP.put(Columns.ACTION, Columns.ACTION);
        PROJECTION_MAP.put(Columns.STATE, Columns.STATE);
        PROJECTION_MAP.put(Columns.DESCRIPTION, Columns.DESCRIPTION);
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public String getDefaultSortOrder() {
        return DEFAULT_SORT_ORDER;
    }

    @Override
    public String[] getDefaultProjection() {
        Set<String> keySet = TaskActionTable.PROJECTION_MAP.keySet();
        return keySet.toArray(new String[keySet.size()]);
    }
}