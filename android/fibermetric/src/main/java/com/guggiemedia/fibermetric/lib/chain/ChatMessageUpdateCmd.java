package com.guggiemedia.fibermetric.lib.chain;



import com.guggiemedia.fibermetric.lib.db.ChatMessageModel;
import com.guggiemedia.fibermetric.lib.db.ContentFacade;


/**
 * Created by donal on 9/29/15.
 */
public class ChatMessageUpdateCmd extends AbstractCmd {
    public static final String LOG_TAG = ChatMessageUpdateCmd.class.getName();

    private final ContentFacade _contentFacade = new ContentFacade();

    /**
     *
     * @param context
     * @return
     * @throws Exception
     */
    public Boolean execute(AbstractCmdCtx context) throws Exception {
        boolean result = false;

        final ChatMessageUpdateCtx ctx = (ChatMessageUpdateCtx) context;
        final ChatMessageModel model = ctx.getModel();

        _contentFacade.updateChatMessage(model, ctx.getAndroidContext());

        return result;
    }
}
