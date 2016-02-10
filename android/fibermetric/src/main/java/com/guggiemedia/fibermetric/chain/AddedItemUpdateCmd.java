package com.guggiemedia.fibermetric.chain;

import com.guggiemedia.fibermetric.db.AddedItemModel;
import com.guggiemedia.fibermetric.db.ContentFacade;
import com.guggiemedia.fibermetric.db.ItemModel;

/**
 * Created by donal on 10/5/15.
 */
public class AddedItemUpdateCmd extends AbstractCmd {
    public static final String LOG_TAG = AddedItemUpdateCmd.class.getName();

    private final ContentFacade _contentFacade = new ContentFacade();

    /**
     * @param context
     * @return
     * @throws Exception
     */
    public Boolean execute(AbstractCmdCtx context) throws Exception {
        boolean result = false;

        final AddedItemUpdateCtx ctx = (AddedItemUpdateCtx) context;
        final AddedItemModel model = ctx.getModel();

        _contentFacade.updateAddedItem(model, ctx.getAndroidContext());

        return result;
    }
}
