package com.guggiemedia.fibermetric.lib.chain;

import android.util.Log;

import com.guggiemedia.fibermetric.lib.Personality;


/**
 * fresh battery update
 */
public class BatteryUpdateCmd extends AbstractCmd {
    public static final String LOG_TAG = BatteryUpdateCmd.class.getName();

    /**
     *
     * @param context
     * @return
     * @throws Exception
     */
    public Boolean execute(AbstractCmdCtx context) throws Exception {
        final BatteryUpdateCtx ctx = (BatteryUpdateCtx) context;

        Log.d(LOG_TAG, "battery power update:" + ctx.getChargeLevel());

        Personality.batteryPower = ctx.getChargeLevel();

        return returnToSender(ctx, ResultEnum.OK);
    }
}
