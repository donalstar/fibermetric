package com.guggiemedia.fibermetric.lib.chain;

/**
 * Select a task action by parent ID
 */
public class TaskActionSelectByParentCmd extends com.guggiemedia.fibermetric.lib.chain.AbstractCmd {
    public static final String LOG_TAG = TaskActionSelectByParentCmd.class.getName();

    /**
     *
     * @param context
     * @return
     * @throws Exception
     */
    public Boolean execute(com.guggiemedia.fibermetric.lib.chain.AbstractCmdCtx context) throws Exception {
        final com.guggiemedia.fibermetric.lib.chain.TaskActionSelectByParentCtx ctx = (com.guggiemedia.fibermetric.lib.chain.TaskActionSelectByParentCtx) context;

        boolean result;

        try {
            com.guggiemedia.fibermetric.lib.db.DataBaseFacade dbf = new com.guggiemedia.fibermetric.lib.db.DataBaseFacade(ctx.getAndroidContext());
            ctx.setSelected(dbf.selectTaskActionByParent(ctx.getParentId()));
            result = returnToSender(ctx, com.guggiemedia.fibermetric.lib.chain.ResultEnum.OK);
        } catch(Exception exception) {
            result = returnToSender(ctx, com.guggiemedia.fibermetric.lib.chain.ResultEnum.FAIL);
        }

        return result;
    }
}