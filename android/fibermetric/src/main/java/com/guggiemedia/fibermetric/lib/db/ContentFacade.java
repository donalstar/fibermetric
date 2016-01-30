package com.guggiemedia.fibermetric.lib.db;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

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
    public Double getGetDailyProgress(Context context) {
        Double daily = 35.0;

        Double current = 0.0;

        Cursor cursor = context.getContentResolver().query(AddedItemTable.ADDED_ITEMS_CONTENT_URI, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                AddedItemModel model = new AddedItemModel();
                model.setDefault();
                model.fromCursor(cursor);

                current += model.getGrams();
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