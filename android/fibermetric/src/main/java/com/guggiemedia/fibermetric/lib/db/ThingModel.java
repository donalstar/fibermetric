package com.guggiemedia.fibermetric.lib.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.guggiemedia.fibermetric.lib.Constant;

import java.util.Date;
import java.util.UUID;

/**
 *
 */
public class ThingModel implements DataBaseModel {
    private Long _id;
    private Boolean _alertFlag;
    private String _barCode;
    private Boolean _barCodeFlag;
    private String _bleAddress;
    private Boolean _bleAddressFlag;
    private Date _bleLastTime;
    private Boolean _custodyFlag;
    private String _description;
    private UUID _gfUuid;
    private ThingTypeEnum _itemType;
    private String _name;
    private Long _parentKey;
    private String _serialNumber;
    private Long _siteKey;
    private String _photoName;
    private String _note;

    @Override
    public void setDefault() {
        _id = 0L;
        _alertFlag = false;
        _barCode = "Unknown";
        _barCodeFlag = false;
        _bleAddress = "Unknown";
        _bleAddressFlag = false;
        _bleLastTime = new Date();
        _custodyFlag = false;
        _description = "Unknown";
        _gfUuid = UUID.randomUUID();
        _itemType = ThingTypeEnum.UNKNOWN;
        _name = "Unknown";
        _parentKey = 0L;
        _serialNumber = "Unknown";
        _siteKey = 0L;
        _photoName = "No Photo";
        _note = "No Note";
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(ThingTable.Columns.PARENT_KEY, _parentKey);
        cv.put(ThingTable.Columns.SITE_KEY, _siteKey);
        cv.put(ThingTable.Columns.ALERT_FLAG, (_alertFlag) ? Constant.SQL_TRUE:Constant.SQL_FALSE);
        cv.put(ThingTable.Columns.BARCODE, _barCode);
        cv.put(ThingTable.Columns.BARCODE_FLAG, (_barCodeFlag) ? Constant.SQL_TRUE:Constant.SQL_FALSE);
        cv.put(ThingTable.Columns.BLE_ADDRESS, _bleAddress);
        cv.put(ThingTable.Columns.BLE_FLAG, (_bleAddressFlag) ? Constant.SQL_TRUE:Constant.SQL_FALSE);
        cv.put(ThingTable.Columns.BLE_LAST_TIME_MS, _bleLastTime.getTime());
        cv.put(ThingTable.Columns.CUSTODY_FLAG, (_custodyFlag) ? Constant.SQL_TRUE: Constant.SQL_FALSE);
        cv.put(ThingTable.Columns.DESCRIPTION, _description);
        cv.put(ThingTable.Columns.ITEM_TYPE, _itemType.toString());
        cv.put(ThingTable.Columns.NAME, _name);
        cv.put(ThingTable.Columns.SERIAL_NUMBER, _serialNumber);
        cv.put(ThingTable.Columns.GF_UUID, _gfUuid.toString());
        cv.put(ThingTable.Columns.PHOTO_NAME, _photoName);
        cv.put(ThingTable.Columns.NOTE, _note);
        return cv;
    }

    @Override
    public void fromCursor(Cursor cursor) {
        _id = cursor.getLong(cursor.getColumnIndex(ThingTable.Columns._ID));
        _parentKey = cursor.getLong(cursor.getColumnIndex(ThingTable.Columns.PARENT_KEY));
        _siteKey = cursor.getLong(cursor.getColumnIndex(ThingTable.Columns.SITE_KEY));
        _alertFlag = (cursor.getInt(cursor.getColumnIndex(ThingTable.Columns.ALERT_FLAG)) != 0);
        _barCode = cursor.getString(cursor.getColumnIndex(ThingTable.Columns.BARCODE));
        _barCodeFlag = (cursor.getInt(cursor.getColumnIndex(ThingTable.Columns.BARCODE_FLAG)) != 0);
        _bleAddress = cursor.getString(cursor.getColumnIndex(ThingTable.Columns.BLE_ADDRESS));
        _bleAddressFlag = (cursor.getInt(cursor.getColumnIndex(ThingTable.Columns.BLE_FLAG)) != 0);
        _bleLastTime.setTime(cursor.getLong(cursor.getColumnIndex(ThingTable.Columns.BLE_LAST_TIME_MS)));
        _custodyFlag = (cursor.getInt(cursor.getColumnIndex(ThingTable.Columns.CUSTODY_FLAG)) != 0);
        _description = cursor.getString(cursor.getColumnIndex(ThingTable.Columns.DESCRIPTION));
        _itemType = ThingTypeEnum.discoverMatchingEnum(cursor.getString(cursor.getColumnIndex(ThingTable.Columns.ITEM_TYPE)));
        _name = cursor.getString(cursor.getColumnIndex(ThingTable.Columns.NAME));
        _serialNumber = cursor.getString(cursor.getColumnIndex(ThingTable.Columns.SERIAL_NUMBER));
        _gfUuid = UUID.fromString(cursor.getString(cursor.getColumnIndex(ThingTable.Columns.GF_UUID)));
        _photoName = cursor.getString(cursor.getColumnIndex(ThingTable.Columns.PHOTO_NAME));
        _note = cursor.getString(cursor.getColumnIndex(ThingTable.Columns.NOTE));
    }

    @Override
    public String getTableName() {
        return ThingTable.TABLE_NAME;
    }

    @Override
    public Uri getTableUri() {
        return ThingTable.CONTENT_URI;
    }

    public Long getId() {
        return _id;
    }
    public void setId(Long id) {
        _id = id;
    }

    public Long getParentId() {
        return _parentKey;
    }
    public void setParentId(Long arg) {
        _parentKey = arg;
    }

    public Long getSiteId() {
        return _siteKey;
    }
    public void setSiteId(Long arg) {
        _siteKey = arg;
    }

    public Boolean isAlert() {
        return _alertFlag;
    }
    public void setAlertFlag(boolean arg) {
        _alertFlag = arg;
    }

    public String getBarCode() {
        return _barCode;
    }
    public void setBarCode(String arg) {
        _barCode = arg;
    }

    public Boolean isBarCode() {
        return _barCodeFlag;
    }
    public void setBarCodeFlag(boolean arg) {
        _barCodeFlag = arg;
    }

    public String getBleAddress() {
        return _bleAddress;
    }
    public void setBleAddress(String arg) {
        _bleAddress = arg;
    }

    public Boolean isBle() {
        return _bleAddressFlag;
    }
    public void setBleFlag(boolean arg) {
        _bleAddressFlag = arg;
    }

    public Date getBleLastTime() {
        return _bleLastTime;
    }
    public void setBleLastTime(Date arg) {
        _bleLastTime = arg;
    }

    public Boolean isCustody() {
        return _custodyFlag;
    }
    public void setCustodyFlag(boolean arg) {
        _custodyFlag = arg;
    }

    public String getDescription() {
        return _description;
    }
    public void setDescription(String arg) {
        _description = arg;
    }

    public UUID getGfUuid() {
        return _gfUuid;
    }
    public void setGfUuid(UUID arg) {
        _gfUuid = arg;
    }

    public ThingTypeEnum getItemType() {
        return _itemType;
    }
    public void setItemType(ThingTypeEnum arg) {
        _itemType = arg;
    }

    public String getName() {
        return _name;
    }
    public void setName(String arg) {
        _name = arg;
    }

    public String getSerialNumber() {
        return _serialNumber;
    }
    public void setSerialNumber(String arg) {
        _serialNumber = arg;
    }

    public String getPhotoName() {
        return _photoName;
    }
    public void setPhotoName(String arg) {
        _photoName = arg;
    }

    public String getNote() {
        return _note;
    }
    public void setNote(String arg) {
        _note = arg;
    }

    public Bitmap getPhoto() {
        if (_photoName.equals("No Photo")) {
            return null;
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;

        return BitmapFactory.decodeFile(_photoName, options);
    }
}
