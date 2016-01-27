package com.guggiemedia.fibermetric.lib.chain;



import com.guggiemedia.fibermetric.lib.db.ContentFacade;
import com.guggiemedia.fibermetric.lib.db.PartModel;

/**
 * Created by donal on 10/5/15.
 */
public class PartReplaceBarcodeCmd extends AbstractCmd {
    public static final String LOG_TAG = PartReplaceBarcodeCmd.class.getName();

    private final ContentFacade _contentFacade = new ContentFacade();

    /**
     * @param context
     * @return
     * @throws Exception
     */
    public Boolean execute(AbstractCmdCtx context) throws Exception {
        boolean result = false;

        final PartReplaceBarcodeCtx ctx = (PartReplaceBarcodeCtx) context;

        long partId = ctx.getPartId();
        String barcode = ctx.getBarcode();

        PartModel partModel = _contentFacade.replaceBarcodeForPart(partId, barcode, ctx.getAndroidContext());

        ctx.setPartModel(partModel);

        return result;
    }
}
