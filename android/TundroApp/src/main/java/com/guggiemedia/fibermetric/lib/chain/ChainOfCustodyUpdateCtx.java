package com.guggiemedia.fibermetric.lib.chain;

import android.content.Context;

import com.guggiemedia.fibermetric.lib.db.ChainOfCustodyModel;


/**
 * Created by donal on 9/29/15.
 */
public class ChainOfCustodyUpdateCtx extends AbstractCmdCtx {
    private ChainOfCustodyModel _model = new ChainOfCustodyModel();

    public ChainOfCustodyUpdateCtx(Context androidContext) {
        super(CommandEnum.CHAIN_OF_CUSTODY_UPDATE, androidContext);
    }

    public ChainOfCustodyModel getModel() {
        return _model;
    }

    public void setModel(ChainOfCustodyModel arg) {
        _model = arg;
    }
}
