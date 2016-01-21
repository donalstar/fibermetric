package com.guggiemedia.fibermetric.lib.chain;

import android.content.Context;

import com.guggiemedia.fibermetric.lib.db.ThingModelList;


/**
 * select thing command context
 */
public class ThingSelectByBleAddressCtx extends AbstractCmdCtx {
    private String _bleAddress;
    private ThingModelList _selected = new ThingModelList();

    public ThingSelectByBleAddressCtx(Context androidContext) {
        super(CommandEnum.THING_SELECT_BY_BLE_ADDRESS, androidContext);
    }

    public String getBleAddress() {
        return _bleAddress;
    }

    public void setBleAddress(String arg) {
        _bleAddress = arg;
    }

    public ThingModelList getSelected() {
        return _selected;
    }

    public void setSelected(ThingModelList arg) {
        _selected = arg;
    }
}