package com.guggiemedia.fibermetric.lib.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.location.Location;
import android.net.Uri;


/**
 *
 */
public class SiteModel implements DataBaseModel {
    public static final String PROVIDER = "Fibermetric";

    private Long _id;
    private String _name;
    private String _street1;
    private String _street2;
    private String _city;
    private String _state;
    private String _zip;
    private Location _location;

    @Override
    public void setDefault() {
        _id = 0L;
        _name = "Unknown";
        _street1 = "";
        _street2 = "";
        _location = new Location(PROVIDER);
        _location.setLatitude(0.0);
        _location.setLongitude(0.0);
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(SiteTable.Columns.NAME, _name);
        cv.put(SiteTable.Columns.STREET1, _street1);
        cv.put(SiteTable.Columns.STREET2, _street2);
        cv.put(SiteTable.Columns.CITY, _city);
        cv.put(SiteTable.Columns.STATE, _state);
        cv.put(SiteTable.Columns.ZIP, _zip);
        cv.put(SiteTable.Columns.LATITUDE, _location.getLatitude());
        cv.put(SiteTable.Columns.LONGITUDE, _location.getLongitude());
        return cv;
    }

    @Override
    public void fromCursor(Cursor cursor) {
        _id = cursor.getLong(cursor.getColumnIndex(SiteTable.Columns._ID));
        _name = cursor.getString(cursor.getColumnIndex(SiteTable.Columns.NAME));
        _street1 = cursor.getString(cursor.getColumnIndex(SiteTable.Columns.STREET1));
        _street2 = cursor.getString(cursor.getColumnIndex(SiteTable.Columns.STREET2));
        _city = cursor.getString(cursor.getColumnIndex(SiteTable.Columns.CITY));
        _state = cursor.getString(cursor.getColumnIndex(SiteTable.Columns.STATE));
        _zip = cursor.getString(cursor.getColumnIndex(SiteTable.Columns.ZIP));

        _location = new Location(PROVIDER);
        _location.setLatitude(cursor.getDouble(cursor.getColumnIndex(SiteTable.Columns.LATITUDE)));
        _location.setLongitude(cursor.getDouble(cursor.getColumnIndex(SiteTable.Columns.LONGITUDE)));
    }

    @Override
    public String getTableName() {
        return SiteTable.TABLE_NAME;
    }

    @Override
    public Uri getTableUri() {
        return SiteTable.CONTENT_URI;
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

    public String getStreet1() {
        return _street1;
    }

    public void setStreet1(String arg) {
        _street1 = arg;
    }

    public String getStreet2() {
        return _street2;
    }

    public void setStreet2(String arg) {
        _street2 = arg;
    }

    public void setCity(String city) {
        this._city = city;
    }

    public void setState(String state) {
        this._state = state;
    }

    public void setZip(String zip) {
        this._zip = zip;
    }

    public String getCity() {
        return _city;
    }

    public String getState() {
        return _state;
    }

    public String getZip() {
        return _zip;
    }

    public Location getLocation() {
        return _location;
    }

    public void setLocation(Location arg) {
        _location = arg;
    }
}