package com.guggiemedia.fibermetric.lib.chain;


/**
 * outbound messages
 */
public class MessageSendCmd extends AbstractCmd {
    public static final String LOG_TAG = MessageSendCmd.class.getName();

    public Boolean execute(AbstractCmdCtx context) throws Exception {
        final MessageSendCtx ctx = (MessageSendCtx) context;

        //OutBoundService.startActionOutBound(ctx.getAndroidContext(), ctx.getMessage());

        return returnToSender(ctx, ResultEnum.OK);
    }
}
