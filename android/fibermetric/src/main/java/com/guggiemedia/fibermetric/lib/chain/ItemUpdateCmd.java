package com.guggiemedia.fibermetric.lib.chain;

import com.guggiemedia.fibermetric.lib.db.ItemModel;

/**
 * Created by donal on 10/5/15.
 */
public class ItemUpdateCmd extends AbstractCmd {
    public static final String LOG_TAG = ItemUpdateCmd.class.getName();

    private final com.guggiemedia.fibermetric.lib.db.ContentFacade _contentFacade = new com.guggiemedia.fibermetric.lib.db.ContentFacade();

    /**
     *
     * @param context
     * @return
     * @throws Exception
     */
    public Boolean execute(com.guggiemedia.fibermetric.lib.chain.AbstractCmdCtx context) throws Exception {
        boolean result = false;

        final ItemUpdateCtx ctx = (ItemUpdateCtx) context;
        final ItemModel model = ctx.getModel();

        _contentFacade.updateItem(model, ctx.getAndroidContext());

        return result;
    }
}
