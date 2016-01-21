package com.guggiemedia.fibermetric.lib.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.location.Location;
import android.net.Uri;


import com.guggiemedia.fibermetric.lib.Constant;

import java.io.Serializable;
import java.util.Date;

/**
 *
 */
public class PartModel implements DataBaseModel, Serializable {
    public static final String PROVIDER = "Fibermetric";

    // TODO: Lots of duplication with "Thing" object - which should we use?

    private Long _id;
    private String _name;
    private InventoryStatusEnum _status;
    private String _serial;
    private String _manufacturer;
    private String _modelNumber;
    private String _barcode;
    private Integer _bleInRange;
    private String _bleAddress;
    private String _owner;
    private Date _pickedUpDate;
    private String _condition;
    private Location _location;

    //    private String _locationName;
    private Long _siteId;
    private String _description;
    private InventoryCategoryEnum _category;

    @Override
    public void setDefault() {
        _id = 0L;

        _name = "Unknown";
        _status = InventoryStatusEnum.pickUp;
        _serial = "Unknown";
        _manufacturer = "Unknown";
        _modelNumber = "Unknown";
        _barcode = "Unknown";
        _bleAddress = "Unknown";
        _bleInRange = 0;
        _owner = "Unknown";
        _pickedUpDate = Constant.NO_DATE;
        _condition = "Unknown";
        _location = new Location(PROVIDER);
        _location.setLatitude(0.0);
        _location.setLongitude(0.0);
//        _locationName = "Unknown";
        _siteId = 0L;
        _description = "No Description";
        _category = InventoryCategoryEnum.unknown;
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues cv = new ContentValues();

        cv.put(PartTable.Columns.NAME, _name);
        cv.put(PartTable.Columns.STATUS, _status.toString());
        cv.put(PartTable.Columns.SERIAL, _serial);
        cv.put(PartTable.Columns.MANUFACTURER, _manufacturer);
        cv.put(PartTable.Columns.MODEL_NUMBER, _modelNumber);
        cv.put(PartTable.Columns.BARCODE, _barcode);
        cv.put(PartTable.Columns.BLE_ADDRESS, _bleAddress);
        cv.put(PartTable.Columns.BLE_IN_RANGE, _bleInRange);
        cv.put(PartTable.Columns.OWNER, _owner);
        cv.put(PartTable.Columns.PICKED_UP_DATE_TS, _pickedUpDate.getTime());
        cv.put(PartTable.Columns.CONDITION, _condition);
        cv.put(PartTable.Columns.LATITUDE, _location.getLatitude());
        cv.put(PartTable.Columns.LONGITUDE, _location.getLongitude());
//        cv.put(PartTable.Columns.LOCATION, _locationName);
        cv.put(PartTable.Columns.SITE_ID, _siteId);
        cv.put(PartTable.Columns.DESCRIPTION, _description);
        cv.put(PartTable.Columns.CATEGORY, _category.toString());

        return cv;
    }

    @Override
    public void fromCursor(Cursor cursor) {
        _id = cursor.getLong(cursor.getColumnIndex(PartTable.Columns._ID));
        _name = cursor.getString(cursor.getColumnIndex(PartTable.Columns.NAME));

        _status = InventoryStatusEnum.discoverMatchingEnum(
                cursor.getString(cursor.getColumnIndex(PartTable.Columns.STATUS)));

        _serial = cursor.getString(cursor.getColumnIndex(PartTable.Columns.SERIAL));
        _manufacturer = cursor.getString(cursor.getColumnIndex(PartTable.Columns.MANUFACTURER));
        _modelNumber = cursor.getString(cursor.getColumnIndex(PartTable.Columns.MODEL_NUMBER));
        _barcode = cursor.getString(cursor.getColumnIndex(PartTable.Columns.BARCODE));
        _bleAddress = cursor.getString(cursor.getColumnIndex(PartTable.Columns.BLE_ADDRESS));
        _bleInRange = cursor.getInt(cursor.getColumnIndex(PartTable.Columns.BLE_IN_RANGE));
        _owner = cursor.getString(cursor.getColumnIndex(PartTable.Columns.OWNER));
        _pickedUpDate.setTime(
                cursor.getLong(cursor.getColumnIndex(PartTable.Columns.PICKED_UP_DATE_TS)));

        _condition = cursor.getString(cursor.getColumnIndex(PartTable.Columns.CONDITION));

        _location = new Location(PROVIDER);
        _location.setLatitude(cursor.getDouble(cursor.getColumnIndex(PartTable.Columns.LATITUDE)));
        _location.setLongitude(cursor.getDouble(cursor.getColumnIndex(PartTable.Columns.LONGITUDE)));
        _siteId = cursor.getLong(cursor.getColumnIndex(PartTable.Columns.SITE_ID));

//        _locationName = cursor.getString(cursor.getColumnIndex(PartTable.Columns.LOCATION));
        _description = cursor.getString(cursor.getColumnIndex(PartTable.Columns.DESCRIPTION));

        _category =
                InventoryCategoryEnum.discoverMatchingEnum(
                        cursor.getString((cursor.getColumnIndex(PartTable.Columns.CATEGORY))));
    }

    @Override
    public String getTableName() {
        return PartTable.TABLE_NAME;
    }

    @Override
    public Uri getTableUri() {
        return PartTable.CONTENT_URI;
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

    public InventoryStatusEnum getStatus() {
        return _status;
    }

    public void setStatus(InventoryStatusEnum _status) {
        this._status = _status;
    }

    public String getSerial() {
        return _serial;
    }

    public void setSerial(String _serial) {
        this._serial = _serial;
    }

    public String getManufacturer() {
        return _manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this._manufacturer = manufacturer;
    }

    public String getModelNumber() {
        return _modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this._modelNumber = modelNumber;
    }

    public String getBarcode() {
        return _barcode;
    }

    public void setBarcode(String barcode) {
        this._barcode = barcode;
    }

    public String getBleAddress() {
        return _bleAddress;
    }

    public void setBleAddress(String bleAddress) {
        this._bleAddress = bleAddress;
    }

    public Integer getBleInRange() {
        return _bleInRange;
    }

    public void setBleInRange(Integer bleInRange) {
        this._bleInRange = bleInRange;
    }

    public String getOwner() {
        return _owner;
    }

    public void setOwner(String _owner) {
        this._owner = _owner;
    }

    public Date getPickedUpDate() {
        return _pickedUpDate;
    }

    public void setPickedUpDate(Date _pickedUpDate) {
        this._pickedUpDate = _pickedUpDate;
    }

    public String getCondition() {
        return _condition;
    }

    public void setCondition(String _condition) {
        this._condition = _condition;
    }

    public Location getLocation() {
        return _location;
    }

    public void setLocation(Location location) {
        this._location = location;
    }

//    public String getLocationName() {
//        return _locationName;
//    }
//
//    public void setLocationName(String locationName) {
//        this._locationName = locationName;
//    }

    public Long getSiteId() {
        return _siteId;
    }

    public void setSiteId(Long siteId) {
        this._siteId = siteId;
    }

    public String getDescription() {
        return _description;
    }

    public void setDescription(String arg) {
        _description = arg;
    }

    public InventoryCategoryEnum getCategory() {
        return _category;
    }

    public void setCategory(InventoryCategoryEnum category) {
        this._category = category;
    }
}
