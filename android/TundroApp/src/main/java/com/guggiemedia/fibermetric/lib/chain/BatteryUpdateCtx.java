package com.guggiemedia.fibermetric.lib.chain;

import android.content.Context;

/**
 * fresh battery update
 */
public class BatteryUpdateCtx extends AbstractCmdCtx {
    private int _chargeLevel;

    public BatteryUpdateCtx(Context androidContext) {
        super(CommandEnum.BATTERY_UPDATE, androidContext);
    }

    public int getChargeLevel() {
        return _chargeLevel;
    }

    public void setChargeLevel(int chargeLevel) {
        _chargeLevel = chargeLevel;
    }
}