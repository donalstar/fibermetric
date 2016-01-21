package com.guggiemedia.fibermetric.lib.db;

import android.net.Uri;
import android.provider.BaseColumns;


import com.guggiemedia.fibermetric.lib.Constant;

import java.util.HashMap;
import java.util.Set;

/**
 *
 */
public class SiteTable implements DataBaseTable {

    public static final String TABLE_NAME = "site";

    public static final Uri CONTENT_URI = Uri.parse("content://" + Constant.AUTHORITY + "/" + TABLE_NAME);

    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.gofactory." + TABLE_NAME;

    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.gofactory." + TABLE_NAME;

    public static final String DEFAULT_SORT_ORDER = Columns.NAME + " ASC";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + Columns._ID + " INTEGER PRIMARY KEY,"
            + Columns.NAME + " TEXT NOT NULL,"
            + Columns.STREET1 + " TEXT NOT NULL,"
            + Columns.STREET2 + " TEXT,"
            + Columns.CITY + " TEXT,"
            + Columns.STATE + " TEXT,"
            + Columns.ZIP + " TEXT,"
            + Columns.LATITUDE + " FLOAT NOT NULL,"
            + Columns.LONGITUDE + " FLOAT NOT NULL"
            + ");";

    public static final class Columns implements BaseColumns {
        public static final String NAME = "name";
        public static final String STREET1 = "street1";
        public static final String STREET2 = "street2";
        public static final String CITY = "city";
        public static final String STATE = "state";
        public static final String ZIP = "zip";
        public static final String LATITUDE = "latitude";
        public static final String LONGITUDE = "longitude";
    }

    //
    public static HashMap<String, String> PROJECTION_MAP = new HashMap<>();

    static {
        PROJECTION_MAP.put(SiteTable.Columns._ID, SiteTable.Columns._ID);
        PROJECTION_MAP.put(SiteTable.Columns.NAME, SiteTable.Columns.NAME);
        PROJECTION_MAP.put(SiteTable.Columns.STREET1, SiteTable.Columns.STREET1);
        PROJECTION_MAP.put(SiteTable.Columns.STREET2, SiteTable.Columns.STREET2);
        PROJECTION_MAP.put(SiteTable.Columns.CITY, SiteTable.Columns.CITY);
        PROJECTION_MAP.put(SiteTable.Columns.STATE, SiteTable.Columns.STATE);
        PROJECTION_MAP.put(SiteTable.Columns.ZIP, SiteTable.Columns.ZIP);
        PROJECTION_MAP.put(SiteTable.Columns.LATITUDE, SiteTable.Columns.LATITUDE);
        PROJECTION_MAP.put(SiteTable.Columns.LONGITUDE, SiteTable.Columns.LONGITUDE);
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
        Set<String> keySet = SiteTable.PROJECTION_MAP.keySet();
        return keySet.toArray(new String[keySet.size()]);
    }
}
