package com.guggiemedia.fibermetric.lib.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 */
public class ChatMessageModel implements DataBaseModel {
    private Long _id;
    String _message;
    String _name;
    Date _messageTime;
    ChatMessageTypeEnum _type;
    private Long _chat_id;
    private Long _person_id;

    @Override
    public void setDefault() {
        _id = 0L;
        _name = "Unknown";
        _message = "None";
        _messageTime = new Date();
        _type = ChatMessageTypeEnum.outbound;
        _chat_id = 0L;
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(ChatMessageTable.Columns.MESSAGE, _message);
        cv.put(ChatMessageTable.Columns.MESSAGE_TIME, formatter(_messageTime));
        cv.put(ChatMessageTable.Columns.MESSAGE_TIME_MS, _messageTime.getTime());
        cv.put(ChatMessageTable.Columns.TYPE, _type.toString());
        cv.put(ChatMessageTable.Columns.CHAT_ID, _chat_id);

        return cv;
    }

    @Override
    public void fromCursor(Cursor cursor) {

        _message = cursor.getString(cursor.getColumnIndex(ChatMessageTable.Columns.MESSAGE));
        _messageTime.setTime(cursor.getLong(cursor.getColumnIndex(ChatMessageTable.Columns.MESSAGE_TIME_MS)));
        _type = ChatMessageTypeEnum.discoverMatchingEnum(cursor.getString(cursor.getColumnIndex(ChatMessageTable.Columns.TYPE)));
        _chat_id = cursor.getLong(cursor.getColumnIndex(ChatMessageTable.Columns.CHAT_ID));

        _name = cursor.getString(cursor.getColumnIndex(ChatTable.Columns.PARTICIPANT));

        if (!cursor.isNull(cursor.getColumnIndex(ChatTable.Columns.PERSON_ID))) {
            _person_id = cursor.getLong(cursor.getColumnIndex(ChatTable.Columns.PERSON_ID));
        }
    }

    @Override
    public String getTableName() {
        return ChatMessageTable.TABLE_NAME;
    }

    @Override
    public Uri getTableUri() {
        return ChatMessageTable.CONTENT_URI;
    }

    public Long getId() {
        return _id;
    }

    public void setId(Long id) {
        _id = id;
    }

    public String getName() {
        return _name;
    }

    public void setName(String arg) {
        _name = arg;
    }

    public String getMessage() {
        return _message;
    }

    public void setMessage(String arg) {
        _message = arg;
    }

    public Date getMessageTime() {
        return _messageTime;
    }

    public void setMessageTime(Date arg) {
        _messageTime = arg;
    }

    public ChatMessageTypeEnum getType() {
        return _type;
    }

    public void setType(ChatMessageTypeEnum arg) {
        _type = arg;
    }

    public Long getChatId() {
        return _chat_id;
    }

    public void setChatId(Long id) {
        _chat_id = id;
    }

    public Long getPersonId() {
        return _person_id;
    }

    public void setPersonId(Long personId) {
        this._person_id = personId;
    }

    public String formatter(Date arg) {
        //Sat, 18 Jun 2011 09:53:00 -0700
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
        return sdf.format(arg);
    }
}
