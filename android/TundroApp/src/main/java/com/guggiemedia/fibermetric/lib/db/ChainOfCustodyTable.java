package com.guggiemedia.fibermetric.lib.db;

import android.net.Uri;
import android.provider.BaseColumns;


import com.guggiemedia.fibermetric.lib.Constant;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by donal on 9/29/15.
 */
public class ChainOfCustodyTable implements DataBaseTable {
    public static final String TABLE_NAME = "chain_of_custody";

    public static final Uri CONTENT_URI = Uri.parse("content://" + Constant.AUTHORITY + "/" + TABLE_NAME);

    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.gofactory." + TABLE_NAME;

    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.gofactory." + TABLE_NAME;

    public static final String DEFAULT_SORT_ORDER = Columns.PICKUP_DATE_TS + " DESC";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + Columns._ID + " INTEGER PRIMARY KEY,"
            + Columns.PART_ID + " TEXT NOT NULL,"
            + Columns.CUSTODIAN + " TEXT NOT NULL,"
            + Columns.PICKUP_DATE_TS + " INTEGER NOT NULL,"
            + Columns.PICKUP_SITE_ID + " INTEGER NOT NULL,"
            + Columns.PICKUP_LATITUDE + " REAL NOT NULL,"
            + Columns.PICKUP_LONGITUDE + " REAL NOT NULL"
            + ");";

    public static final class Columns implements BaseColumns {
        public static final String PART_ID = "part_id";
        public static final String CUSTODIAN = "custodian";
        public static final String PICKUP_DATE_TS = "pickup_time";
        public static final String PICKUP_SITE_ID = "pickup_site_id";
        public static final String PICKUP_LATITUDE = "pickup_latitude";
        public static final String PICKUP_LONGITUDE = "pickup_longitude";
    }

    public static HashMap<String, String> PROJECTION_MAP = new HashMap<>();

    static {
        PROJECTION_MAP.put(ChainOfCustodyTable.Columns._ID, ChainOfCustodyTable.Columns._ID);
        PROJECTION_MAP.put(ChainOfCustodyTable.Columns.PART_ID, ChainOfCustodyTable.Columns.PART_ID);
        PROJECTION_MAP.put(ChainOfCustodyTable.Columns.CUSTODIAN, ChainOfCustodyTable.Columns.CUSTODIAN);
        PROJECTION_MAP.put(ChainOfCustodyTable.Columns.PICKUP_DATE_TS, ChainOfCustodyTable.Columns.PICKUP_DATE_TS);
        PROJECTION_MAP.put(ChainOfCustodyTable.Columns.PICKUP_SITE_ID, ChainOfCustodyTable.Columns.PICKUP_SITE_ID);
        PROJECTION_MAP.put(ChainOfCustodyTable.Columns.PICKUP_LATITUDE, ChainOfCustodyTable.Columns.PICKUP_LATITUDE);
        PROJECTION_MAP.put(ChainOfCustodyTable.Columns.PICKUP_LONGITUDE, ChainOfCustodyTable.Columns.PICKUP_LONGITUDE);
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
        Set<String> keySet = ChainOfCustodyTable.PROJECTION_MAP.keySet();
        return keySet.toArray(new String[keySet.size()]);
    }
}


