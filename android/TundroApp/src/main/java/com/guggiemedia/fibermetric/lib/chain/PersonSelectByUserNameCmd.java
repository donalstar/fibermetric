package com.guggiemedia.fibermetric.lib.chain;


import com.guggiemedia.fibermetric.lib.db.DataBaseFacade;

/**
 * Select a person by user name
 * Always returns a live PersonModel, check the rowId to discover selection success
 */
public class PersonSelectByUserNameCmd extends AbstractCmd {
    public static final String LOG_TAG = PersonSelectByUserNameCmd.class.getName();

    public Boolean execute(AbstractCmdCtx context) throws Exception {
        final PersonSelectByUserNameCtx ctx = (PersonSelectByUserNameCtx) context;

        DataBaseFacade dbf = new DataBaseFacade(ctx.getAndroidContext());
        ctx.setSelected(dbf.selectUser(ctx.getUserName()));
        return returnToSender(ctx, ResultEnum.OK);
    }
}