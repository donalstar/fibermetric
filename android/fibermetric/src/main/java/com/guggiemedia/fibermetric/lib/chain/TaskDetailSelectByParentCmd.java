package com.guggiemedia.fibermetric.lib.chain;

import com.guggiemedia.fibermetric.lib.db.DataBaseFacade;

/**
 * Select a task detail by parent ID
 */
public class TaskDetailSelectByParentCmd extends AbstractCmd {
    public static final String LOG_TAG = TaskDetailSelectByParentCmd.class.getName();

    /**
     *
     * @param context
     * @return
     * @throws Exception
     */
    public Boolean execute(AbstractCmdCtx context) throws Exception {
        final TaskDetailSelectByParentCtx ctx = (TaskDetailSelectByParentCtx) context;

        boolean result;

        try {
            DataBaseFacade dbf = new DataBaseFacade(ctx.getAndroidContext());
            ctx.setSelected(dbf.selectTaskDetailByParent(ctx.getParentId()));
            result = returnToSender(ctx, ResultEnum.OK);
        } catch(Exception exception) {
            result = returnToSender(ctx, ResultEnum.FAIL);
        }

        return result;
    }
}