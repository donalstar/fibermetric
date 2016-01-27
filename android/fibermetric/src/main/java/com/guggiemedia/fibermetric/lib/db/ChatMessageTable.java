package com.guggiemedia.fibermetric.lib.db;

import android.net.Uri;
import android.provider.BaseColumns;

import com.guggiemedia.fibermetric.lib.Constant;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by donal on 9/29/15.
 */
public class ChatMessageTable implements DataBaseTable {
    public static final String TABLE_NAME = "chat_message";

    public static final Uri CONTENT_URI = Uri.parse("content://" + Constant.AUTHORITY + "/" + TABLE_NAME);

    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.gofactory." + TABLE_NAME;

    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.gofactory." + TABLE_NAME;

    public static final String DEFAULT_SORT_ORDER = Columns.MESSAGE_TIME_MS + " ASC";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + Columns._ID + " INTEGER PRIMARY KEY,"
            + Columns.MESSAGE + " TEXT NOT NULL,"
            + Columns.MESSAGE_TIME + " TEXT NOT NULL,"
            + Columns.MESSAGE_TIME_MS + " INTEGER NOT NULL,"
            + Columns.TYPE + " TEXT NOT NULL,"
            + Columns.CHAT_ID + " INTEGER NOT NULL"
            + ");";


    public static final String TABLE_JOIN_CHAT_TABLE
            = "chat_message as chat_message" +
            "    INNER JOIN chat AS chat" +
            "    ON chat._id = chat_message.chat_id";

    public static final class Columns implements BaseColumns {
        public static final String MESSAGE = "message";
        public static final String MESSAGE_TIME = "message_time";
        public static final String MESSAGE_TIME_MS = "message_time_ms";
        public static final String TYPE = "type";
        public static final String CHAT_ID = "chat_id";
    }

    public static HashMap<String, String> PROJECTION_MAP = new HashMap<>();

    static {
        PROJECTION_MAP.put(ChatMessageTable.TABLE_NAME + "." + Columns._ID, Columns._ID);
        PROJECTION_MAP.put(Columns.MESSAGE, Columns.MESSAGE);
        PROJECTION_MAP.put(ChatTable.Columns.PARTICIPANT, ChatTable.Columns.PARTICIPANT);
        PROJECTION_MAP.put(Columns.MESSAGE_TIME, Columns.MESSAGE_TIME);
        PROJECTION_MAP.put(Columns.MESSAGE_TIME_MS, Columns.MESSAGE_TIME_MS);
        PROJECTION_MAP.put(ChatMessageTable.TABLE_NAME + "." + Columns.TYPE, Columns.TYPE);
        PROJECTION_MAP.put(Columns.CHAT_ID, Columns.CHAT_ID);

        // from Chat table
        PROJECTION_MAP.put(ChatTable.Columns.PERSON_ID, ChatTable.Columns.PERSON_ID);
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
        Set<String> keySet = ChatMessageTable.PROJECTION_MAP.keySet();
        return keySet.toArray(new String[keySet.size()]);
    }
}

