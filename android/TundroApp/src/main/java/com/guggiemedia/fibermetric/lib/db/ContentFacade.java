package com.guggiemedia.fibermetric.lib.db;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;


import com.guggiemedia.fibermetric.lib.Constant;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * access database via ContentProvider
 */
public class ContentFacade {
    public static final String LOG_TAG = ContentFacade.class.getName();

    public AlertModel selectActiveAlert(AlertEnum alertType, Long thingRowId, Context context) {
        AlertModelList alertModelList = selectActiveAlertByThingId(thingRowId, context);
        for (AlertModel alertModel : alertModelList) {
            if (alertModel.isActive() && (alertModel.getAlertType() == alertType)) {
                return alertModel;
            }
        }

        return null;
    }

    public AlertModelList selectActiveAlertByThingId(long rowId, Context context) {
        AlertModelList result = new AlertModelList();

        String selection = AlertTable.Columns.THING_ID + "=? and " + AlertTable.Columns.ACTIVE_FLAG + "=?";
        String selectionArgs[] = new String[]{Long.toString(rowId), Integer.toString(Constant.SQL_TRUE)};

        Cursor cursor = context.getContentResolver().query(AlertTable.CONTENT_URI, null, selection, selectionArgs, null);
        if (cursor.moveToFirst()) {
            do {
                AlertModel model = new AlertModel();
                model.setDefault();
                model.fromCursor(cursor);
                result.add(model);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return result;
    }

    public AlertModelList selectActiveAlertByType(AlertEnum alertType, Context context) {
        AlertModelList result = new AlertModelList();

        String selection = AlertTable.Columns.ALERT_TYPE + "=? and " + AlertTable.Columns.ACTIVE_FLAG + "=?";
        String selectionArgs[] = new String[]{alertType.toString(), Integer.toString(Constant.SQL_TRUE)};

        Cursor cursor = context.getContentResolver().query(AlertTable.CONTENT_URI, null, selection, selectionArgs, null);
        if (cursor.moveToFirst()) {
            do {
                AlertModel model = new AlertModel();
                model.setDefault();
                model.fromCursor(cursor);
                result.add(model);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return result;
    }

    public AlertModel selectAlertByRowId(long rowId, Context context) {
        AlertModel model = new AlertModel();
        model.setDefault();

        if (rowId > 0) {
            String[] target = new String[1];
            target[0] = Long.toString(rowId);

            Cursor cursor = context.getContentResolver().query(AlertTable.CONTENT_URI, null, "_id=?", target, null);
            if (cursor.moveToFirst()) {
                model.fromCursor(cursor);
            }

            cursor.close();
        }

        return model;
    }

    public void updateAlert(AlertModel model, Context context) {
        Log.d(LOG_TAG, "updateAlert:" + model.getId() + ":" + model.getAlertType());

        if (model.getId() > 0L) {
            String[] target = new String[1];
            target[0] = Long.toString(model.getId());
            context.getContentResolver().update(AlertTable.CONTENT_URI, model.toContentValues(), "_id=?", target);
        } else {
            Uri uri = context.getContentResolver().insert(AlertTable.CONTENT_URI, model.toContentValues());
            model.setId(ContentUris.parseId(uri));
        }
    }

    //////////////////////////
    //////////////////////////
    //////////////////////////

    public void updateChainOfCustody(ChainOfCustodyModel model, Context context) {
        Log.d(LOG_TAG, "updateChainOfCustody:" + model.getId() + ":" + model.getCustodian());

        if (model.getId() > 0L) {
            String[] target = new String[1];
            target[0] = Long.toString(model.getId());
            context.getContentResolver().update(ChainOfCustodyTable.CONTENT_URI, model.toContentValues(), "_id=?", target);
        } else {
            Uri uri = context.getContentResolver().insert(ChainOfCustodyTable.CONTENT_URI, model.toContentValues());
            model.setId(ContentUris.parseId(uri));
        }
    }

    public void updateChat(ChatModel model, Context context) {
        Log.d(LOG_TAG, "updateChat:" + model.getId() + ":" + model.getParticipant());

        Uri uri = context.getContentResolver().insert(ChatTable.CONTENT_URI, model.toContentValues());
        model.setId(ContentUris.parseId(uri));
    }

    public void updateChatMessage(ChatMessageModel model, Context context) {
        Log.d(LOG_TAG, "updateChatMessage:" + model.getId() + ":" + model.getName());

        Uri uri = context.getContentResolver().insert(ChatMessageTable.CONTENT_URI, model.toContentValues());
        model.setId(ContentUris.parseId(uri));
    }

    //////////////////////////
    //////////////////////////
    //////////////////////////

    public void insertEvent(EventModel model, Context context) {
        context.getContentResolver().insert(EventTable.CONTENT_URI, model.toContentValues());
    }

    //////////////////////////
    //////////////////////////
    //////////////////////////

    public void updateImage(ImageModel model, Context context) {
        if (model.getId() > 0L) {
            String[] target = new String[1];
            target[0] = Long.toString(model.getId());
            context.getContentResolver().update(ImageTable.CONTENT_URI, model.toContentValues(), "_id=?", target);
        } else {
            Uri uri = context.getContentResolver().insert(ImageTable.CONTENT_URI, model.toContentValues());
            model.setId(ContentUris.parseId(uri));
        }
    }

    //////////////////////////
    //////////////////////////
    //////////////////////////

    public List<JobTaskModel> selectJobTaskAll(Context context) {
        List<JobTaskModel> result = new ArrayList<>();

        Cursor cursor = context.getContentResolver().query(JobTaskTable.CONTENT_URI, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                JobTaskModel model = new JobTaskModel();
                model.setDefault();
                model.fromCursor(cursor);
                result.add(model);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return result;
    }

    /**
     * @param date
     * @param context
     * @return
     */
    public List<JobTaskModel> selectJobTaskForDeadlineDate(Date date, Context context) {
        List<JobTaskModel> result = new ArrayList<>();

        String[] target = new String[2];
        target[0] = JobTaskModel.formatter2(date);
        target[1] = Integer.toString(Constant.SQL_TRUE);

        Log.i(LOG_TAG, "Get job tasks by date " + target[0]);

        Cursor cursor = context.getContentResolver().query(JobTaskTable.CONTENT_URI, null,
                JobTaskTable.Columns.DEADLINE + "=? and " + JobTaskTable.Columns.JOB_FLAG + "=?", target, null);

        if (cursor.moveToFirst()) {
            do {
                JobTaskModel model = new JobTaskModel();
                model.setDefault();
                model.fromCursor(cursor);
                result.add(model);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return result;
    }

    /**
     * @param date
     * @param context
     * @return
     */
    public List<JobTaskModel> selectJobTaskByDeadlineDate(Date date, Context context) {
        List<JobTaskModel> result = new ArrayList<>();

        String[] target = new String[2];

        target[0] = String.valueOf(date.getTime());
        target[1] = Integer.toString(Constant.SQL_TRUE);

        Cursor cursor = context.getContentResolver().query(JobTaskTable.CONTENT_URI, null,
                JobTaskTable.Columns.DEADLINE_MS + "<? and " + JobTaskTable.Columns.JOB_FLAG + "=?",
                target, null);

        if (cursor.moveToFirst()) {
            do {
                JobTaskModel model = new JobTaskModel();
                model.setDefault();
                model.fromCursor(cursor);
                result.add(model);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return result;
    }

    //////////////////////////
    //////////////////////////
    //////////////////////////

    /**
     * @param model
     * @param context
     */
    public void updateJobTask(JobTaskModel model, Context context) {

        Log.i(LOG_TAG, "Update JOB " + model.getId() + " TO STATE " + model.getState());

        if (model.getId() > 0L) {
            String[] target = new String[1];
            target[0] = Long.toString(model.getId());
            context.getContentResolver().update(JobTaskTable.CONTENT_URI, model.toContentValues(), "_id=?", target);
        } else {
            Uri uri = context.getContentResolver().insert(JobTaskTable.CONTENT_URI, model.toContentValues());
            model.setId(ContentUris.parseId(uri));
        }
    }

    /**
     * @param model
     * @param context
     */
    public void updateJobPart(JobPartModel model, Context context) {
        if (model.getId() > 0L) {
            String[] target = new String[1];
            target[0] = Long.toString(model.getId());
            context.getContentResolver().update(JobPartTable.CONTENT_URI, model.toContentValues(), "_id=?", target);
        } else {
            Uri uri = context.getContentResolver().insert(JobPartTable.CONTENT_URI, model.toContentValues());
            model.setId(ContentUris.parseId(uri));
        }
    }


    //////////////////////////
    //////////////////////////
    //////////////////////////

    /**
     * @param model
     * @param context
     */
    public void updateTaskAction(TaskActionModel model, Context context) {
        if (model.getId() > 0L) {
            String[] target = new String[1];
            target[0] = Long.toString(model.getId());
            context.getContentResolver().update(TaskActionTable.CONTENT_URI, model.toContentValues(), "_id=?", target);
        } else {
            Uri uri = context.getContentResolver().insert(TaskActionTable.CONTENT_URI, model.toContentValues());
            model.setId(ContentUris.parseId(uri));
        }
    }

    /**
     * @param model
     * @param context
     */
    public void updateTaskDetail(TaskDetailModel model, Context context) {
        if (model.getId() > 0L) {
            String[] target = new String[1];
            target[0] = Long.toString(model.getId());
            context.getContentResolver().update(TaskDetailTable.CONTENT_URI, model.toContentValues(), "_id=?", target);
        } else {
            Uri uri = context.getContentResolver().insert(TaskDetailTable.CONTENT_URI, model.toContentValues());
            model.setId(ContentUris.parseId(uri));
        }
    }

    //////////////////////////
    //////////////////////////
    //////////////////////////

    /**
     * @param model
     * @param context
     */
    public void updatePart(PartModel model, Context context) {
        if (model.getId() > 0L) {
            String[] target = new String[1];
            target[0] = Long.toString(model.getId());
            context.getContentResolver().update(PartTable.CONTENT_URI, model.toContentValues(), "_id=?", target);
        } else {
            Uri uri = context.getContentResolver().insert(PartTable.CONTENT_URI, model.toContentValues());
            model.setId(ContentUris.parseId(uri));
        }
    }

    /**
     * @param partId
     * @param barcode
     * @param context
     * @return
     */
    public PartModel replaceBarcodeForPart(long partId, String barcode, Context context) {
        PartModel partModel = selectPartByRowId(partId, context);

        if (partModel.getId() != 0L) {
            // update barcode
            partModel.setBarcode(barcode);

            updatePart(partModel, context);
        }

        return partModel;
    }

    /**
     * @param barcode
     * @param context
     * @return
     */
    public PartModel updatePartStatusByBarcode(String barcode, Context context) {
        Log.d(LOG_TAG, "updatePartStatusByBarcode " + barcode);

        PartModel partModel = selectPartByBarcode(barcode, context);

        if (partModel.getId() != 0L) {
            // update status
            partModel.setStatus(InventoryStatusEnum.inCustody);

            updatePart(partModel, context);
        } else {
            partModel = null;
        }

        return partModel;
    }

    /**
     * @param rowId
     * @param context
     * @return
     */
    public PartModel selectPartByRowId(long rowId, Context context) {
        PartModel model = new PartModel();
        model.setDefault();

        if (rowId > 0) {
            String[] target = new String[1];
            target[0] = Long.toString(rowId);

            Cursor cursor = context.getContentResolver().query(PartTable.CONTENT_URI, null, "_id=?", target, null);
            if (cursor.moveToFirst()) {
                model.fromCursor(cursor);
            }

            cursor.close();
        }

        return model;
    }

    /**
     * @param context
     * @return
     */
    public List<PartModel> selectPartsWithBleBeacons(Context context) {
        List<PartModel> result = new ArrayList<>();

        String[] target = new String[]{"0"};

        Cursor cursor = context.getContentResolver().query(PartTable.CONTENT_URI, null, "ble_address<>?", target, null);
        if (cursor.moveToFirst()) {
            do {
                PartModel model = new PartModel();

                model.setDefault();
                model.fromCursor(cursor);
                result.add(model);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return result;
    }

    /**
     * @param barcode
     * @param context
     * @return
     */
    public PartModel selectPartByBarcode(String barcode, Context context) {
        PartModel model = new PartModel();
        model.setDefault();

        if (barcode != null) {
            String[] target = new String[1];
            target[0] = barcode;

            Cursor cursor = context.getContentResolver().query(PartTable.CONTENT_URI, null, "barcode=?", target, null);
            if (cursor.moveToFirst()) {
                model.fromCursor(cursor);
            }

            cursor.close();
        }

        return model;
    }


    //////////////////////////
    //////////////////////////
    //////////////////////////

    public PersonModelList selectPersonAll(Context context) {
        PersonModelList result = new PersonModelList();

        Cursor cursor = context.getContentResolver().query(PersonTable.CONTENT_URI, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                PersonModel model = new PersonModel();
                model.setDefault();
                model.fromCursor(cursor);
                result.add(model);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return result;
    }

    public PersonModel selectPersonByRowId(long rowId, Context context) {
        PersonModel model = new PersonModel();
        model.setDefault();

        if (rowId > 0) {
            String[] target = new String[1];
            target[0] = Long.toString(rowId);

            Cursor cursor = context.getContentResolver().query(PersonTable.CONTENT_URI, null, "_id=?", target, null);
            if (cursor.moveToFirst()) {
                model.fromCursor(cursor);
            }

            cursor.close();
        }

        return model;
    }

    public List<SiteModel> selectSiteAll(Context context) {
        List<SiteModel> result = new ArrayList<>();

        Cursor cursor = context.getContentResolver().query(SiteTable.CONTENT_URI, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                SiteModel model = new SiteModel();
                model.setDefault();
                model.fromCursor(cursor);
                result.add(model);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return result;
    }

    /*
    public PersonModel selectPersonSelf(Context context) {
        PersonModel model = new PersonModel();
        model.setDefault();

        String[] target = new String[1];
        target[0] = Integer.toString(Constant.SQL_TRUE);

        Cursor cursor = context.getContentResolver().query(PersonTable.CONTENT_URI, null, PersonTable.Columns.SELF_FLAG + "=?", target, null);
        if (cursor.moveToFirst()) {
            model.fromCursor(cursor);
        }

        cursor.close();

        return model;
    }
    */

    public void updatePerson(PersonModel model, Context context) {
        if (model.getId() > 0L) {
            String[] target = new String[1];
            target[0] = Long.toString(model.getId());
            context.getContentResolver().update(PersonTable.CONTENT_URI, model.toContentValues(), "_id=?", target);
        } else {
            Uri uri = context.getContentResolver().insert(PersonTable.CONTENT_URI, model.toContentValues());
            model.setId(ContentUris.parseId(uri));
        }
    }

    /**
     * @param model
     * @param context
     */
    public void updatePersonPart(PersonPartModel model, Context context) {
        if (model.getId() > 0L) {
            String[] target = new String[1];
            target[0] = Long.toString(model.getId());
            context.getContentResolver().update(PersonPartTable.CONTENT_URI, model.toContentValues(), "_id=?", target);
        } else {
            Uri uri = context.getContentResolver().insert(PersonPartTable.CONTENT_URI, model.toContentValues());
            model.setId(ContentUris.parseId(uri));
        }
    }


    //////////////////////////
    //////////////////////////
    //////////////////////////

    /**
     * @param model
     * @param context
     */
    public void updateSite(SiteModel model, Context context) {
        if (model.getId() > 0L) {
            String[] target = new String[1];
            target[0] = Long.toString(model.getId());
            context.getContentResolver().update(SiteTable.CONTENT_URI, model.toContentValues(), "_id=?", target);
        } else {
            Uri uri = context.getContentResolver().insert(SiteTable.CONTENT_URI, model.toContentValues());
            model.setId(ContentUris.parseId(uri));
        }
    }

    //////////////////////////
    //////////////////////////
    //////////////////////////

    /*
    public void deleteTask(Long arg, Context context) {
        String[] target = new String[1];
        target[0] = arg.toString();

        context.getContentResolver().delete(TaskTable.CONTENT_URI, "_id=?", target);
    }
    */

    /**
     * @param model
     * @param context
     */
    /*
    public void updateTask(TaskModel model, Context context) {
        if (model.getId() > 0L) {
            String[] target = new String[1];
            target[0] = Long.toString(model.getId());
            context.getContentResolver().update(TaskTable.CONTENT_URI, model.toContentValues(), "_id=?", target);
        } else {
            Uri uri = context.getContentResolver().insert(TaskTable.CONTENT_URI, model.toContentValues());
            model.setId(ContentUris.parseId(uri));
        }
    }
    */

    //////////////////////////
    //////////////////////////
    //////////////////////////

    /**
     * select model by row id
     *
     * @param rowId
     * @param context
     * @return
     */
    public ThingModel selectThingByRowId(long rowId, Context context) {
        ThingModel model = new ThingModel();
        model.setDefault();

        if (rowId > 0) {
            String[] target = new String[1];
            target[0] = Long.toString(rowId);

            Cursor cursor = context.getContentResolver().query(ThingTable.CONTENT_URI, null, "_id=?", target, null);
            if (cursor.moveToFirst()) {
                model.fromCursor(cursor);
            }

            cursor.close();
        }

        return model;
    }

    /**
     * select model by barcode
     *
     * @param barcode
     * @param context
     * @return
     */
    public ThingModel selectThingByBarCode(String barcode, Context context) {
        ThingModel model = new ThingModel();
        model.setDefault();

        String selection = ThingTable.Columns.BARCODE + "=? and " + ThingTable.Columns.BARCODE_FLAG + "=?";
        String selectionArgs[] = new String[]{barcode, Integer.toString(Constant.SQL_TRUE)};

        Cursor cursor = context.getContentResolver().query(ThingTable.CONTENT_URI, null, selection, selectionArgs, null);
        if (cursor.moveToFirst()) {
            model.fromCursor(cursor);
        }

        cursor.close();

        return model;
    }

    /**
     * select model by BLE address
     *
     * @param address
     * @param context
     * @return selected model if found, else default model
     */
    public ThingModel selectThingByBleAddress(String address, Context context) {
        ThingModel model = new ThingModel();
        model.setDefault();

        String selection = ThingTable.Columns.BLE_ADDRESS + "=? and " + ThingTable.Columns.BLE_FLAG + "=?";
        String selectionArgs[] = new String[]{address, Integer.toString(Constant.SQL_TRUE)};

        Cursor cursor = context.getContentResolver().query(ThingTable.CONTENT_URI, null, selection, selectionArgs, null);
        if (cursor.moveToFirst()) {
            model.fromCursor(cursor);
        }

        cursor.close();

        return model;
    }

    /**
     * @param model
     * @param context
     */
    public void updateThing(ThingModel model, Context context) {
        if (model.getId() > 0L) {
            String[] target = new String[1];
            target[0] = Long.toString(model.getId());
            context.getContentResolver().update(ThingTable.CONTENT_URI, model.toContentValues(), "_id=?", target);
        } else {
            Uri uri = context.getContentResolver().insert(ThingTable.CONTENT_URI, model.toContentValues());
            model.setId(ContentUris.parseId(uri));
        }
    }

    //////////////////////////
    //////////////////////////
    //////////////////////////

    /**
     * @param model
     * @param context
     */
    public void updateTool(ToolModel model, Context context) {
        if (model.getId() > 0L) {
            String[] target = new String[1];
            target[0] = Long.toString(model.getId());
            context.getContentResolver().update(ToolTable.CONTENT_URI, model.toContentValues(), "_id=?", target);
        } else {
            Uri uri = context.getContentResolver().insert(ToolTable.CONTENT_URI, model.toContentValues());
            model.setId(ContentUris.parseId(uri));
        }
    }
}