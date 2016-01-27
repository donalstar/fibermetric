package com.guggiemedia.fibermetric.lib.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.location.Location;
import android.net.Uri;

import java.util.Date;

/**
 *
 */
public class ChainOfCustodyModel implements DataBaseModel {
    public static final String PROVIDER = "Fibermetric";

    private Long _id;
    private Long _partId;
    private String _custodian;
    private Date _pickupDate;
    private Long _pickupSiteId;
    private Location _location;

    @Override
    public void setDefault() {
        _id = 0L;
        _partId = 0L;
        _custodian = "Unknown";
        _pickupDate = new Date();
        _pickupSiteId = 0L;
        _location = new Location(PROVIDER);
        _location.setLatitude(0.0);
        _location.setLongitude(0.0);
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues cv = new ContentValues();

        cv.put(ChainOfCustodyTable.Columns.PART_ID, _partId);
        cv.put(ChainOfCustodyTable.Columns.CUSTODIAN, _custodian);
        cv.put(ChainOfCustodyTable.Columns.PICKUP_DATE_TS, _pickupDate.getTime());
        cv.put(ChainOfCustodyTable.Columns.PICKUP_SITE_ID, _pickupSiteId);
        cv.put(ChainOfCustodyTable.Columns.PICKUP_LATITUDE, _location.getLatitude());
        cv.put(ChainOfCustodyTable.Columns.PICKUP_LONGITUDE, _location.getLongitude());

        return cv;
    }

    @Override
    public void fromCursor(Cursor cursor) {
        _partId = cursor.getLong(cursor.getColumnIndex(ChainOfCustodyTable.Columns.PART_ID));
        _custodian = cursor.getString(cursor.getColumnIndex(ChainOfCustodyTable.Columns.CUSTODIAN));
        _pickupDate.setTime(cursor.getLong(cursor.getColumnIndex(ChainOfCustodyTable.Columns.PICKUP_DATE_TS)));
        _pickupSiteId = cursor.getLong(cursor.getColumnIndex(ChainOfCustodyTable.Columns.PICKUP_SITE_ID));

        _location = new Location(PROVIDER);
        _location.setLatitude(cursor.getDouble(cursor.getColumnIndex(ChainOfCustodyTable.Columns.PICKUP_LATITUDE)));
        _location.setLongitude(cursor.getDouble(cursor.getColumnIndex(ChainOfCustodyTable.Columns.PICKUP_LONGITUDE)));
    }

    @Override
    public String getTableName() {
        return ChatMessageTable.TABLE_NAME;
    }

    @Override
    public Uri getTableUri() {
        return ChatMessageTable.CONTENT_URI;
    }

    public Long getId() {
        return _id;
    }

    public void setId(Long id) {
        _id = id;
    }

    public Long getPartId() {
        return _partId;
    }

    public void setPartId(Long partId) {
        this._partId = partId;
    }

    public String getCustodian() {
        return _custodian;
    }

    public void setCustodian(String custodian) {
        this._custodian = custodian;
    }

    public Date getPickupDate() {
        return _pickupDate;
    }

    public void setPickupDate(Date pickupDate) {
        this._pickupDate = pickupDate;
    }

    public Long getPickupSiteId() {
        return _pickupSiteId;
    }

    public void setPickupSiteId(Long pickupSiteId) {
        this._pickupSiteId = pickupSiteId;
    }

    public Location getLocation() {
        return _location;
    }

    public void setLocation(Location arg) {
        _location = arg;
    }
}
