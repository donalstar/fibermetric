package com.guggiemedia.fibermetric.lib.db;

import android.net.Uri;
import android.provider.BaseColumns;


import com.guggiemedia.fibermetric.lib.Constant;

import java.util.HashMap;
import java.util.Set;

/**
 *
 */
public class PersonPartTable implements DataBaseTable {
    public static final String TABLE_NAME = "person_part";

    public static final Uri CONTENT_URI = Uri.parse("content://" + Constant.AUTHORITY + "/" + TABLE_NAME);

    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.gofactory." + TABLE_NAME;

    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.gofactory." + TABLE_NAME;

    public static final String DEFAULT_SORT_ORDER = Columns.PERSON_ID + " ASC";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + Columns._ID + " INTEGER PRIMARY KEY,"
            + Columns.PERSON_ID + " INTEGER NOT NULL,"
            + Columns.PART_ID + " INTEGER NOT NULL"
            + ");";

    public static final class Columns implements BaseColumns {
        public static final String PERSON_ID = "person_id";
        public static final String PART_ID = "part_id";
    }

    //
    public static HashMap<String, String> PROJECTION_MAP = new HashMap<>();

    static {
        PROJECTION_MAP.put(PersonPartTable.Columns._ID, PersonPartTable.Columns._ID);
        PROJECTION_MAP.put(PersonPartTable.Columns.PERSON_ID, PersonPartTable.Columns.PERSON_ID);
        PROJECTION_MAP.put(PersonPartTable.Columns.PART_ID, PersonPartTable.Columns.PART_ID);
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
        Set<String> keySet = PersonPartTable.PROJECTION_MAP.keySet();
        return keySet.toArray(new String[keySet.size()]);
    }
}
