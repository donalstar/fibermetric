package com.guggiemedia.fibermetric.lib.db;

import android.net.Uri;
import android.provider.BaseColumns;

import com.guggiemedia.fibermetric.lib.Constant;

import java.util.HashMap;
import java.util.Set;

/**
 *
 */
public class ItemTable implements DataBaseTable {
    public static final String TABLE_NAME = "item";

    public static final Uri CONTENT_URI = Uri.parse("content://" + Constant.AUTHORITY + "/" + TABLE_NAME);

    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.guggiemedia." + TABLE_NAME;

    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.guggiemedia." + TABLE_NAME;

    public static final String DEFAULT_SORT_ORDER = ItemTable.TABLE_NAME + "." + Columns.NAME + " ASC";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + Columns._ID + " INTEGER PRIMARY KEY,"
            + Columns.NAME + " TEXT NOT NULL,"
            + Columns.PORTION + " TEXT NOT NULL,"
            + Columns.PORTION_TYPE + " TEXT NOT NULL,"
            + Columns.GRAMS + " FLOAT NOT NULL,"
            + Columns.TYPE + " TEXT NOT NULL"
            + ");";

    public static final class Columns implements BaseColumns {
        public static final String NAME = "name";
        public static final String PORTION = "portion";
        public static final String PORTION_TYPE = "portion_type";
        public static final String GRAMS = "grams";
        public static final String TYPE = "type";
    }

    //
    public static HashMap<String, String> PROJECTION_MAP = new HashMap<>();

    static {
        PROJECTION_MAP.put(ItemTable.TABLE_NAME + "." + Columns._ID, Columns._ID);
        PROJECTION_MAP.put(ItemTable.TABLE_NAME + "." + Columns.NAME, Columns.NAME);
        PROJECTION_MAP.put(Columns.PORTION, Columns.PORTION);
        PROJECTION_MAP.put(Columns.PORTION_TYPE, Columns.PORTION_TYPE);
        PROJECTION_MAP.put(Columns.GRAMS, Columns.GRAMS);
        PROJECTION_MAP.put(Columns.TYPE, Columns.TYPE);
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
        Set<String> keySet = ItemTable.PROJECTION_MAP.keySet();
        return keySet.toArray(new String[keySet.size()]);
    }
}
