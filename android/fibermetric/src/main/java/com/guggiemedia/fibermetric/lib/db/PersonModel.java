package com.guggiemedia.fibermetric.lib.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;


/**
 *
 */
public class PersonModel implements DataBaseModel {
    private Long _id;
    private String _email;
    private String _nameUser;
    private String _nameLast;
    private String _nameFirst;
    private String _nameSuffix;
    private String _phone;
    private double _latitude;
    private double _longitude;
    private long _fixTimeMs;
    private String _note;

    @Override
    public void setDefault() {
        _id = 0L;
        _email = "Unknown";
        _nameUser = "user";
        _nameLast = "last";
        _nameFirst = "first";
        _nameSuffix = "";
        _phone = "";
        _latitude = 0;
        _longitude = 0;
        _fixTimeMs = 0;
        _note = "No Note";
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(PersonTable.Columns.EMAIL, _email);
        cv.put(PersonTable.Columns.NAME_USER, _nameUser.toLowerCase());
        cv.put(PersonTable.Columns.NAME_LAST, _nameLast);
        cv.put(PersonTable.Columns.NAME_FIRST, _nameFirst);
        cv.put(PersonTable.Columns.NAME_SUFFIX, _nameSuffix);
        cv.put(PersonTable.Columns.PHONE, _phone);
        cv.put(PersonTable.Columns.LATITUDE, _latitude);
        cv.put(PersonTable.Columns.LONGITUDE, _longitude);
        cv.put(PersonTable.Columns.FIX_TIME_MS, _fixTimeMs);
        cv.put(PersonTable.Columns.NOTE, _note);
        return cv;
    }

    @Override
    public void fromCursor(Cursor cursor) {
        _id = cursor.getLong(cursor.getColumnIndex(PersonTable.Columns._ID));
        _email = cursor.getString(cursor.getColumnIndex(PersonTable.Columns.EMAIL));
        _nameUser = cursor.getString(cursor.getColumnIndex(PersonTable.Columns.NAME_USER));
        _nameLast = cursor.getString(cursor.getColumnIndex(PersonTable.Columns.NAME_LAST));
        _nameFirst = cursor.getString(cursor.getColumnIndex(PersonTable.Columns.NAME_FIRST));
        _nameSuffix = cursor.getString(cursor.getColumnIndex(PersonTable.Columns.NAME_SUFFIX));
        _phone = cursor.getString(cursor.getColumnIndex(PersonTable.Columns.PHONE));
        _latitude = cursor.getDouble(cursor.getColumnIndex(PersonTable.Columns.LATITUDE));
        _longitude = cursor.getDouble(cursor.getColumnIndex(PersonTable.Columns.LONGITUDE));
        _fixTimeMs = cursor.getLong(cursor.getColumnIndex(PersonTable.Columns.FIX_TIME_MS));
        _note = cursor.getString(cursor.getColumnIndex(PersonTable.Columns.NOTE));
    }

    @Override
    public String getTableName() {
        return PersonTable.TABLE_NAME;
    }

    @Override
    public Uri getTableUri() {
        return PersonTable.CONTENT_URI;
    }

    public Long getId() {
        return _id;
    }

    public void setId(Long id) {
        _id = id;
    }

    public String getNameUser() {
        return _nameUser;
    }

    public void setNameUser(String arg) {
        _nameUser = arg;
    }

    public String getNameLast() {
        return _nameLast;
    }

    public void setNameLast(String arg) {
        _nameLast = arg;
    }

    public String getNameFirst() {
        return _nameFirst;
    }

    public void setNameFirst(String arg) {
        _nameFirst = arg;
    }

    public String getNameSuffix() {
        return _nameSuffix;
    }

    public void setNameSuffix(String arg) {
        _nameSuffix = arg;
    }

    public String getPhone() {
        return _phone;
    }

    public void setPhone(String phone) {
        this._phone = phone;
    }

    public double getLatitude() {
        return _latitude;
    }

    public void setLatitude(double arg) {
        _latitude = arg;
    }

    public double getLongitude() {
        return _longitude;
    }

    public void setLongitude(double arg) {
        _longitude = arg;
    }

    public long getFixTimeMs() {
        return _fixTimeMs;
    }

    public void setFixTimeMs(long arg) {
        _fixTimeMs = arg;
    }

    public String getEmail() {
        return _email;
    }

    public void setEmail(String arg) {
        _email = arg;
    }

    public String getNote() {
        return _note;
    }

    public void setNote(String arg) {
        _note = arg;
    }

    public String getName() {
        return _nameFirst + " " + _nameLast + " " + _nameSuffix;
    }
}
