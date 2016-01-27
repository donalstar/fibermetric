package com.guggiemedia.fibermetric.lib.db;

import android.net.Uri;
import android.provider.BaseColumns;

import com.guggiemedia.fibermetric.lib.Constant;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by donal on 9/29/15.
 */
public class ChatTable  implements DataBaseTable {
    public static final String TABLE_NAME = "chat";

    public static final Uri CONTENT_URI = Uri.parse("content://" + Constant.AUTHORITY + "/" + TABLE_NAME);

    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.gofactory." + TABLE_NAME;

    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.gofactory." + TABLE_NAME;

    public static final String DEFAULT_SORT_ORDER = Columns.PARTICIPANT + " ASC";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + Columns._ID + " INTEGER PRIMARY KEY,"
            + Columns.PARTICIPANT + " TEXT,"
            + Columns.PERSON_ID + " INTEGER,"
            + Columns.TYPE + " TEXT NOT NULL,"
            + Columns.LAST_MESSAGE + " TEXT NOT NULL,"
            + Columns.LAST_MESSAGE_TIME_MS +  " INTEGER NOT NULL"
            + ");";

    public static final class Columns implements BaseColumns {
        public static final String PARTICIPANT = "participant";
        public static final String PERSON_ID = "person_id";
        public static final String TYPE = "type";
        public static final String LAST_MESSAGE = "last_message";
        public static final String LAST_MESSAGE_TIME_MS = "last_message_time_ms";
    }

    public static HashMap<String, String> PROJECTION_MAP = new HashMap<>();

    static {
        PROJECTION_MAP.put(Columns._ID, Columns._ID);
        PROJECTION_MAP.put(Columns.PARTICIPANT, Columns.PARTICIPANT);
        PROJECTION_MAP.put(Columns.PERSON_ID, Columns.PERSON_ID);
        PROJECTION_MAP.put(Columns.TYPE, Columns.TYPE);
        PROJECTION_MAP.put(Columns.LAST_MESSAGE, Columns.LAST_MESSAGE);
        PROJECTION_MAP.put(Columns.LAST_MESSAGE_TIME_MS, Columns.LAST_MESSAGE_TIME_MS);
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
        Set<String> keySet = ChatTable.PROJECTION_MAP.keySet();
        return keySet.toArray(new String[keySet.size()]);
    }
}

