package com.guggiemedia.fibermetric.lib.chain;



import com.guggiemedia.fibermetric.lib.db.ChatModel;
import com.guggiemedia.fibermetric.lib.db.ContentFacade;




/**
 * Created by donal on 9/29/15.
 */
public class ChatUpdateCmd extends AbstractCmd {
    public static final String LOG_TAG = ChatUpdateCmd.class.getName();

    private final ContentFacade _contentFacade = new ContentFacade();

    /**
     *
     * @param context
     * @return
     * @throws Exception
     */
    public Boolean execute(AbstractCmdCtx context) throws Exception {
        boolean result = false;

        final ChatUpdateCtx ctx = (ChatUpdateCtx) context;
        final ChatModel model = ctx.getModel();

        _contentFacade.updateChat(model, ctx.getAndroidContext());

        return result;
    }
}
