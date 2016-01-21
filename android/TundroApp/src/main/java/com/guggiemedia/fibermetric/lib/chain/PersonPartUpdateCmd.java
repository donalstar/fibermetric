package com.guggiemedia.fibermetric.lib.chain;


import com.guggiemedia.fibermetric.lib.db.ContentFacade;
import com.guggiemedia.fibermetric.lib.db.PersonPartModel;


/**
 * Created by donal on 10/5/15.
 */
public class PersonPartUpdateCmd extends AbstractCmd {
    public static final String LOG_TAG = PersonPartUpdateCmd.class.getName();

    private final ContentFacade _contentFacade = new ContentFacade();

    /**
     * @param context
     * @return
     * @throws Exception
     */
    public Boolean execute(AbstractCmdCtx context) throws Exception {
        boolean result = false;

        final PersonPartUpdateCtx ctx = (PersonPartUpdateCtx) context;
        final PersonPartModel model = ctx.getModel();

        _contentFacade.updatePersonPart(model, ctx.getAndroidContext());

        return result;
    }
}
