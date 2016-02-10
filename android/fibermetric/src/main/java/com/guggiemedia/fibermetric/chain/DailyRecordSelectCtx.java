package com.guggiemedia.fibermetric.chain;

import android.content.Context;

import com.guggiemedia.fibermetric.db.DailyRecordModel;

import java.util.List;


/**
 * Created by donal
 */
public class DailyRecordSelectCtx extends AbstractCmdCtx {
    private List<DailyRecordModel> _dailyRecordModels;

    public DailyRecordSelectCtx(Context androidContext) {
        super(CommandEnum.HISTORY_SELECT, androidContext);
    }

    public List<DailyRecordModel> getHistoryModels() {
        return _dailyRecordModels;
    }

    public void setHistoryModels(List<DailyRecordModel> models) {
        _dailyRecordModels = models;
    }
}
