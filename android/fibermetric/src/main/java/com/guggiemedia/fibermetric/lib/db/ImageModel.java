package com.guggiemedia.fibermetric.lib.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 */
public class ImageModel implements DataBaseModel {
    private Long _id;
    private Date _createTime;
    private String _fileName;

    @Override
    public void setDefault() {
        _id = 0L;
        _createTime = new Date();
        _fileName = "Unknown";
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(ImageTable.Columns.CREATE_TIME, formatter(_createTime));
        cv.put(ImageTable.Columns.CREATE_TIME_MS, _createTime.getTime());
        cv.put(ImageTable.Columns.FILE_NAME, _fileName);
        return cv;
    }

    @Override
    public void fromCursor(Cursor cursor) {
        _id = cursor.getLong(cursor.getColumnIndex(ImageTable.Columns._ID));
        _createTime.setTime(cursor.getLong(cursor.getColumnIndex(ImageTable.Columns.CREATE_TIME_MS)));
        _fileName = cursor.getString(cursor.getColumnIndex(ImageTable.Columns.FILE_NAME));
    }

    @Override
    public String getTableName() {
        return ImageTable.TABLE_NAME;
    }

    @Override
    public Uri getTableUri() {
        return ImageTable.CONTENT_URI;
    }

    public Long getId() {
        return _id;
    }
    public void setId(Long id) {
        _id = id;
    }

    public Date getCreateTime() {
        return _createTime;
    }
    public void setCreateTime(Date arg) {
        _createTime = arg;
    }

    public String getFileName() {
        return _fileName;
    }
    public void setFileName(String arg) {
        _fileName = arg;
    }

    public String formatter(Date arg) {
        //Sat, 18 Jun 2011 09:53:00 -0700
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
        return sdf.format(arg);
    }
}
