package com.guggiemedia.fibermetric.lib.chain;

/**
 * service heartbeat from AlarmManager
 */
public class HeartBeatCmd extends AbstractCmd {
    public static final String LOG_TAG = HeartBeatCmd.class.getName();

    /**
     *
     * @param context
     * @return
     * @throws Exception
     */
    public Boolean execute(AbstractCmdCtx context) throws Exception {
        final HeartBeatCtx ctx = (HeartBeatCtx) context;
        return returnToSender(ctx, ResultEnum.OK);
    }
}
