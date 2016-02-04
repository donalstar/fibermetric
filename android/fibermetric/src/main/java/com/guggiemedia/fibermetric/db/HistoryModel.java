package com.guggiemedia.fibermetric.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import java.io.Serializable;
import java.util.Date;


/**
 *
 */
public class HistoryModel implements DataBaseModel, Serializable {
    private Long _id;
    private Double _fruit;
    private Double _veg;
    private Double _grain;
    private Double _total;
    private Date _date;

    @Override
    public void setDefault() {
        _id = 0L;

        _fruit = 0.0;
        _veg = 0.0;
        _grain = 0.0;
        _total = 0.0;
        _date = new Date();
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues cv = new ContentValues();

        cv.put(HistoryTable.Columns.FRUIT, _fruit);
        cv.put(HistoryTable.Columns.VEG, _veg);
        cv.put(HistoryTable.Columns.GRAIN, _grain);
        cv.put(HistoryTable.Columns.TOTAL, _total);
        cv.put(HistoryTable.Columns.DATE, _date.getTime());

        return cv;
    }

    @Override
    public void fromCursor(Cursor cursor) {
        _id = cursor.getLong(cursor.getColumnIndex(HistoryTable.Columns._ID));
        _fruit = cursor.getDouble(cursor.getColumnIndex(HistoryTable.Columns.FRUIT));
        _veg = cursor.getDouble(cursor.getColumnIndex(HistoryTable.Columns.VEG));
        _grain = cursor.getDouble(cursor.getColumnIndex(HistoryTable.Columns.GRAIN));
        _total = cursor.getDouble(cursor.getColumnIndex(HistoryTable.Columns.TOTAL));
        _date = new Date(cursor.getLong(cursor.getColumnIndex(HistoryTable.Columns.DATE)));
    }

    @Override
    public String getTableName() {
        return ItemTable.TABLE_NAME;
    }

    @Override
    public Uri getTableUri() {
        return ItemTable.CONTENT_URI;
    }

    public Long getId() {
        return _id;
    }

    public void setId(Long id) {
        _id = id;
    }

    public Double getFruit() {
        return _fruit;
    }

    public void setFruit(Double fruit) {
        this._fruit = fruit;
    }

    public Double getVeg() {
        return _veg;
    }

    public void setVeg(Double veg) {
        this._veg = veg;
    }

    public Double getGrain() {
        return _grain;
    }

    public void setGrain(Double grain) {
        this._grain = grain;
    }

    public Double getTotal() {
        return _total;
    }

    public void setTotal(Double total) {
        this._total = total;
    }

    public Date getDate() {
        return _date;
    }

    public void setDate(Date date) {
        this._date = date;
    }
}
