package com.guggiemedia.fibermetric.lib.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;


import com.guggiemedia.fibermetric.lib.Constant;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 */
public class AlertModel implements DataBaseModel {
    private Long _id;
    private boolean _activeFlag;
    private AlertEnum _alertEnum;
    private Long _counter;
    private Long _thingId;
    private String _thingName;
    private Date _startTime;
    private Date _endTime;
    private String _note;

    @Override
    public void setDefault() {
        _id = 0L;
        _activeFlag = false;
        _alertEnum = AlertEnum.UNKNOWN;
        _counter = 0L;
        _thingId = 0L;
        _thingName = "Unknown";
        _startTime = new Date();
        _endTime = new Date();
        _note = "No Note";
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(AlertTable.Columns.ACTIVE_FLAG, (_activeFlag) ? Constant.SQL_TRUE:Constant.SQL_FALSE);
        cv.put(AlertTable.Columns.ALERT_TYPE, _alertEnum.toString());
        cv.put(AlertTable.Columns.COUNTER, _counter);
        cv.put(AlertTable.Columns.THING_ID, _thingId);
        cv.put(AlertTable.Columns.THING_NAME, _thingName);
        cv.put(AlertTable.Columns.START_TIME, formatter(_startTime));
        cv.put(AlertTable.Columns.START_TIME_MS, _startTime.getTime());
        cv.put(AlertTable.Columns.LAST_TIME, formatter(_endTime));
        cv.put(AlertTable.Columns.LAST_TIME_MS, _endTime.getTime());
        cv.put(AlertTable.Columns.NOTE, _note);
        return cv;
    }

    @Override
    public void fromCursor(Cursor cursor) {
        _id = cursor.getLong(cursor.getColumnIndex(AlertTable.Columns._ID));
        _activeFlag = (cursor.getInt(cursor.getColumnIndex(AlertTable.Columns.ACTIVE_FLAG)) != 0);
        _alertEnum = AlertEnum.discoverMatchingEnum(cursor.getString(cursor.getColumnIndex(AlertTable.Columns.ALERT_TYPE)));
        _counter = cursor.getLong(cursor.getColumnIndex(AlertTable.Columns.COUNTER));
        _thingId = cursor.getLong(cursor.getColumnIndex(AlertTable.Columns.THING_ID));
        _thingName = cursor.getString(cursor.getColumnIndex(AlertTable.Columns.THING_NAME));
        _startTime.setTime(cursor.getLong(cursor.getColumnIndex(AlertTable.Columns.START_TIME_MS)));
        _endTime.setTime(cursor.getLong(cursor.getColumnIndex(AlertTable.Columns.LAST_TIME_MS)));
        _note = cursor.getString(cursor.getColumnIndex(AlertTable.Columns.NOTE));
    }

    @Override
    public String getTableName() {
        return AlertTable.TABLE_NAME;
    }

    @Override
    public Uri getTableUri() {
        return AlertTable.CONTENT_URI;
    }

    public Long getId() {
        return _id;
    }
    public void setId(Long id) {
        _id = id;
    }

    public Boolean isActive() {
        return _activeFlag;
    }

    public void setActiveFlag(boolean arg) {
        _activeFlag = arg;
    }

    public AlertEnum getAlertType() {
        return _alertEnum;
    }

    public void setAlertType(AlertEnum arg) {
        _alertEnum = arg;
    }

    public Long getCounter() {
        return _counter;
    }

    public void setCounter(Long arg) {
        _counter = arg;
    }

    public Long getThingId() {
        return _thingId;
    }

    public void setThingId(Long arg) {
        _thingId = arg;
    }

    public String getThingName() {
        return _thingName;
    }

    public void setThingName(String arg) {
        _thingName = arg;
    }

    public Date getStartTime() {
        return _startTime;
    }

    public void setStartTime(Date arg) {
        _startTime = arg;
    }

    public Date getEndTime() {
        return _endTime;
    }

    public void setEndTime(Date arg) {
        _endTime = arg;
    }

    public String getNote() {
        return _note;
    }

    public void setNote(String arg) {
        _note = arg;
    }

    public String formatter(Date arg) {
        //Sat, 18 Jun 2011 09:53:00 -0700
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
        return sdf.format(arg);
    }
}
