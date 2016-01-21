package com.guggiemedia.fibermetric.lib.chain;

import android.content.Context;

import com.guggiemedia.fibermetric.lib.db.ThingModel;


/**
 * insert/update thing command context
 */
public class ThingUpdateCtx extends AbstractCmdCtx {
    private ThingModel _model = new ThingModel();
    private ThingWorkFlowEnum _workFlowStatus = ThingWorkFlowEnum.UNKNOWN;
    private boolean _deleteBarCodeFlag = false;
    private boolean _updateBarCodeFlag = false;
    private boolean _deleteBleBeaconFlag = false;
    private boolean _updateBleBeaconFlag = false;

    public ThingUpdateCtx(Context androidContext) {
        super(CommandEnum.THING_UPDATE, androidContext);
    }

    public ThingWorkFlowEnum getWorkFlowStatus() {
        return _workFlowStatus;
    }

    public void setWorkFlowStatus(ThingWorkFlowEnum arg) {
        _workFlowStatus = arg;
    }

    public ThingModel getModel() {
        return _model;
    }

    public void setModel(ThingModel arg) {
        _model = arg;
    }

    public Boolean isDeleteBarCode() {
        return _deleteBarCodeFlag;
    }

    public void setDeleteBarCode(boolean arg) {
        _deleteBarCodeFlag = arg;
    }

    public Boolean isUpdateBarCode() {
        return _updateBarCodeFlag;
    }

    public void setUpdateBarCode(boolean arg) {
        _updateBarCodeFlag = arg;
    }

    public Boolean isDeleteBleBeacon() {
        return _deleteBleBeaconFlag;
    }

    public void setDeleteBleBeacon(boolean arg) {
        _deleteBleBeaconFlag = arg;
    }

    public Boolean isUpdateBleBeacon() {
        return _updateBleBeaconFlag;
    }

    public void setUpdateBleBeacon(boolean arg) {
        _updateBleBeaconFlag = arg;
    }
}
