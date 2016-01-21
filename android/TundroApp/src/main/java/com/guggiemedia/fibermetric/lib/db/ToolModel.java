package com.guggiemedia.fibermetric.lib.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;



/**
 *
 */
public class ToolModel implements DataBaseModel {
    private Long _id;
    private Long _job;
    private Long _thing;

    @Override
    public void setDefault() {
        _id = 0L;
        _job = 0L;
        _thing = 0L;
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(ToolTable.Columns.JOB, _job);
        cv.put(ToolTable.Columns.THING, _thing);
        return cv;
    }
    @Override
    public void fromCursor(Cursor cursor) {
        _id = cursor.getLong(cursor.getColumnIndex(ToolTable.Columns._ID));
        _job = cursor.getLong(cursor.getColumnIndex(ToolTable.Columns.JOB));
        _thing = cursor.getLong(cursor.getColumnIndex(ToolTable.Columns.THING));
    }

    @Override
    public String getTableName() {
        return ToolTable.TABLE_NAME;
    }

    @Override
    public Uri getTableUri() {
        return ToolTable.CONTENT_URI;
    }

    public Long getId() {
        return _id;
    }
    public void setId(Long id) {
        _id = id;
    }

    public Long getJob() {
        return _job;
    }
    public void setJob(Long job) {
        _job = job;
    }

    public Long getThing() {
        return _thing;
    }
    public void setThing(Long thing) {
        _thing = thing;
    }
}
