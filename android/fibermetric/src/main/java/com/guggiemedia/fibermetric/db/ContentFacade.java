package com.guggiemedia.fibermetric.db;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * access database via ContentProvider
 */
public class ContentFacade {
    public static final String LOG_TAG = ContentFacade.class.getName();

    //////////////////////////
    //////////////////////////
    //////////////////////////

    /**
     * @param model
     * @param context
     */
    public void updateItem(ItemModel model, Context context) {
        updateModel(model, ItemTable.CONTENT_URI, context);
    }

    /**
     * @param model
     * @param context
     */
    public void updateAddedItem(AddedItemModel model, Context context) {
        updateModel(model, AddedItemTable.CONTENT_URI, context);
    }

    /**
     * @param model
     * @param context
     */
    public void updateDailyRecord(DailyRecordModel model, Context context) {
        updateModel(model, DailyRecordTable.CONTENT_URI, context);
    }


    /**
     * @param model
     * @param contentUri
     * @param context
     */
    private void updateModel(DataBaseModel model, Uri contentUri, Context context) {
        if (model.getId() > 0L) {
            String[] target = new String[1];
            target[0] = Long.toString(model.getId());
            context.getContentResolver().update(contentUri, model.toContentValues(), "_id=?", target);
        } else {
            Uri uri = context.getContentResolver().insert(contentUri, model.toContentValues());
            model.setId(ContentUris.parseId(uri));
        }
    }

    /**
     * @param context
     * @return
     */
    public List<DailyRecordModel> selectDailyRecordAll(Context context) {
        List<DailyRecordModel> result = new ArrayList<>();

        Cursor cursor = context.getContentResolver().query(DailyRecordTable.CONTENT_URI, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                DailyRecordModel model = new DailyRecordModel();
                model.setDefault();
                model.fromCursor(cursor);
                result.add(model);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return result;
    }

    /**
     * @param context
     * @param date
     * @return
     */
    public DailyRecordModel selectDailyRecordForDate(Context context, Date date) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Date startDate = calendar.getTime();

        calendar.add(Calendar.DAY_OF_MONTH, 1);

        Date endDate = calendar.getTime();

        List<DailyRecordModel> models = selectDailyRecordByDate(context, startDate, endDate);

        return (models.isEmpty()) ? null : models.get(0);
    }

    /**
     * @param context
     * @return
     */
    public List<DailyRecordModel> selectDailyRecordByDate(Context context, Date fromDate, Date toDate) {
        List<DailyRecordModel> result = new ArrayList<>();

        String selection = "date > ? and date < ?";

        String[] selectionArgs = {String.valueOf(fromDate.getTime()), String.valueOf(toDate.getTime())};

        Cursor cursor = context.getContentResolver().query(DailyRecordTable.CONTENT_URI, null, selection, selectionArgs, null);

        if (cursor.moveToFirst()) {
            do {
                DailyRecordModel model = new DailyRecordModel();
                model.setDefault();
                model.fromCursor(cursor);
                result.add(model);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return result;
    }

    /**
     *
     * @param dateRange
     * @param context
     * @return
     */
    public Double getGetDailyProgress(Date dateRange[], Context context) {
        Double daily = 35.0;

        Double current = 0.0;

        String selection = "date > ? and date < ?";
        String selectionArgs[] = {String.valueOf(dateRange[0].getTime()), String.valueOf(dateRange[1].getTime())};

        Cursor cursor = context.getContentResolver().query(AddedItemTable.ADDED_ITEMS_CONTENT_URI, null, selection, selectionArgs, null);
        if (cursor.moveToFirst()) {
            do {
                AddedItemModel model = new AddedItemModel();
                model.setDefault();
                model.fromCursor(cursor);

                current += (model.getGrams() * model.getWeightMultiple());
            } while (cursor.moveToNext());
        }

        cursor.close();

        return current / daily;
    }

    public void deleteAddedItem(Long id, Context context) {
        String[] target = new String[1];
        target[0] = Long.toString(id);

        context.getContentResolver().delete(AddedItemTable.CONTENT_URI, "_id=?", target);
    }
}