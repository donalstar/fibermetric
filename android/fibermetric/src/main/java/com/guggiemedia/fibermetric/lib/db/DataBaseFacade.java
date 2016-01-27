package com.guggiemedia.fibermetric.lib.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.guggiemedia.fibermetric.lib.Constant;

import java.util.Date;

/**
 *
 */
public class DataBaseFacade {
    public static final String LOG_TAG = DataBaseFacade.class.getName();

    private DataBaseHelper _dbh;

    /**
     *
     * @param appContext
     */
    public DataBaseFacade(Context context) {
        _dbh = new DataBaseHelper(context);
    }

    /**
     * insert/update a database table from model
     *
     * @param rowId
     * @param tableName
     * @return
     */
    public int deleteModel(Long rowId, String tableName) {
        String selection = "_id=?";
        String selectionArgs[] = new String[] {rowId.toString()};

        SQLiteDatabase sqlDb = _dbh.getWritableDatabase();
        int count = sqlDb.delete(tableName, selection, selectionArgs);
        sqlDb.close();

        return count;
    }

    /**
     * insert/update a database table from model
     *
     * @param arg populated model
     * @return true is success
     */
    public boolean updateModel(DataBaseModel arg) {
        boolean flag = false;

        SQLiteDatabase sqlDb = _dbh.getWritableDatabase();

        if ((arg.getId() == null) || (arg.getId() < 1)) {
            //insert
            long result = sqlDb.insert(arg.getTableName(), null, arg.toContentValues());
            if (result > 0) {
                arg.setId(result);
                flag = true;
            }
        } else {
            //update
            String selection = "_id=?";
            String[] selectionArgs = new String[] {arg.getId().toString()};
            int result = sqlDb.update(arg.getTableName(), arg.toContentValues(), selection, selectionArgs);
            if (result > 0) {
                flag = true;
            }
        }

        sqlDb.close();

        return flag;
    }

    /**
     * Select a database row, return as model
     *
     * @param rowId target row ID
     * @param dbTable target table
     * @return
     */
    public DataBaseModel selectModel(Long rowId, DataBaseTable dbTable) {
        String tableName = dbTable.getTableName();
        String[] projection = dbTable.getDefaultProjection();

        String selection = "_id=?";
        String[] selectionArgs = new String[] {rowId.toString()};

        SQLiteDatabase sqlDb = _dbh.getReadableDatabase();
        Cursor cursor = sqlDb.query(tableName, projection, selection, selectionArgs, null, null, null);

        DataBaseModel result = toModel(tableName);
        if (cursor.moveToFirst()) {
            result.fromCursor(cursor);
        } else {
            Log.i(LOG_TAG, "selection failure:" + rowId + ":" + dbTable.getTableName());
        }

        cursor.close();
        sqlDb.close();

        return result;
    }

    /**
     * Map cursor to model
     *
     * @param cursor
     * @param tableName
     * @return populated model
     */
    private DataBaseModel toModel(String tableName) {
        DataBaseModel result = null;

        if (tableName.equals(ImageTable.TABLE_NAME)) {
            result = new ImageModel();
        } else if (tableName.equals(JobTaskTable.TABLE_NAME)) {
            result = new JobTaskModel();
        } else if (tableName.equals(PartTable.TABLE_NAME)) {
            result = new PartModel();
        } else if (tableName.equals(SiteTable.TABLE_NAME)) {
            result = new SiteModel();
        } else {
            throw new IllegalArgumentException("unknown table:" + tableName);
        }

        result.setDefault();
        return result;
    }


    /**
     * Return tasks associated w/a job
     * @param parentId
     * @return
     */
    public JobTaskModelList selectJobTaskByParent(Long parentId) {
        JobTaskModelList result = new JobTaskModelList();

        JobTaskTable table = new JobTaskTable();

        String selection = JobTaskTable.Columns.PARENT + "=? and " + JobTaskTable.Columns.JOB_FLAG + "=?";
        String[] selectionArgs = new String[] {Long.toString(parentId), Integer.toString(Constant.SQL_FALSE)};

        String orderBy = JobTaskTable.Columns.ORDER_NDX + " ASC";

        SQLiteDatabase sqlDb = _dbh.getReadableDatabase();
        Cursor cursor = sqlDb.query(JobTaskTable.TABLE_NAME, table.getDefaultProjection(), selection, selectionArgs, null, null, orderBy);

        if (cursor.moveToFirst()) {
            do {
                JobTaskModel model = new JobTaskModel();
                model.setDefault();
                model.fromCursor(cursor);
                result.add(model);
            } while(cursor.moveToNext());
        }

        cursor.close();
        sqlDb.close();

        return result;
    }

    public JobTaskModelList selectJobs(boolean todayOnly) {
        JobTaskModelList result = new JobTaskModelList();

        JobTaskTable table = new JobTaskTable();

        String selection = null;
        String[] selectionArgs = null;

        String[] projection = table.getDefaultProjection();
        String orderBy = JobTaskTable.Columns.JOB_STATE + " DESC";

        if (todayOnly) {
            String target = JobTaskModel.formatter2(new Date());

            selection = JobTaskTable.Columns.JOB_FLAG + "=? and " + JobTaskTable.Columns.DEADLINE + "=?";
            selectionArgs = new String[] {Integer.toString(Constant.SQL_TRUE), target};
        } else {
            selection = JobTaskTable.Columns.JOB_FLAG + "=?";
            selectionArgs = new String[] {Integer.toString(Constant.SQL_TRUE)};
        }

        SQLiteDatabase sqlDb = _dbh.getReadableDatabase();
        Cursor cursor = sqlDb.query(JobTaskTable.TABLE_NAME, table.getDefaultProjection(), selection, selectionArgs, null, null, orderBy);

        if (cursor.moveToFirst()) {
            do {
                JobTaskModel model = new JobTaskModel();
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