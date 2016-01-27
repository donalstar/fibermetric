package com.guggiemedia.fibermetric.lib.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 */
public class EventModel implements DataBaseModel {
    private Long _id;
    private Date _timeStamp;
    private String _note;

    @Override
    public void setDefault() {
        _id = 0L;
        _timeStamp = new Date();
        _note = "No Note";
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(EventTable.Columns.TIMESTAMP, formatter(_timeStamp));
        cv.put(EventTable.Columns.TIMESTAMP_MS, _timeStamp.getTime());
        cv.put(EventTable.Columns.NOTE, _note);
        return cv;
    }

    @Override
    public void fromCursor(Cursor cursor) {
        _id = cursor.getLong(cursor.getColumnIndex(EventTable.Columns._ID));
        _timeStamp.setTime(cursor.getLong(cursor.getColumnIndex(EventTable.Columns.TIMESTAMP_MS)));
        _note = cursor.getString(cursor.getColumnIndex(EventTable.Columns.NOTE));
    }

    @Override
    public String getTableName() {
        return EventTable.TABLE_NAME;
    }

    @Override
    public Uri getTableUri() {
        return EventTable.CONTENT_URI;
    }

    public Long getId() {
        return _id;
    }
    public void setId(Long id) {
        _id = id;
    }

    public Date getTimeStamp() {
        return _timeStamp;
    }
    public void setTimeStamp(Date arg) {
        _timeStamp = arg;
    }

    public String getNote() {
        return _note;
    }
    public void setNote(String arg) {
        _note = arg;
    }

    private String formatter(Date arg) {
        //Sat, 18 Jun 2011 09:53:00 -0700
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
        return sdf.format(arg);
    }
}
