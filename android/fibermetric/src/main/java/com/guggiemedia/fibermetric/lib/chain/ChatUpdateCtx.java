package com.guggiemedia.fibermetric.lib.chain;

import android.content.Context;

import com.guggiemedia.fibermetric.lib.db.ChatModel;


/**
 * Created by donal on 9/29/15.
 */
public class ChatUpdateCtx extends AbstractCmdCtx {
    private ChatModel _model = new ChatModel();

    public ChatUpdateCtx(Context androidContext) {
        super(CommandEnum.CHAT_UPDATE, androidContext);
    }

    public ChatModel getModel() {
        return _model;
    }

    public void setModel(ChatModel arg) {
        _model = arg;
    }
}
