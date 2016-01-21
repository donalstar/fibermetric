package com.guggiemedia.fibermetric.lib.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;


import java.util.Date;

/**
 * Created by donal on 9/28/15.
 */
public class ChatModel implements DataBaseModel {

    private Long _id;
    private String _participant;
    private Long _personId;
    private ChatTypeEnum _type;
    private String _lastMessage;
    private Date _lastMessageTime;

    @Override
    public void setDefault() {
        _id = 0L;
        _participant = "Unknown";
        _type = ChatTypeEnum.oneOnOne;
        _lastMessage = "";
        _lastMessageTime = new Date();
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(ChatTable.Columns.PARTICIPANT, _participant);
        cv.put(ChatTable.Columns.PERSON_ID, _personId);
        cv.put(ChatTable.Columns.TYPE, _type.toString());
        cv.put(ChatTable.Columns.LAST_MESSAGE, _lastMessage);
        cv.put(ChatTable.Columns.LAST_MESSAGE_TIME_MS, _lastMessageTime.getTime());

        return cv;
    }

    @Override
    public void fromCursor(Cursor cursor) {
        _id = cursor.getLong(cursor.getColumnIndex(ChatTable.Columns._ID));
        _participant = cursor.getString(cursor.getColumnIndex(ChatTable.Columns.PARTICIPANT));

        if (!cursor.isNull(cursor.getColumnIndex(ChatTable.Columns.PERSON_ID))) {
            _personId = cursor.getLong(cursor.getColumnIndex(ChatTable.Columns.PERSON_ID));
        }

        _type = ChatTypeEnum.discoverMatchingEnum(cursor.getString(cursor.getColumnIndex(ChatTable.Columns.TYPE)));
        _lastMessage = cursor.getString(cursor.getColumnIndex(ChatTable.Columns.LAST_MESSAGE));
        _lastMessageTime.setTime(cursor.getLong(cursor.getColumnIndex(ChatTable.Columns.LAST_MESSAGE_TIME_MS)));
    }

    @Override
    public String getTableName() {
        return ChatTable.TABLE_NAME;
    }

    @Override
    public Uri getTableUri() {
        return ChatTable.CONTENT_URI;
    }

    public Long getId() {
        return _id;
    }

    public void setId(Long id) {
        _id = id;
    }

    public String getParticipant() {
        return _participant;
    }

    public void setParticipant(String participant) {
        this._participant = participant;
    }

    public Long getPersonId() {
        return _personId;
    }

    public void setPersonId(Long personId) {
        this._personId = personId;
    }

    public ChatTypeEnum getType() {
        return _type;
    }

    public void setType(ChatTypeEnum _type) {
        this._type = _type;
    }

    public String getLastMessage() {
        return _lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        _lastMessage = lastMessage;
    }

    public Date getLastMessageTime() {
        return _lastMessageTime;
    }

    public void setLastMessageTime(Date lastMessageTime) {
        this._lastMessageTime = lastMessageTime;
    }
}
