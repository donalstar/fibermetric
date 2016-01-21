package com.guggiemedia.fibermetric.lib.db;

import android.net.Uri;
import android.provider.BaseColumns;


import com.guggiemedia.fibermetric.lib.Constant;

import java.util.HashMap;
import java.util.Set;

/**
 *
 */
public class ToolTable implements DataBaseTable {

    public static final String TABLE_NAME = "tool";

    public static final Uri CONTENT_URI = Uri.parse("content://" + Constant.AUTHORITY + "/" + TABLE_NAME);

    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.gofactory." + TABLE_NAME;

    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.gofactory." + TABLE_NAME;

    public static final String DEFAULT_SORT_ORDER = Columns.JOB + " ASC";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + Columns._ID + " INTEGER PRIMARY KEY,"
            + Columns.JOB + " INTEGER NOT NULL,"
            + Columns.THING + " INTEGER NOT NULL"
            + ");";

    public static final class Columns implements BaseColumns {
        public static final String JOB = "job";
        public static final String THING = "thing";
    }

    //
    public static HashMap<String, String> PROJECTION_MAP = new HashMap<>();

    static {
        PROJECTION_MAP.put(ToolTable.Columns._ID, ToolTable.Columns._ID);
        PROJECTION_MAP.put(ToolTable.Columns.JOB, ToolTable.Columns.JOB);
        PROJECTION_MAP.put(ToolTable.Columns.THING, ToolTable.Columns.THING);
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
        Set<String> keySet = ToolTable.PROJECTION_MAP.keySet();
        return keySet.toArray(new String[keySet.size()]);
    }
}
