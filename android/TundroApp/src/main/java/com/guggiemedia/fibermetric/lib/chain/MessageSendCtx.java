package com.guggiemedia.fibermetric.lib.chain;

import android.content.Context;



/**
 * outbound messages
 */
public class MessageSendCtx extends AbstractCmdCtx {
//    private AbstractMessage _message;

    public MessageSendCtx(Context androidContext) {
        super(CommandEnum.MESSAGE_SEND, androidContext);
    }

    /*
    public AbstractMessage getMessage() {
        return _message;
    }

    public void setMessage(AbstractMessage arg) {
        _message = arg;
    }
    */
}
