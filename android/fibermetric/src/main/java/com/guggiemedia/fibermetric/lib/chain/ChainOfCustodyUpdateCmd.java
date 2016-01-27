package com.guggiemedia.fibermetric.lib.chain;



import com.guggiemedia.fibermetric.lib.db.ChainOfCustodyModel;
import com.guggiemedia.fibermetric.lib.db.ContentFacade;

/**
 * Created by donal on 10/5/15.
 */
public class ChainOfCustodyUpdateCmd extends AbstractCmd {
    public static final String LOG_TAG = ChainOfCustodyUpdateCmd.class.getName();

    private final ContentFacade _contentFacade = new ContentFacade();

    /**
     *
     * @param context
     * @return
     * @throws Exception
     */
    public Boolean execute(AbstractCmdCtx context) throws Exception {
        boolean result = false;

        final ChainOfCustodyUpdateCtx ctx = (ChainOfCustodyUpdateCtx) context;
        final ChainOfCustodyModel model = ctx.getModel();

        _contentFacade.updateChainOfCustody(model, ctx.getAndroidContext());

        return result;
    }
}
