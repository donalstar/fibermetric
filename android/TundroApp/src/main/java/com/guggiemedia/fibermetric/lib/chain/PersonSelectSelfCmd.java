package com.guggiemedia.fibermetric.lib.chain;


import com.guggiemedia.fibermetric.lib.db.ContentFacade;

/**
 * Discover which Person owns the application
 * Always returns a live PersonModel, check the rowId to discover selection success
 */
public class PersonSelectSelfCmd extends AbstractCmd {
    public static final String LOG_TAG = PersonSelectSelfCmd.class.getName();

    private final ContentFacade _contentFacade = new ContentFacade();

    /**
     *
     * @param context
     * @return
     * @throws Exception
     */
    public Boolean execute(AbstractCmdCtx context) throws Exception {
        boolean result = false;
        final PersonSelectSelfCtx ctx = (PersonSelectSelfCtx) context;

        /*


        try {
            ctx.setSelected(_contentFacade.selectPersonSelf(ctx.getAndroidContext()));
            result = returnToSender(ctx, ResultEnum.OK);
        } catch(Exception exception) {
            result = returnToSender(ctx, ResultEnum.FAIL);
        }
        */

        result = returnToSender(ctx, ResultEnum.OK);

        return result;
    }
}