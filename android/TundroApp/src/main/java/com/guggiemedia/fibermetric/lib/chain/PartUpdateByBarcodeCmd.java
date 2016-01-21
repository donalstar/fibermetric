package com.guggiemedia.fibermetric.lib.chain;


import com.guggiemedia.fibermetric.lib.db.ContentFacade;
import com.guggiemedia.fibermetric.lib.db.PartModel;
import com.guggiemedia.fibermetric.lib.db.PersonPartModel;

/**
 * Created by donal on 10/5/15.
 */
public class PartUpdateByBarcodeCmd extends AbstractCmd {
    public static final String LOG_TAG = PartUpdateByBarcodeCmd.class.getName();

    private final ContentFacade _contentFacade = new ContentFacade();

    /**
     * @param context
     * @return
     * @throws Exception
     */
    public Boolean execute(AbstractCmdCtx context) throws Exception {
        boolean result = false;

        final PartUpdateByBarcodeCtx ctx = (PartUpdateByBarcodeCtx) context;

        String barcode = ctx.getBarcode();

        PartModel partModel = _contentFacade.updatePartStatusByBarcode(barcode, ctx.getAndroidContext());

        PersonPartModel personPartModel = new PersonPartModel();
        personPartModel.setDefault();

        personPartModel.setPartId(partModel.getId());
        personPartModel.setPersonId(ctx.getPersonModel().getId());

        _contentFacade.updatePersonPart(personPartModel, ctx.getAndroidContext());

        ctx.setPartModel(partModel);

        return result;
    }
}
