package com.guggiemedia.fibermetric.chain;

import android.content.Context;

import com.guggiemedia.fibermetric.db.HistoryModel;

import java.util.List;


/**
 * Created by donal
 */
public class HistorySelectCtx extends AbstractCmdCtx {
    private List<HistoryModel> _historyModels;

    public HistorySelectCtx(Context androidContext) {
        super(CommandEnum.HISTORY_SELECT, androidContext);
    }

    public List<HistoryModel> getHistoryModels() {
        return _historyModels;
    }

    public void setHistoryModels(List<HistoryModel> models) {
        _historyModels = models;
    }
}
