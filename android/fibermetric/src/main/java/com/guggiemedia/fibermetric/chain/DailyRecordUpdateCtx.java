package com.guggiemedia.fibermetric.chain;

import android.content.Context;

import com.guggiemedia.fibermetric.db.DailyRecordModel;


/**
 * Created by donal
 */
public class DailyRecordUpdateCtx extends AbstractCmdCtx {
    private DailyRecordModel _model = new DailyRecordModel();


    public DailyRecordUpdateCtx(Context androidContext) {
        super(CommandEnum.HISTORY_UPDATE, androidContext);
    }

    public DailyRecordModel getModel() {
        return _model;
    }

    public void setModel(DailyRecordModel arg) {
        _model = arg;
    }
}
