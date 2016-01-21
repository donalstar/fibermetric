package com.guggiemedia.fibermetric.lib.db;

import android.net.Uri;
import android.provider.BaseColumns;


import com.guggiemedia.fibermetric.lib.Constant;

import java.util.HashMap;
import java.util.Set;

/**
 *
 */
public class EventTable implements DataBaseTable {
    public static final String TABLE_NAME = "event";

    public static final Uri CONTENT_URI = Uri.parse("content://" + Constant.AUTHORITY + "/" + TABLE_NAME);

    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.gofactory." + TABLE_NAME;

    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.gofactory." + TABLE_NAME;

    public static final String DEFAULT_SORT_ORDER = Columns.TIMESTAMP_MS + " DESC";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + Columns._ID + " INTEGER PRIMARY KEY,"
            + Columns.TIMESTAMP + " TEXT NOT NULL,"
            + Columns.TIMESTAMP_MS + " INTEGER NOT NULL,"
            + Columns.NOTE + " TEXT NOT NULL"
            + ");";

    public static final class Columns implements BaseColumns {
        public static final String TIMESTAMP = "time_stamp";
        public static final String TIMESTAMP_MS = "time_stamp_ms";
        public static final String NOTE = "note";
    }

    //
    public static HashMap<String, String> PROJECTION_MAP = new HashMap<>();

    static {
        PROJECTION_MAP.put(EventTable.Columns._ID, EventTable.Columns._ID);
        PROJECTION_MAP.put(EventTable.Columns.TIMESTAMP, EventTable.Columns.TIMESTAMP);
        PROJECTION_MAP.put(EventTable.Columns.TIMESTAMP_MS, EventTable.Columns.TIMESTAMP_MS);
        PROJECTION_MAP.put(EventTable.Columns.NOTE, EventTable.Columns.NOTE);
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
        Set<String> keySet = EventTable.PROJECTION_MAP.keySet();
        return keySet.toArray(new String[keySet.size()]);
    }
}
