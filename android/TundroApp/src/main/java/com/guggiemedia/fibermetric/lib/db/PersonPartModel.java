package com.guggiemedia.fibermetric.lib.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;


import java.io.Serializable;

/**
 *
 */
public class PersonPartModel implements DataBaseModel, Serializable {
    public static final String PROVIDER = "Fibermetric";

    private Long _id;
    private Long _person_id;
    private Long _part_id;

    @Override
    public void setDefault() {
        _id = 0L;

        _person_id = 0L;
        _part_id = 0L;
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues cv = new ContentValues();

        cv.put(PersonPartTable.Columns.PERSON_ID, _person_id);
        cv.put(PersonPartTable.Columns.PART_ID, _part_id);

        return cv;
    }

    @Override
    public void fromCursor(Cursor cursor) {
        _id = cursor.getLong(cursor.getColumnIndex(PersonPartTable.Columns._ID));
        _person_id = cursor.getLong(cursor.getColumnIndex(PersonPartTable.Columns.PERSON_ID));
        _part_id = cursor.getLong(cursor.getColumnIndex(PersonPartTable.Columns.PART_ID));
    }

    @Override
    public String getTableName() {
        return PersonPartTable.TABLE_NAME;
    }

    @Override
    public Uri getTableUri() {
        return PersonPartTable.CONTENT_URI;
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

    public Long getPersonId() {
        return _person_id;
    }

    public void setPersonId(Long person_id) {
        this._person_id = person_id;
    }
}