package com.guggiemedia.fibermetric.lib.db;

import android.net.Uri;
import android.provider.BaseColumns;

import com.guggiemedia.fibermetric.lib.Constant;

import java.util.HashMap;
import java.util.Set;

/**
 *
 */
public class AddedItemTable implements DataBaseTable {
    public static final String TABLE_NAME = "added_item";

    public static final Uri CONTENT_URI = Uri.parse("content://" + Constant.AUTHORITY + "/" + TABLE_NAME);

    public static final Uri ADDED_ITEMS_CONTENT_URI = Uri.parse("content://" + Constant.AUTHORITY + "/"
            + TABLE_NAME + "/items");

    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.guggiemedia." + TABLE_NAME;

    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.guggiemedia." + TABLE_NAME;

    public static final String DEFAULT_SORT_ORDER = TABLE_NAME + "." + Columns._ID + " ASC";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + Columns._ID + " INTEGER PRIMARY KEY,"
            + Columns.ITEM_ID + " INTEGER NOT NULL,"
            + Columns.SELECTED_PORTION + " TEXT NOT NULL,"
            + Columns.WEIGHT_MULTIPLE + " FLOAT NOT NULL"
            + ");";

    public static final String TABLE_JOIN_ITEM_TABLE =
            ItemTable.TABLE_NAME + " " + ItemTable.TABLE_NAME + " " +
                    "INNER JOIN " + AddedItemTable.TABLE_NAME + " "
                    + AddedItemTable.TABLE_NAME
                    + " ON " + AddedItemTable.TABLE_NAME + "." + AddedItemTable.Columns.ITEM_ID + " = "
                    + ItemTable.TABLE_NAME
                    + "." + Columns._ID;


    public static final class Columns implements BaseColumns {
        public static final String ITEM_ID = "item_id";
        public static final String SELECTED_PORTION = "selected_portion";
        public static final String WEIGHT_MULTIPLE = "weight_multiple";
    }

    //
    public static HashMap<String, String> PROJECTION_MAP = new HashMap<>();

    static {
        PROJECTION_MAP.put(AddedItemTable.TABLE_NAME + "." + Columns._ID, Columns._ID);
        PROJECTION_MAP.put(Columns.ITEM_ID, Columns.ITEM_ID);
        PROJECTION_MAP.put(Columns.SELECTED_PORTION, Columns.SELECTED_PORTION);
        PROJECTION_MAP.put(Columns.WEIGHT_MULTIPLE, Columns.WEIGHT_MULTIPLE);

        PROJECTION_MAP.put(ItemTable.Columns.NAME, ItemTable.Columns.NAME);
        PROJECTION_MAP.put(ItemTable.Columns.GRAMS, ItemTable.Columns.GRAMS);
        PROJECTION_MAP.put(ItemTable.Columns.TYPE, ItemTable.Columns.TYPE);
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
        Set<String> keySet = AddedItemTable.PROJECTION_MAP.keySet();
        return keySet.toArray(new String[keySet.size()]);
    }
}
