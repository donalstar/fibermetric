package com.guggiemedia.fibermetric.lib.chain;

import android.content.Context;

import com.guggiemedia.fibermetric.lib.db.ThingModel;


/**
 * assert custody command context
 */
public class CustodyAssertCtx extends AbstractCmdCtx {
    private long _rowId;
    private ThingModel _thingModel;

    public CustodyAssertCtx(Context androidContext) {
        super(CommandEnum.CUSTODY_ASSERT, androidContext);
    }

    public Long getRowId() {
        return _rowId;
    }

    public void setRowId(long arg) {
        _rowId = arg;
    }

    public ThingModel getModel() {
        return _thingModel;
    }

    public void setModel(ThingModel arg) {
        _thingModel = arg;
    }
}
