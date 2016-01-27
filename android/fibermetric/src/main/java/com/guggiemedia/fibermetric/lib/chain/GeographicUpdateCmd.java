package com.guggiemedia.fibermetric.lib.chain;


import com.guggiemedia.fibermetric.lib.Personality;

/**
 * fresh geographic update
 */
public class GeographicUpdateCmd extends AbstractCmd {
    public static final String LOG_TAG = GeographicUpdateCmd.class.getName();

    /**
     *
     * @param context
     * @return
     * @throws Exception
     */
    public Boolean execute(AbstractCmdCtx context) throws Exception {
        final GeographicUpdateCtx ctx = (GeographicUpdateCtx) context;

        Personality.currentLocation = ctx.getLocation();

        return returnToSender(ctx, ResultEnum.OK);
    }
}
