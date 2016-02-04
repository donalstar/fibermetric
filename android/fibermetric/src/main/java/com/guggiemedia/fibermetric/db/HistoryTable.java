package com.guggiemedia.fibermetric.db;

import android.net.Uri;
import android.provider.BaseColumns;

import com.guggiemedia.fibermetric.Constant;

import java.util.HashMap;
import java.util.Set;

/**
 *
 */
public class HistoryTable implements DataBaseTable {
    public static final String TABLE_NAME = "history";

    public static final Uri CONTENT_URI = Uri.parse("content://" + Constant.AUTHORITY + "/" + TABLE_NAME);

    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.guggiemedia." + TABLE_NAME;

    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.guggiemedia." + TABLE_NAME;

    public static final String DEFAULT_SORT_ORDER = HistoryTable.TABLE_NAME + "." + Columns.DATE + " ASC";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + Columns._ID + " INTEGER PRIMARY KEY,"
            + Columns.FRUIT + " REAL NOT NULL,"
            + Columns.VEG + " REAL NOT NULL,"
            + Columns.GRAIN + " REAL NOT NULL,"
            + Columns.TOTAL + " REAL NOT NULL,"
            + Columns.DATE + " DATE NOT NULL"
            + ");";

    public static final class Columns implements BaseColumns {
        public static final String FRUIT = "fruit";
        public static final String VEG = "veg";
        public static final String GRAIN = "grain";
        public static final String TOTAL = "total";
        public static final String DATE = "date";
    }

    //
    public static HashMap<String, String> PROJECTION_MAP = new HashMap<>();

    static {
        PROJECTION_MAP.put(Columns._ID, Columns._ID);
        PROJECTION_MAP.put(Columns.FRUIT, Columns.FRUIT);
        PROJECTION_MAP.put(Columns.VEG, Columns.VEG);
        PROJECTION_MAP.put(Columns.GRAIN, Columns.GRAIN);
        PROJECTION_MAP.put(Columns.TOTAL, Columns.TOTAL);
        PROJECTION_MAP.put(Columns.DATE, Columns.DATE);
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
        Set<String> keySet = HistoryTable.PROJECTION_MAP.keySet();
        return keySet.toArray(new String[keySet.size()]);
    }
}
