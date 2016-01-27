package com.guggiemedia.fibermetric.lib.chain;

import android.content.Context;

import com.guggiemedia.fibermetric.lib.db.ChatMessageModel;


/**
 * Created by donal on 9/29/15.
 */
public class ChatMessageUpdateCtx extends AbstractCmdCtx {
    private ChatMessageModel _model = new ChatMessageModel();

    public ChatMessageUpdateCtx(Context androidContext) {
        super(CommandEnum.CHAT_MSG_UPDATE, androidContext);
    }

    public ChatMessageModel getModel() {
        return _model;
    }

    public void setModel(ChatMessageModel arg) {
        _model = arg;
    }
}
