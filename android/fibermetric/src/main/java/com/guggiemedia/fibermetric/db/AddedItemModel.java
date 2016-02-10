package com.guggiemedia.fibermetric.db;

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
    private Long _dailyRecordId;
    private String _selectedPortion;

    private String _name;
    private Double _grams;
    private ItemTypeEnum _type;
    private Double _weightMultiple;


    @Override
    public void setDefault() {
        _id = 0L;
        _itemId = 0L;
        _dailyRecordId = 0L;
        _selectedPortion = "Unknown";
        _weightMultiple = 1.0;

        _name = "Unknown";
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues cv = new ContentValues();

        cv.put(AddedItemTable.Columns.ITEM_ID, _itemId);
        cv.put(AddedItemTable.Columns.DAILY_RECORD_ID, _dailyRecordId);
        cv.put(AddedItemTable.Columns.SELECTED_PORTION, _selectedPortion);
        cv.put(AddedItemTable.Columns.WEIGHT_MULTIPLE, _weightMultiple);

        return cv;
    }

    @Override
    public void fromCursor(Cursor cursor) {
        _id = cursor.getLong(cursor.getColumnIndex(AddedItemTable.Columns._ID));
        _itemId = cursor.getLong(cursor.getColumnIndex(AddedItemTable.Columns.ITEM_ID));
        _dailyRecordId = cursor.getLong(cursor.getColumnIndex(AddedItemTable.Columns.DAILY_RECORD_ID));
        _selectedPortion = cursor.getString(cursor.getColumnIndex(AddedItemTable.Columns.SELECTED_PORTION));
        _weightMultiple = cursor.getDouble(cursor.getColumnIndex(AddedItemTable.Columns.WEIGHT_MULTIPLE));

        if (cursor.getColumnIndex(ItemTable.Columns.NAME) != -1) {
            _name = cursor.getString(cursor.getColumnIndex(ItemTable.Columns.NAME));
            _grams = cursor.getDouble(cursor.getColumnIndex(ItemTable.Columns.GRAMS));
            _type = ItemTypeEnum.discoverMatchingEnum(cursor.getString(cursor.getColumnIndex(ItemTable.Columns.TYPE)));
        }
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

    public Long getDailyRecordId() {
        return _dailyRecordId;
    }

    public void setDailyRecordId(Long dailyRecordId) {
        this._dailyRecordId = dailyRecordId;
    }

    public String getName() {
        return _name;
    }

    public void setName(String arg) {
        _name = arg;
    }

    public String getSelectedPortion() {
        return _selectedPortion;
    }

    public void setSelectedPortion(String selectedPortion) {
        this._selectedPortion = selectedPortion;
    }

    public Double getWeightMultiple() {
        return _weightMultiple;
    }

    public void setWeightMultiple(Double weightMultiple) {
        this._weightMultiple = weightMultiple;
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
