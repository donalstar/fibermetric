package com.guggiemedia.fibermetric.lib.chain;


import com.guggiemedia.fibermetric.lib.db.DataBaseFacade;

/**
 * Select by row ID
 * Always returns a live model, check the rowId to discover selection success
 */
public class SelectByRowIdCmd extends AbstractCmd {
    public static final String LOG_TAG = SelectByRowIdCmd.class.getName();

    /**
     *
     * @param context
     * @return
     * @throws Exception
     */
    public Boolean execute(AbstractCmdCtx context) throws Exception {
        final SelectByRowIdCtx ctx = (SelectByRowIdCtx) context;

        final DataBaseFacade dbf = new DataBaseFacade(ctx.getAndroidContext());

        ctx.setSelected(dbf.selectModel(ctx.getRowId(), ctx.getTable()));

        return returnToSender(ctx, ResultEnum.OK);
    }
}