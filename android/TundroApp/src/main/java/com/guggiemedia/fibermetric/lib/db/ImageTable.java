package com.guggiemedia.fibermetric.lib.db;

import android.net.Uri;
import android.provider.BaseColumns;


import com.guggiemedia.fibermetric.lib.Constant;

import java.util.HashMap;
import java.util.Set;

/**
 *
 */
public class ImageTable implements DataBaseTable {
    public static final String TABLE_NAME = "image";

    public static final Uri CONTENT_URI = Uri.parse("content://" + Constant.AUTHORITY + "/" + TABLE_NAME);

    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.gofactory." + TABLE_NAME;

    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.gofactory." + TABLE_NAME;

    public static final String DEFAULT_SORT_ORDER = Columns.CREATE_TIME_MS + " DESC";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + Columns._ID + " INTEGER PRIMARY KEY,"
            + Columns.CREATE_TIME + " TEXT NOT NULL,"
            + Columns.CREATE_TIME_MS + " INTEGER NOT NULL,"
            + Columns.FILE_NAME + " TEXT NOT NULL"
            + ");";

    public static final class Columns implements BaseColumns {
        public static final String CREATE_TIME = "create_time";
        public static final String CREATE_TIME_MS = "create_time_ms";
        public static final String FILE_NAME = "file_name";
    }

    //
    public static HashMap<String, String> PROJECTION_MAP = new HashMap<>();

    static {
        PROJECTION_MAP.put(ImageTable.Columns._ID, ImageTable.Columns._ID);
        PROJECTION_MAP.put(ImageTable.Columns.CREATE_TIME, ImageTable.Columns.CREATE_TIME);
        PROJECTION_MAP.put(ImageTable.Columns.CREATE_TIME_MS, ImageTable.Columns.CREATE_TIME_MS);
        PROJECTION_MAP.put(ImageTable.Columns.FILE_NAME, ImageTable.Columns.FILE_NAME);
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
        Set<String> keySet = ImageTable.PROJECTION_MAP.keySet();
        return keySet.toArray(new String[keySet.size()]);
    }
}