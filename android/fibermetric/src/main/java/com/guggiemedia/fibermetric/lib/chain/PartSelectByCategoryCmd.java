package com.guggiemedia.fibermetric.lib.chain;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.guggiemedia.fibermetric.lib.db.DataBaseHelper;
import com.guggiemedia.fibermetric.lib.db.InventoryCategoryEnum;
import com.guggiemedia.fibermetric.lib.db.PartModel;
import com.guggiemedia.fibermetric.lib.db.PartTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Select inventory items by category
 */
public class PartSelectByCategoryCmd extends AbstractCmd {
    public static final String LOG_TAG = PartSelectByCategoryCmd.class.getName();

    private DataBaseHelper _dbh;

    /**
     *
     * @param context
     * @return
     * @throws Exception
     */
    public Boolean execute(AbstractCmdCtx context) throws Exception {
        final PartSelectByCategoryCtx ctx = (PartSelectByCategoryCtx) context;

        boolean result;

        try {
            _dbh = new DataBaseHelper(ctx.getAndroidContext());
            ctx.setSelected(selectItemsByCategory(ctx.getCategory()));
            result = returnToSender(ctx, ResultEnum.OK);
        } catch(Exception exception) {
            result = returnToSender(ctx, ResultEnum.FAIL);
        }

        return result;
    }

    public List<PartModel> selectItemsByCategory(InventoryCategoryEnum category) {
        List<PartModel> result = new ArrayList<>();

        PartTable table = new PartTable();

        String selection = PartTable.Columns.CATEGORY + "=?";
        String[] selectionArgs = new String[] { category.toString() };

        String orderBy = PartTable.Columns.STATUS + " ASC";

        SQLiteDatabase sqlDb = _dbh.getReadableDatabase();
        Cursor cursor = sqlDb.query(PartTable.TABLE_NAME, table.getDefaultProjection(), selection, selectionArgs, null, null, orderBy);

        if (cursor.moveToFirst()) {
            do {
                PartModel model = new PartModel();
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