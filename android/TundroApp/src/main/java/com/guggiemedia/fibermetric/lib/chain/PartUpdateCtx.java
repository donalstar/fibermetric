package com.guggiemedia.fibermetric.lib.chain;

import android.content.Context;

import com.guggiemedia.fibermetric.lib.db.PartModel;


/**
 * Created by donal on 9/29/15.
 */
public class PartUpdateCtx extends AbstractCmdCtx {
    private PartModel _model = new PartModel();
    private String _newCustodian;
    private String _siteId;

    public PartUpdateCtx(Context androidContext) {
        super(CommandEnum.PART_UPDATE, androidContext);
    }

    public PartModel getModel() {
        return _model;
    }

    public void setModel(PartModel arg) {
        _model = arg;
    }

    public String getNewCustodian() {
        return _newCustodian;
    }

    public void setNewCustodian(String newCustodian) {
        this._newCustodian = newCustodian;
    }

    public String getSiteId() {
        return _siteId;
    }

    public void setSiteId(String siteId) {
        this._siteId = siteId;
    }
}
