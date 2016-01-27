package com.guggiemedia.fibermetric.lib.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;


/**
 *
 */
public class TaskDetailModel implements DataBaseModel {
    private Long _id;
    private Long _parent;
    private Integer _orderNdx;
    private String _description;

    @Override
    public void setDefault() {
        _id = 0L;
        _parent = 0L;
        _orderNdx = 0;
        _description = "No Description";
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(TaskDetailTable.Columns.PARENT, _parent);
        cv.put(TaskDetailTable.Columns.ORDER_NDX, _orderNdx);
        cv.put(TaskDetailTable.Columns.DESCRIPTION, _description);
        return cv;
    }

    @Override
    public void fromCursor(Cursor cursor) {
        _id = cursor.getLong(cursor.getColumnIndex(TaskDetailTable.Columns._ID));
        _parent = cursor.getLong(cursor.getColumnIndex(TaskDetailTable.Columns.PARENT));
        _orderNdx = cursor.getInt(cursor.getColumnIndex(TaskDetailTable.Columns.ORDER_NDX));
        _description = cursor.getString(cursor.getColumnIndex(TaskDetailTable.Columns.DESCRIPTION));
    }

    @Override
    public String getTableName() {
        return JobTaskTable.TABLE_NAME;
    }

    @Override
    public Uri getTableUri() {
        return JobTaskTable.CONTENT_URI;
    }

    public Long getId() {
        return _id;
    }
    public void setId(Long id) {
        _id = id;
    }

    public Long getParent() {
        return _parent;
    }
    public void setParent(long arg) {
        _parent = arg;
    }

    public Integer getOrderNdx() {
        return _orderNdx;
    }
    public void setOrderNdx(int arg) {
        _orderNdx = arg;
    }

    public String getDescription() {
        return _description;
    }
    public void setDescription(String arg) {
        _description = arg;
    }
}