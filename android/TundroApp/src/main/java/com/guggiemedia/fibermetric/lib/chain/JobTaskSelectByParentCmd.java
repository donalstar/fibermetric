package com.guggiemedia.fibermetric.lib.chain;


import com.guggiemedia.fibermetric.lib.db.DataBaseFacade;

/**
 * Select a jobtask by parent ID
 */
public class JobTaskSelectByParentCmd extends AbstractCmd {
    public static final String LOG_TAG = JobTaskSelectByParentCmd.class.getName();

    /**
     *
     * @param context
     * @return
     * @throws Exception
     */
    public Boolean execute(AbstractCmdCtx context) throws Exception {
        final JobTaskSelectByParentCtx ctx = (JobTaskSelectByParentCtx) context;

        boolean result;

        try {
            DataBaseFacade dbf = new DataBaseFacade(ctx.getAndroidContext());
            ctx.setSelected(dbf.selectJobTaskByParent(ctx.getParentId()));
            result = returnToSender(ctx, ResultEnum.OK);
        } catch(Exception exception) {
            result = returnToSender(ctx, ResultEnum.FAIL);
        }

        return result;
    }
}