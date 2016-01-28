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


    //////////////////////////
    //////////////////////////
    //////////////////////////

    /**
     * @param model
     * @param context
     */
    public void updatePart(ItemModel model, Context context) {
        if (model.getId() > 0L) {
            String[] target = new String[1];
            target[0] = Long.toString(model.getId());
            context.getContentResolver().update(ItemTable.CONTENT_URI, model.toContentValues(), "_id=?", target);
        } else {
            Uri uri = context.getContentResolver().insert(ItemTable.CONTENT_URI, model.toContentValues());
            model.setId(ContentUris.parseId(uri));
        }
    }


    /**
     * @param rowId
     * @param context
     * @return
     */
    public ItemModel selectPartByRowId(long rowId, Context context) {
        ItemModel model = new ItemModel();
        model.setDefault();

        if (rowId > 0) {
            String[] target = new String[1];
            target[0] = Long.toString(rowId);

            Cursor cursor = context.getContentResolver().query(ItemTable.CONTENT_URI, null, "_id=?", target, null);
            if (cursor.moveToFirst()) {
                model.fromCursor(cursor);
            }

            cursor.close();
        }

        return model;
    }


}