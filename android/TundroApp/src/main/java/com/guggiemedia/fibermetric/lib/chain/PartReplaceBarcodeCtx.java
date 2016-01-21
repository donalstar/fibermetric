package com.guggiemedia.fibermetric.lib.chain;

import android.content.Context;

import com.guggiemedia.fibermetric.lib.db.PartModel;


/**
 * Created by donal on 9/29/15.
 */
public class PartReplaceBarcodeCtx extends AbstractCmdCtx {

    private long _partId;
    private String _barcode;
    private PartModel _partModel;

    public PartReplaceBarcodeCtx(Context androidContext) {
        super(CommandEnum.PART_REPLACE_BARCODE, androidContext);
    }

    public long getPartId() {
        return _partId;
    }

    public void setPartId(long partId) {
        this._partId = partId;
    }

    public String getBarcode() {
        return _barcode;
    }

    public void setBarcode(String barcode) {
        this._barcode = barcode;
    }

    public PartModel getPartModel() {
        return _partModel;
    }

    public void setPartModel(PartModel partModel) {
        this._partModel = partModel;
    }
}
