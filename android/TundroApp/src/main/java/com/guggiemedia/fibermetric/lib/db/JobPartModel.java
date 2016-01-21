package com.guggiemedia.fibermetric.lib.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;


import java.io.Serializable;

/**
 *
 */
public class JobPartModel implements DataBaseModel, Serializable {
    public static final String PROVIDER = "Fibermetric";

    private Long _id;
    private Long _jobtask_id;
    private Long _part_id;

    @Override
    public void setDefault() {
        _id = 0L;

        _jobtask_id = 0L;
        _part_id = 0L;
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues cv = new ContentValues();

        cv.put(JobPartTable.Columns.JOBTASK_ID, _jobtask_id);
        cv.put(JobPartTable.Columns.PART_ID, _part_id);

        return cv;
    }

    @Override
    public void fromCursor(Cursor cursor) {
        _id = cursor.getLong(cursor.getColumnIndex(JobPartTable.Columns._ID));
        _jobtask_id = cursor.getLong(cursor.getColumnIndex(JobPartTable.Columns.JOBTASK_ID));
        _part_id = cursor.getLong(cursor.getColumnIndex(JobPartTable.Columns.PART_ID));
    }

    @Override
    public String getTableName() {
        return JobPartTable.TABLE_NAME;
    }

    @Override
    public Uri getTableUri() {
        return JobPartTable.CONTENT_URI;
    }

    public Long getId() {
        return _id;
    }

    public void setId(Long id) {
        _id = id;
    }

    public Long getPartId() {
        return _part_id;
    }

    public void setPartId(Long part_id) {
        this._part_id = part_id;
    }

    public Long getJobtaskId() {
        return _jobtask_id;
    }

    public void setJobtaskId(Long person_id) {
        this._jobtask_id = person_id;
    }
}