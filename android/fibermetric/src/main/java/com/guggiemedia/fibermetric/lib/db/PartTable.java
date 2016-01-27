package com.guggiemedia.fibermetric.lib.db;

import android.net.Uri;
import android.provider.BaseColumns;

import com.guggiemedia.fibermetric.lib.Constant;

import java.util.HashMap;
import java.util.Set;

/**
 *
 */
public class PartTable implements DataBaseTable {
    public static final String TABLE_NAME = "part";

    public static final Uri CONTENT_URI = Uri.parse("content://" + Constant.AUTHORITY + "/" + TABLE_NAME);

    public static final String QUERY_BY_PERSON_CONTENT_URI = "content://" + Constant.AUTHORITY + "/"
            + PartTable.TABLE_NAME + "/by_person/";

    public static final String QUERY_BY_JOB_DEADLINE_CONTENT_URI = "content://" + Constant.AUTHORITY + "/"
            + PartTable.TABLE_NAME + "/by_job/";

    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.gofactory." + TABLE_NAME;

    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.gofactory." + TABLE_NAME;

    public static final String DEFAULT_SORT_ORDER = Columns.NAME + " ASC";

    public static final String TABLE_JOIN_PERSON_PART_TABLE =
            PartTable.TABLE_NAME + " " + PartTable.TABLE_NAME + " " +
                    "INNER JOIN " + PersonPartTable.TABLE_NAME + " "
                    + PersonPartTable.TABLE_NAME
                    + " ON " + PartTable.TABLE_NAME + "." + Columns._ID + " = "
                    + PersonPartTable.TABLE_NAME
                    + "." + PersonPartTable.Columns.PART_ID;

    public static final String TABLE_JOIN_PART_JOB_TABLE
            = PartTable.TABLE_NAME + " as " + PartTable.TABLE_NAME +
            "    INNER JOIN " + JobPartTable.TABLE_NAME + " AS " + JobPartTable.TABLE_NAME +
            "    ON " + PartTable.TABLE_NAME + "._id = " + JobPartTable.TABLE_NAME + ".part_id" +
            "    INNER JOIN " + JobTaskTable.TABLE_NAME + " AS " + JobTaskTable.TABLE_NAME +
            "    ON job_part.jobtask_id = jobtask._id";


    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + Columns._ID + " INTEGER PRIMARY KEY,"
            + Columns.NAME + " TEXT NOT NULL,"
            + Columns.STATUS + " INTEGER NOT NULL,"
            + Columns.SERIAL + " TEXT NOT NULL,"
            + Columns.MANUFACTURER + " TEXT NOT NULL,"
            + Columns.MODEL_NUMBER + " TEXT NOT NULL,"
            + Columns.BARCODE + " TEXT,"
            + Columns.BLE_ADDRESS + " TEXT,"
            + Columns.BLE_IN_RANGE + " INTEGER NOT NULL,"
            + Columns.OWNER + " TEXT NOT NULL,"
            + Columns.PICKED_UP_DATE_TS + " INTEGER NOT NULL,"
            + Columns.CONDITION + " TEXT NOT NULL,"
            + Columns.SITE_ID + " INTEGER NOT NULL,"
            + Columns.LATITUDE + " REAL NOT NULL,"
            + Columns.LONGITUDE + " REAL NOT NULL,"
            + Columns.DESCRIPTION + " TEXT NOT NULL,"
            + Columns.CATEGORY + " TEXT NOT NULL"
            + ");";

    public static final class Columns implements BaseColumns {
        public static final String NAME = "name";
        public static final String STATUS = "status";
        public static final String SERIAL = "serial";
        public static final String MANUFACTURER = "manufacturer";
        public static final String MODEL_NUMBER = "model_number";
        public static final String BARCODE = "barcode";
        public static final String BLE_ADDRESS = "ble_address";
        public static final String BLE_IN_RANGE = "ble_in_range";
        public static final String OWNER = "owner";
        public static final String PICKED_UP_DATE_TS = "picked_up_date_ts";
        public static final String CONDITION = "condition";
        public static final String SITE_ID = "site_id";
        public static final String LATITUDE = "latitude";
        public static final String LONGITUDE = "longitude";
        public static final String DESCRIPTION = "description";
        public static final String CATEGORY = "category";
    }

    //
    public static HashMap<String, String> PROJECTION_MAP = new HashMap<>();

    static {
        PROJECTION_MAP.put(PartTable.TABLE_NAME + "." + Columns._ID, Columns._ID);
        PROJECTION_MAP.put(PartTable.TABLE_NAME + "." + Columns.NAME, Columns.NAME);
        PROJECTION_MAP.put(Columns.STATUS, Columns.STATUS);
        PROJECTION_MAP.put(Columns.SERIAL, Columns.SERIAL);
        PROJECTION_MAP.put(Columns.MANUFACTURER, Columns.MANUFACTURER);
        PROJECTION_MAP.put(Columns.MODEL_NUMBER, Columns.MODEL_NUMBER);
        PROJECTION_MAP.put(Columns.BARCODE, Columns.BARCODE);
        PROJECTION_MAP.put(Columns.BLE_ADDRESS, Columns.BLE_ADDRESS);
        PROJECTION_MAP.put(Columns.BLE_IN_RANGE, Columns.BLE_IN_RANGE);
        PROJECTION_MAP.put(Columns.OWNER, Columns.OWNER);
        PROJECTION_MAP.put(Columns.PICKED_UP_DATE_TS, Columns.PICKED_UP_DATE_TS);
        PROJECTION_MAP.put(Columns.CONDITION, Columns.CONDITION);
        PROJECTION_MAP.put(Columns.SITE_ID, Columns.SITE_ID);
        PROJECTION_MAP.put(Columns.LATITUDE, Columns.LATITUDE);
        PROJECTION_MAP.put(Columns.LONGITUDE, Columns.LONGITUDE);
        PROJECTION_MAP.put(PartTable.TABLE_NAME + "." + Columns.DESCRIPTION, Columns.DESCRIPTION);
        PROJECTION_MAP.put(Columns.CATEGORY, Columns.CATEGORY);
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
        Set<String> keySet = PartTable.PROJECTION_MAP.keySet();
        return keySet.toArray(new String[keySet.size()]);
    }
}
