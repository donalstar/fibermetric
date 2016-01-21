package com.guggiemedia.fibermetric.lib.db;

import android.net.Uri;
import android.provider.BaseColumns;


import com.guggiemedia.fibermetric.lib.Constant;

import java.util.HashMap;
import java.util.Set;

/**
 *
 */
public class ThingTable implements DataBaseTable {
    public static final String TABLE_NAME = "thing";

    public static final Uri CONTENT_URI = Uri.parse("content://" + Constant.AUTHORITY + "/" + TABLE_NAME);

    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.gofactory." + TABLE_NAME;

    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.gofactory." + TABLE_NAME;

    public static final String DEFAULT_SORT_ORDER = Columns.NAME + " COLLATE NOCASE ASC";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + Columns._ID + " INTEGER PRIMARY KEY,"
            + Columns.PARENT_KEY + " INTEGER NOT NULL,"
            + Columns.SITE_KEY + " INTEGER NOT NULL,"
            + Columns.ALERT_FLAG + " INTEGER NOT NULL,"
            + Columns.BARCODE + " TEXT NOT NULL,"
            + Columns.BARCODE_FLAG + " INTEGER NOT NULL,"
            + Columns.BLE_ADDRESS + " TEXT NOT NULL,"
            + Columns.BLE_FLAG + " INTEGER NOT NULL,"
            + Columns.BLE_LAST_TIME_MS + " INTEGER NOT NULL,"
            + Columns.CUSTODY_FLAG + " INTEGER NOT NULL,"
            + Columns.DESCRIPTION + " TEXT NOT NULL,"
            + Columns.ITEM_TYPE + " TEXT NOT NULL,"
            + Columns.NAME + " TEXT NOT NULL,"
            + Columns.SERIAL_NUMBER + " TEXT NOT NULL,"
            + Columns.GF_UUID + " TEXT NOT NULL,"
            + Columns.PHOTO_NAME + " TEXT NOT NULL,"
            + Columns.NOTE + " TEXT NOT NULL"
            + ");";

    public static final class Columns implements BaseColumns {
        public static final String PARENT_KEY = "parent_key";
        public static final String SITE_KEY = "site_key";
        public static final String ALERT_FLAG = "alert_flag";
        public static final String BARCODE = "barcode";
        public static final String BARCODE_FLAG = "barcode_flag";
        public static final String BLE_ADDRESS = "ble_address";
        public static final String BLE_FLAG = "ble_flag";
        public static final String BLE_LAST_TIME_MS = "ble_last_time_ms";
        public static final String CUSTODY_FLAG = "custody_flag";
        public static final String DESCRIPTION = "description";
        public static final String ITEM_TYPE = "item_type";
        public static final String NAME = "name";
        public static final String SERIAL_NUMBER = "serial_number";
        public static final String GF_UUID = "gf_uuid";
        public static final String PHOTO_NAME = "photo_name";
        public static final String NOTE = "note";
    }

    //
    public static HashMap<String, String> PROJECTION_MAP = new HashMap<>();

    static {
        PROJECTION_MAP.put(ThingTable.Columns._ID, ThingTable.Columns._ID);
        PROJECTION_MAP.put(ThingTable.Columns.PARENT_KEY, ThingTable.Columns.PARENT_KEY);
        PROJECTION_MAP.put(ThingTable.Columns.SITE_KEY, ThingTable.Columns.SITE_KEY);
        PROJECTION_MAP.put(ThingTable.Columns.ALERT_FLAG, ThingTable.Columns.ALERT_FLAG);
        PROJECTION_MAP.put(ThingTable.Columns.BARCODE, ThingTable.Columns.BARCODE);
        PROJECTION_MAP.put(ThingTable.Columns.BARCODE_FLAG, ThingTable.Columns.BARCODE_FLAG);
        PROJECTION_MAP.put(ThingTable.Columns.BLE_ADDRESS, ThingTable.Columns.BLE_ADDRESS);
        PROJECTION_MAP.put(ThingTable.Columns.BLE_FLAG, ThingTable.Columns.BLE_FLAG);
        PROJECTION_MAP.put(ThingTable.Columns.BLE_LAST_TIME_MS, ThingTable.Columns.BLE_LAST_TIME_MS);
        PROJECTION_MAP.put(ThingTable.Columns.CUSTODY_FLAG, ThingTable.Columns.CUSTODY_FLAG);
        PROJECTION_MAP.put(ThingTable.Columns.DESCRIPTION, ThingTable.Columns.DESCRIPTION);
        PROJECTION_MAP.put(ThingTable.Columns.ITEM_TYPE, ThingTable.Columns.ITEM_TYPE);
        PROJECTION_MAP.put(ThingTable.Columns.NAME, ThingTable.Columns.NAME);
        PROJECTION_MAP.put(ThingTable.Columns.SERIAL_NUMBER, ThingTable.Columns.SERIAL_NUMBER);
        PROJECTION_MAP.put(ThingTable.Columns.GF_UUID, ThingTable.Columns.GF_UUID);
        PROJECTION_MAP.put(ThingTable.Columns.PHOTO_NAME, ThingTable.Columns.PHOTO_NAME);
        PROJECTION_MAP.put(ThingTable.Columns.NOTE, ThingTable.Columns.NOTE);
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
        Set<String> keySet = ThingTable.PROJECTION_MAP.keySet();
        return keySet.toArray(new String[keySet.size()]);
    }
}
