package com.guggiemedia.fibermetric.chain;

import android.content.Context;

import com.guggiemedia.fibermetric.db.HistoryModel;
import com.guggiemedia.fibermetric.db.ItemModel;


/**
 * Created by donal
 */
public class HistoryUpdateCtx extends AbstractCmdCtx {
    private HistoryModel _model = new HistoryModel();


    public HistoryUpdateCtx(Context androidContext) {
        super(CommandEnum.HISTORY_UPDATE, androidContext);
    }

    public HistoryModel getModel() {
        return _model;
    }

    public void setModel(HistoryModel arg) {
        _model = arg;
    }
}
