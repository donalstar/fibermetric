package com.guggiemedia.fibermetric.lib.chain;


import com.guggiemedia.fibermetric.lib.db.ContentFacade;

/**
 * Select all persons
 */
public class PersonSelectAllCmd extends AbstractCmd {
    public static final String LOG_TAG = PersonSelectAllCmd.class.getName();

    private final ContentFacade _contentFacade = new ContentFacade();

    /**
     *
     * @param context
     * @return
     * @throws Exception
     */
    public Boolean execute(AbstractCmdCtx context) throws Exception {
        boolean result = false;

        final PersonSelectAllCtx ctx = (PersonSelectAllCtx) context;

        try {
            ctx.setSelected(_contentFacade.selectPersonAll(ctx.getAndroidContext()));
            result = returnToSender(ctx, ResultEnum.OK);
        } catch(Exception exception) {
            result = returnToSender(ctx, ResultEnum.FAIL);
        }

        return result;
    }
}