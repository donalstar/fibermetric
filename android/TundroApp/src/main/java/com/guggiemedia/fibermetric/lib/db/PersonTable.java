package com.guggiemedia.fibermetric.lib.db;

import android.net.Uri;
import android.provider.BaseColumns;


import com.guggiemedia.fibermetric.lib.Constant;

import java.util.HashMap;
import java.util.Set;

/**
 *
 */
public class PersonTable implements DataBaseTable {

    public static final String TABLE_NAME = "person";

    public static final Uri CONTENT_URI = Uri.parse("content://" + Constant.AUTHORITY + "/" + TABLE_NAME);

    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.gofactory." + TABLE_NAME;

    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.gofactory." + TABLE_NAME;

    public static final String DEFAULT_SORT_ORDER = Columns.NAME_LAST + " ASC";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + Columns._ID + " INTEGER PRIMARY KEY,"
            + Columns.EMAIL + " TEXT NOT NULL,"
            + Columns.NAME_USER + " TEXT NOT NULL,"
            + Columns.NAME_LAST + " TEXT NOT NULL,"
            + Columns.NAME_FIRST + " TEXT NOT NULL,"
            + Columns.NAME_SUFFIX + " TEXT NOT NULL,"
            + Columns.PHONE + " TEXT NOT NULL,"
            + Columns.LATITUDE + " REAL NOT NULL,"
            + Columns.LONGITUDE + " REAL NOT NULL,"
            + Columns.FIX_TIME_MS + " INTEGER NOT NULL,"
            + Columns.NOTE + " TEXT NOT NULL"
            + ");";

    public static final class Columns implements BaseColumns {
        public static final String EMAIL = "email";
        public static final String NAME_USER = "name_user";
        public static final String NAME_LAST = "name_last";
        public static final String NAME_FIRST = "name_first";
        public static final String NAME_SUFFIX = "name_suffix";
        public static final String PHONE = "phone";
        public static final String LATITUDE = "latitude";
        public static final String LONGITUDE = "longitude";
        public static final String FIX_TIME_MS = "fix_time_ms";
        public static final String NOTE = "note";
    }

    //
    public static HashMap<String, String> PROJECTION_MAP = new HashMap<>();

    static {
        PROJECTION_MAP.put(PersonTable.Columns._ID, PersonTable.Columns._ID);
        PROJECTION_MAP.put(PersonTable.Columns.EMAIL, PersonTable.Columns.EMAIL);
        PROJECTION_MAP.put(PersonTable.Columns.NAME_USER, PersonTable.Columns.NAME_USER);
        PROJECTION_MAP.put(PersonTable.Columns.NAME_LAST, PersonTable.Columns.NAME_LAST);
        PROJECTION_MAP.put(PersonTable.Columns.NAME_FIRST, PersonTable.Columns.NAME_FIRST);
        PROJECTION_MAP.put(PersonTable.Columns.NAME_SUFFIX, PersonTable.Columns.NAME_SUFFIX);
        PROJECTION_MAP.put(PersonTable.Columns.PHONE, PersonTable.Columns.PHONE);
        PROJECTION_MAP.put(PersonTable.Columns.LATITUDE, PersonTable.Columns.LATITUDE);
        PROJECTION_MAP.put(PersonTable.Columns.LONGITUDE, PersonTable.Columns.LONGITUDE);
        PROJECTION_MAP.put(PersonTable.Columns.FIX_TIME_MS, PersonTable.Columns.FIX_TIME_MS);
        PROJECTION_MAP.put(PersonTable.Columns.NOTE, PersonTable.Columns.NOTE);
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
        Set<String> keySet = PersonTable.PROJECTION_MAP.keySet();
        return keySet.toArray(new String[keySet.size()]);
    }
}