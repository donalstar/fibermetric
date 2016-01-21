package com.guggiemedia.fibermetric.lib.chain;


import com.guggiemedia.fibermetric.lib.db.ContentFacade;

/**
 * insert/update a person
 */
public class PersonUpdateCmd extends AbstractCmd {
    public static final String LOG_TAG = PersonUpdateCmd.class.getName();

    private final ContentFacade _contentFacade = new ContentFacade();

    /**
     *
     * @param context
     * @return
     * @throws Exception
     */
    public Boolean execute(AbstractCmdCtx context) throws Exception {
        final PersonUpdateCtx ctx = (PersonUpdateCtx) context;

        _contentFacade.updatePerson(ctx.getModel(), ctx.getAndroidContext());

        ctx.writeEvent("fresh person:" + ctx.getModel().getId() + ":" + ctx.getModel().getNameUser());

        return returnToSender(ctx, ResultEnum.OK);
    }
}
