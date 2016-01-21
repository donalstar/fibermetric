package com.guggiemedia.fibermetric.lib.chain;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.guggiemedia.fibermetric.lib.Constant;
import com.guggiemedia.fibermetric.lib.db.DataBaseHelper;
import com.guggiemedia.fibermetric.lib.db.ThingModel;
import com.guggiemedia.fibermetric.lib.db.ThingModelList;
import com.guggiemedia.fibermetric.lib.db.ThingTable;

/**
 * Select a thing by barcode
 */
public class ThingSelectByBarCodeCmd extends AbstractCmd {
    public static final String LOG_TAG = ThingSelectByBarCodeCmd.class.getName();

    private DataBaseHelper _dbh;

    /**
     *
     * @param context
     * @return
     * @throws Exception
     */
    public Boolean execute(AbstractCmdCtx context) throws Exception {
        boolean result = false;

        final ThingSelectByBarCodeCtx ctx = (ThingSelectByBarCodeCtx) context;

        try {
            _dbh = new DataBaseHelper(ctx.getAndroidContext());
            ctx.setSelected(selectThingByBarCode(ctx.getBarCode()));
            result = returnToSender(ctx, ResultEnum.OK);
        } catch(Exception exception) {
            result = returnToSender(ctx, ResultEnum.FAIL);
        }

        return result;
    }

    public ThingModelList selectThingByBarCode(String address) {
        ThingModelList result = new ThingModelList();
        ThingTable thingTable = new ThingTable();

        String selection = ThingTable.Columns.BARCODE + "=? and " + ThingTable.Columns.BARCODE_FLAG + "=?";
        String[] selectionArgs = new String[] {address, Integer.toString(Constant.SQL_TRUE)};

        SQLiteDatabase sqlDb = _dbh.getReadableDatabase();
        Cursor cursor = sqlDb.query(ThingTable.TABLE_NAME, thingTable.getDefaultProjection(), selection, selectionArgs, null, null, ThingTable.DEFAULT_SORT_ORDER);

        if (cursor.moveToFirst()) {
            do {
                ThingModel model = new ThingModel();
                model.setDefault();
                model.fromCursor(cursor);
                result.add(model);
            } while(cursor.moveToNext());
        }

        cursor.close();
        sqlDb.close();

        return result;
    }
}