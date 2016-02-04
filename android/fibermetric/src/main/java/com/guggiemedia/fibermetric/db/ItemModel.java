package com.guggiemedia.fibermetric.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import java.io.Serializable;

/**
 *
 */
public class ItemModel implements DataBaseModel, Serializable {
    private Long _id;
    private String _name;
    private String _portion;
    private PortionTypeEnum _portionType;
    private Double _grams;
    private ItemTypeEnum _type;

    @Override
    public void setDefault() {
        _id = 0L;

        _name = "Unknown";
        _portion = "Unknown";
        _portionType = PortionTypeEnum.UNKNOWN;
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues cv = new ContentValues();

        cv.put(ItemTable.Columns.NAME, _name);
        cv.put(ItemTable.Columns.PORTION, _portion);
        cv.put(ItemTable.Columns.PORTION_TYPE, _portionType.toString());
        cv.put(ItemTable.Columns.GRAMS, _grams);
        cv.put(ItemTable.Columns.TYPE, _type.toString());

        return cv;
    }

    @Override
    public void fromCursor(Cursor cursor) {
        _id = cursor.getLong(cursor.getColumnIndex(ItemTable.Columns._ID));
        _name = cursor.getString(cursor.getColumnIndex(ItemTable.Columns.NAME));
        _portion = cursor.getString(cursor.getColumnIndex(ItemTable.Columns.PORTION));
        _portionType = PortionTypeEnum.discoverMatchingEnum(cursor.getString(cursor.getColumnIndex(ItemTable.Columns.PORTION_TYPE)));
        _grams = cursor.getDouble(cursor.getColumnIndex(ItemTable.Columns.GRAMS));
        _type = ItemTypeEnum.discoverMatchingEnum(cursor.getString(cursor.getColumnIndex(ItemTable.Columns.TYPE)));
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

    public String getName() {
        return _name;
    }

    public void setName(String arg) {
        _name = arg;
    }


    public String getPortion() {
        return _portion;
    }

    public PortionTypeEnum getPortionType() {
        return _portionType;
    }

    public void setPortionType(PortionTypeEnum portionType) {
        this._portionType = portionType;
    }

    public void setPortion(String _serial) {
        this._portion = _serial;
    }

    public Double getGrams() {
        return _grams;
    }

    public void setGrams(Double grams) {
        this._grams = grams;
    }

    public ItemTypeEnum getType() {
        return _type;
    }

    public void setType(ItemTypeEnum type) {
        this._type = type;
    }
}
