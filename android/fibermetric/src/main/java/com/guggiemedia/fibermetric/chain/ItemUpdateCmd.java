package com.guggiemedia.fibermetric.chain;

import com.guggiemedia.fibermetric.db.ContentFacade;
import com.guggiemedia.fibermetric.db.ItemModel;

/**
 * Created by donal on 10/5/15.
 */
public class ItemUpdateCmd extends AbstractCmd {
    public static final String LOG_TAG = ItemUpdateCmd.class.getName();

    private final ContentFacade _contentFacade = new ContentFacade();

    /**
     *
     * @param context
     * @return
     * @throws Exception
     */
    public Boolean execute(com.guggiemedia.fibermetric.chain.AbstractCmdCtx context) throws Exception {
        boolean result = false;

        final ItemUpdateCtx ctx = (ItemUpdateCtx) context;
        final ItemModel model = ctx.getModel();

        _contentFacade.updateItem(model, ctx.getAndroidContext());

        return result;
    }
}
