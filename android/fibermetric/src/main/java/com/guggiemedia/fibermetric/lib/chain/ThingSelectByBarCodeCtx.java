package com.guggiemedia.fibermetric.lib.chain;

import android.content.Context;

import com.guggiemedia.fibermetric.lib.db.ThingModelList;


/**
 * select thing command context
 */
public class ThingSelectByBarCodeCtx extends AbstractCmdCtx {
    private String _barCode;
    private ThingModelList _selected = new ThingModelList();

    public ThingSelectByBarCodeCtx(Context androidContext) {
        super(CommandEnum.THING_SELECT_BY_BARCODE, androidContext);
    }

    public String getBarCode() {
        return _barCode;
    }

    public void setBarCode(String arg) {
        _barCode = arg;
    }

    public ThingModelList getSelected() {
        return _selected;
    }

    public void setSelected(ThingModelList arg) {
        _selected = arg;
    }
}
