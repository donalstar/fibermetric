package com.guggiemedia.fibermetric.lib.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 *
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_FILE_NAME = "tundro.db";
    public static final int DATABASE_VERSION = 1;

    public DataBaseHelper(Context context) {
        super(context, context.getExternalFilesDir(null).getAbsolutePath() + "/" + DATABASE_FILE_NAME, null, DATABASE_VERSION);
//        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(AlertTable.CREATE_TABLE);
        db.execSQL(ChainOfCustodyTable.CREATE_TABLE);
        db.execSQL(ChatTable.CREATE_TABLE);
        db.execSQL(ChatMessageTable.CREATE_TABLE);
        db.execSQL(EventTable.CREATE_TABLE);
        db.execSQL(ImageTable.CREATE_TABLE);
        db.execSQL(JobPartTable.CREATE_TABLE);
        db.execSQL(JobTaskTable.CREATE_TABLE);
        db.execSQL(PartTable.CREATE_TABLE);
        db.execSQL(PersonTable.CREATE_TABLE);
        db.execSQL(PersonPartTable.CREATE_TABLE);
        db.execSQL(SiteTable.CREATE_TABLE);
        db.execSQL(TaskActionTable.CREATE_TABLE);
        db.execSQL(TaskDetailTable.CREATE_TABLE);
        db.execSQL(ThingTable.CREATE_TABLE);
        db.execSQL(ToolTable.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //empty
    }
}
