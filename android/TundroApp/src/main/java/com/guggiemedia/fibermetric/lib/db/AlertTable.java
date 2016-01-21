package com.guggiemedia.fibermetric.lib.db;

import android.net.Uri;
import android.provider.BaseColumns;


import com.guggiemedia.fibermetric.lib.Constant;

import java.util.HashMap;
import java.util.Set;

/**
 *
 */
public class AlertTable implements DataBaseTable {
    public static final String TABLE_NAME = "alert";

    public static final Uri CONTENT_URI = Uri.parse("content://" + Constant.AUTHORITY + "/" + TABLE_NAME);

    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.gofactory." + TABLE_NAME;

    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.gofactory." + TABLE_NAME;

    public static final String DEFAULT_SORT_ORDER = Columns.START_TIME_MS + " DESC";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + Columns._ID + " INTEGER PRIMARY KEY,"
            + Columns.ACTIVE_FLAG + " INTEGER NOT NULL,"
            + Columns.ALERT_TYPE + " TEXT NOT NULL,"
            + Columns.COUNTER + " INTEGER NOT NULL,"
            + Columns.THING_ID + " INTEGER NOT NULL,"
            + Columns.THING_NAME + " TEXT NOT NULL,"
            + Columns.START_TIME + " TEXT NOT NULL,"
            + Columns.START_TIME_MS + " INTEGER NOT NULL,"
            + Columns.LAST_TIME + " TEXT NOT NULL,"
            + Columns.LAST_TIME_MS + " INTEGER NOT NULL,"
            + Columns.NOTE + " TEXT NOT NULL"
            + ");";

    public static final class Columns implements BaseColumns {
        public static final String ACTIVE_FLAG = "active_flag";
        public static final String ALERT_TYPE = "alert_type";
        public static final String COUNTER = "counter";
        public static final String THING_ID = "thing_id";
        public static final String THING_NAME = "thing_name";
        public static final String START_TIME = "start_time";
        public static final String START_TIME_MS = "start_time_me";
        public static final String LAST_TIME= "last_time";
        public static final String LAST_TIME_MS = "last_time_ms";
        public static final String NOTE = "note";
    }

    //
    public static HashMap<String, String> PROJECTION_MAP = new HashMap<>();

    static {
        PROJECTION_MAP.put(AlertTable.Columns._ID, AlertTable.Columns._ID);
        PROJECTION_MAP.put(AlertTable.Columns.ACTIVE_FLAG, AlertTable.Columns.ACTIVE_FLAG);
        PROJECTION_MAP.put(AlertTable.Columns.ALERT_TYPE, AlertTable.Columns.ALERT_TYPE);
        PROJECTION_MAP.put(AlertTable.Columns.COUNTER, AlertTable.Columns.COUNTER);
        PROJECTION_MAP.put(AlertTable.Columns.THING_ID, AlertTable.Columns.THING_ID);
        PROJECTION_MAP.put(AlertTable.Columns.THING_NAME, AlertTable.Columns.THING_NAME);
        PROJECTION_MAP.put(AlertTable.Columns.START_TIME, AlertTable.Columns.START_TIME);
        PROJECTION_MAP.put(AlertTable.Columns.START_TIME_MS, AlertTable.Columns.START_TIME_MS);
        PROJECTION_MAP.put(AlertTable.Columns.LAST_TIME, AlertTable.Columns.LAST_TIME);
        PROJECTION_MAP.put(AlertTable.Columns.LAST_TIME_MS, AlertTable.Columns.LAST_TIME_MS);
        PROJECTION_MAP.put(AlertTable.Columns.NOTE, AlertTable.Columns.NOTE);
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
        Set<String> keySet = AlertTable.PROJECTION_MAP.keySet();
        return keySet.toArray(new String[keySet.size()]);
    }
}
