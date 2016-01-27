package com.guggiemedia.fibermetric.lib.chain;

import android.content.Context;

import com.guggiemedia.fibermetric.lib.db.AlertEnum;
import com.guggiemedia.fibermetric.lib.db.ContentFacade;
import com.guggiemedia.fibermetric.lib.db.EventModel;
import com.guggiemedia.fibermetric.lib.db.ThingModel;

/**
 * insert/update a thing
 */
public class ThingUpdateCmd extends AbstractCmd {
    public static final String LOG_TAG = ThingUpdateCmd.class.getName();


    private final ContentFacade _contentFacade = new ContentFacade();

    /**
     * ensure model contains plausible values
     * @param model
     * @return
     */
    private ThingWorkFlowEnum simpleValidation(ThingModel model) {
        if (model.getBarCode() == null || model.getBarCode().isEmpty()) {
            return ThingWorkFlowEnum.FAIL_BAD_BARCODE;
        }

        if (model.getBarCode() == null || model.getBleAddress().isEmpty()) {
            return ThingWorkFlowEnum.FAIL_BAD_BLE_ADDRESS;
        }

        if (model.getDescription() == null || model.getDescription().isEmpty()) {
            return ThingWorkFlowEnum.FAIL_BAD_DESCRIPTION;
        }

        if (model.getName() == null || model.getName().isEmpty()) {
            return ThingWorkFlowEnum.FAIL_BAD_UUID;
        }

        if (model.getNote() == null || model.getNote().isEmpty()) {
            return ThingWorkFlowEnum.FAIL_BAD_NOTE;
        }

        if (model.getGfUuid() == null) {
            return ThingWorkFlowEnum.FAIL_BAD_UUID;
        }

        if (model.getSerialNumber() == null || model.getSerialNumber().isEmpty()) {
            return ThingWorkFlowEnum.FAIL_BAD_SERIAL;
        }

        return ThingWorkFlowEnum.SUCCESS;
    }

    /**
     * ensure unique barcode
     * @param model
     * @param context
     * @return
     */
    private ThingWorkFlowEnum barCodeValidation(ThingModel model, Context context) {
        ThingModel selected = _contentFacade.selectThingByBarCode(model.getBarCode(), context);

        if (selected.getId()< 1) {
            // not in database
            return ThingWorkFlowEnum.SUCCESS;
        }

        if (selected.getId().longValue() == model.getId().longValue()) {
            // update same row
            return ThingWorkFlowEnum.SUCCESS;
        }

        // fall through for failure
        return ThingWorkFlowEnum.FAIL_DUPLICATE_BARCODE;
    }

    /**
     * ensure unique BLE address
     * @param model
     * @param context
     * @return
     */
    private ThingWorkFlowEnum bleValidation(ThingModel model, Context context) {
        ThingModel selected = _contentFacade.selectThingByBleAddress(model.getBleAddress(), context);

        if (selected.getId() < 1) {
            // not in database
            return ThingWorkFlowEnum.SUCCESS;
        }

        if (selected.getId().longValue() == model.getId().longValue()) {
            // update same row
            return ThingWorkFlowEnum.SUCCESS;
        }

        // fall through for failure
        return ThingWorkFlowEnum.FAIL_DUPLICATE_BLE_ADDRESS;
    }


    /**
     * ensure custody sanity
     * @param model
     * @param context
     * @return
     */
    private ThingWorkFlowEnum custodyValidation(ThingModel model, Context context) {
        if (!model.isCustody()) {
            //must have custody to update
            return ThingWorkFlowEnum.FAIL_NOT_CUSTODY;
        }

        return ThingWorkFlowEnum.SUCCESS;
    }

    /**
     *
     * @param context
     * @return
     * @throws Exception
     */
    public Boolean execute(AbstractCmdCtx context) throws Exception {
        boolean result = false;

        final ThingUpdateCtx ctx = (ThingUpdateCtx) context;

        try {
            ThingWorkFlowEnum status = simpleValidation(ctx.getModel());
            if (status != ThingWorkFlowEnum.SUCCESS) {
                ctx.setWorkFlowStatus(status);
                return returnToSender(ctx, ResultEnum.FAIL);
            }

            if (ctx.getModel().isBarCode()) {
                status = barCodeValidation(ctx.getModel(), ctx.getAndroidContext());
                if (status != ThingWorkFlowEnum.SUCCESS) {
                    ctx.setWorkFlowStatus(status);
                    return returnToSender(ctx, ResultEnum.FAIL);
                }
            }

            if (ctx.getModel().isBle()) {
                status = bleValidation(ctx.getModel(), ctx.getAndroidContext());
                if (status != ThingWorkFlowEnum.SUCCESS) {
                    ctx.setWorkFlowStatus(status);
                    return returnToSender(ctx, ResultEnum.FAIL);
                }
            }

            if (ctx.getModel().isCustody()) {
                status = custodyValidation(ctx.getModel(), ctx.getAndroidContext());
                if (status != ThingWorkFlowEnum.SUCCESS) {
                    ctx.setWorkFlowStatus(status);
                    return returnToSender(ctx, ResultEnum.FAIL);
                }
            }

            if (ctx.isDeleteBarCode()) {
                //TODO log entry
                ctx.getModel().setBarCodeFlag(false);
                ctx.getModel().setBarCode("Unknown");
            }

            if (ctx.isUpdateBarCode()) {
                // TODO
            }

            if (ctx.isDeleteBleBeacon()) {
                // TODO log entry
                // deleting BLE beacon stops alerts
                ctx.getModel().setBleFlag(false);
                ctx.getModel().setBleAddress("Unknown");

                if (ctx.getModel().isAlert()) {
                    // deleting BLE beacon stops alerts
                    ctx.getModel().setAlertFlag(false);
                    clearAlert(AlertEnum.BLE_MISSING, ctx.getModel().getId(), ctx.getAndroidContext());
                }
            }

            if (ctx.getModel().isAlert() && ctx.isUpdateBleBeacon()) {
                // new BLE beacon stops alerts
                ctx.getModel().setAlertFlag(false);
                clearAlert(AlertEnum.BLE_MISSING, ctx.getModel().getId(), ctx.getAndroidContext());
            }

            ctx.setWorkFlowStatus(ThingWorkFlowEnum.SUCCESS);

            _contentFacade.updateThing(ctx.getModel(), ctx.getAndroidContext());

            updateLog(ctx.getModel().getId(), ctx.getAndroidContext());

            result = returnToSender(ctx, ResultEnum.OK);
        } catch(Exception exception) {
            result = returnToSender(ctx, ResultEnum.FAIL);
        }

        return result;
    }

    private void clearAlert(AlertEnum alertType, long thingRowId, Context context) {
        AlertStopCtx alertStopCtx = (AlertStopCtx) ContextFactory.factory(CommandEnum.ALERT_STOP, context);
        alertStopCtx.setAlertType(alertType);
        alertStopCtx.setThingRowId(thingRowId);
        CommandFactory.execute(alertStopCtx);
    }

    private void updateLog(long rowId, Context context) {
        EventModel model = new EventModel();
        model.setDefault();
        model.setNote("update thing:" + rowId);

        _contentFacade.insertEvent(model, context);
    }
}
