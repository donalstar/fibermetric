package com.guggiemedia.fibermetric.lib.chain;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.guggiemedia.fibermetric.lib.Constant;
import com.guggiemedia.fibermetric.lib.db.DataBaseHelper;
import com.guggiemedia.fibermetric.lib.db.JobTaskModel;
import com.guggiemedia.fibermetric.lib.db.JobTaskTable;
import com.guggiemedia.fibermetric.lib.db.KeyList;


/**
 * Select a tasks by parent job id
 */
public class JobTaskSelectByJobIdCmd extends AbstractCmd {
    public static final String LOG_TAG = JobTaskSelectByJobIdCmd.class.getName();

    private DataBaseHelper _dbh;

    /**
     *
     * @param context
     * @return
     * @throws Exception
     */
    public Boolean execute(AbstractCmdCtx context) throws Exception {
        boolean result = false;

        final JobTaskSelectByJobIdCtx ctx = (JobTaskSelectByJobIdCtx) context;

        try {
            _dbh = new DataBaseHelper(ctx.getAndroidContext());
            ctx.setSelected(select(ctx.getJobId()));
            result = returnToSender(ctx, ResultEnum.OK);
        } catch(Exception exception) {
            result = returnToSender(ctx, ResultEnum.FAIL);
        }

        return result;
    }

    private KeyList select(Long jobId) {
        KeyList result = new KeyList();

        JobTaskTable table = new JobTaskTable();

        String selection = JobTaskTable.Columns.PARENT + "=? and " + JobTaskTable.Columns.JOB_FLAG + "=?";
        String[] selectionArgs = new String[] {Long.toString(jobId), Integer.toString(Constant.SQL_FALSE)};

        String orderBy = JobTaskTable.Columns.ORDER_NDX + " ASC";

        SQLiteDatabase sqlDb = _dbh.getReadableDatabase();
        Cursor cursor = sqlDb.query(JobTaskTable.TABLE_NAME, table.getDefaultProjection(), selection, selectionArgs, null, null, orderBy);

        if (cursor.moveToFirst()) {
            do {
                JobTaskModel model = new JobTaskModel();
                model.setDefault();
                model.fromCursor(cursor);
                result.add(model.getId());
            } while(cursor.moveToNext());
        }

        cursor.close();
        sqlDb.close();

        return result;
    }
}