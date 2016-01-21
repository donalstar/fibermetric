package com.guggiemedia.fibermetric.lib.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;


/**
 *
 */
public class TaskActionModel implements DataBaseModel {
    private Long _id;
    private Long _parent;
    private Integer _orderNdx;
    private String _description;
    private TaskActionEnum _taskAction;
    private TaskActionStateEnum _taskState;

    @Override
    public void setDefault() {
        _id = 0L;
        _parent = 0L;
        _orderNdx = 0;
        _description = "No Description";
        _taskAction = TaskActionEnum.UNKNOWN;
        _taskState = TaskActionStateEnum.UNKNOWN;
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(TaskActionTable.Columns.PARENT, _parent);
        cv.put(TaskActionTable.Columns.ORDER_NDX, _orderNdx);
        cv.put(TaskActionTable.Columns.DESCRIPTION, _description);
        cv.put(TaskActionTable.Columns.ACTION, _taskAction.toString());
        cv.put(TaskActionTable.Columns.STATE, _taskState.toString());
        return cv;
    }

    @Override
    public void fromCursor(Cursor cursor) {
        _id = cursor.getLong(cursor.getColumnIndex(TaskActionTable.Columns._ID));
        _parent = cursor.getLong(cursor.getColumnIndex(TaskActionTable.Columns.PARENT));
        _orderNdx = cursor.getInt(cursor.getColumnIndex(TaskActionTable.Columns.ORDER_NDX));
        _description = cursor.getString(cursor.getColumnIndex(TaskActionTable.Columns.DESCRIPTION));
        _taskAction = TaskActionEnum.discoverMatchingEnum(cursor.getString(cursor.getColumnIndex(TaskActionTable.Columns.ACTION)));
        _taskState = TaskActionStateEnum.discoverMatchingEnum(cursor.getString(cursor.getColumnIndex(TaskActionTable.Columns.STATE)));
    }

    @Override
    public String getTableName() {
        return TaskActionTable.TABLE_NAME;
    }

    @Override
    public Uri getTableUri() {
        return TaskActionTable.CONTENT_URI;
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

    public TaskActionEnum getAction() {
        return _taskAction;
    }
    public void setAction(TaskActionEnum arg) {
        _taskAction = arg;
    }

    public TaskActionStateEnum getState() {
        return _taskState;
    }
    public void setState(TaskActionStateEnum arg) {
        _taskState = arg;
    }
}