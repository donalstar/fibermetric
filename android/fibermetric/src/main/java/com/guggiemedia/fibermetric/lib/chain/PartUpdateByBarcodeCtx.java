package com.guggiemedia.fibermetric.lib.chain;

import android.content.Context;

/**
 * Created by donal on 9/29/15.
 */
public class PartUpdateByBarcodeCtx extends AbstractCmdCtx {

    private String _barcode;
    private com.guggiemedia.fibermetric.lib.db.PartModel _partModel;
    private com.guggiemedia.fibermetric.lib.db.PersonModel _personModel;
    private String _newCustodian;
    private Long _pickupSiteId;

    public PartUpdateByBarcodeCtx(Context androidContext) {
        super(CommandEnum.PART_UPDATE_BY_BARCODE, androidContext);
    }

    public String getBarcode() {
        return _barcode;
    }

    public void setBarcode(String barcode) {
        this._barcode = barcode;
    }

    public com.guggiemedia.fibermetric.lib.db.PersonModel getPersonModel() {
        return _personModel;
    }

    public void setPersonModel(com.guggiemedia.fibermetric.lib.db.PersonModel personModel) {
        this._personModel = personModel;
    }

    public com.guggiemedia.fibermetric.lib.db.PartModel getPartModel() {
        return _partModel;
    }

    public void setPartModel(com.guggiemedia.fibermetric.lib.db.PartModel partModel) {
        this._partModel = partModel;
    }

    public String getNewCustodian() {
        return _newCustodian;
    }

    public void setNewCustodian(String newCustodian) {
        this._newCustodian = newCustodian;
    }

    public Long getPickupSiteId() {
        return _pickupSiteId;
    }

    public void setPickupSiteId(Long pickupSiteId) {
        this._pickupSiteId = pickupSiteId;
    }
}
