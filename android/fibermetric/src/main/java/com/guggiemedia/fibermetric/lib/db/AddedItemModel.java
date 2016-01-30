package com.guggiemedia.fibermetric.lib.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import java.io.Serializable;

/**
 *
 */
public class AddedItemModel implements DataBaseModel, Serializable {
    private Long _id;
    private Long _itemId;

    @Override
    public void setDefault() {
        _id = 0L;
        _itemId = 0L;
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues cv = new ContentValues();

        cv.put(AddedItemTable.Columns.ITEM_ID, _itemId);

        return cv;
    }

    @Override
    public void fromCursor(Cursor cursor) {
        _id = cursor.getLong(cursor.getColumnIndex(AddedItemTable.Columns._ID));
        _itemId = cursor.getLong(cursor.getColumnIndex(AddedItemTable.Columns.ITEM_ID));
    }

    @Override
    public String getTableName() {
        return AddedItemTable.TABLE_NAME;
    }

    @Override
    public Uri getTableUri() {
        return AddedItemTable.CONTENT_URI;
    }

    public Long getId() {
        return _id;
    }

    public void setId(Long id) {
        _id = id;
    }

    public Long getItemId() {
        return _itemId;
    }

    public void setItemId(Long itemId) {
        this._itemId = itemId;
    }
}
