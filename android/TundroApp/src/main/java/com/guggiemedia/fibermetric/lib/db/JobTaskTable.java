package com.guggiemedia.fibermetric.lib.db;

import android.net.Uri;
import android.provider.BaseColumns;


import com.guggiemedia.fibermetric.lib.Constant;

import java.util.HashMap;
import java.util.Set;

/**
 *
 */
public class JobTaskTable implements DataBaseTable {
    public static final String TABLE_NAME = "jobtask";

    public static final Uri CONTENT_URI = Uri.parse("content://" + Constant.AUTHORITY + "/" + TABLE_NAME);

    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.gofactory." + TABLE_NAME;

    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.gofactory." + TABLE_NAME;

    public static final String DEFAULT_SORT_ORDER = Columns.NAME + " ASC";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + Columns._ID + " INTEGER PRIMARY KEY,"
            + Columns.NAME + " TEXT NOT NULL,"
            + Columns.SITE + " INTEGER NOT NULL,"
            + Columns.PARENT + " INTEGER NOT NULL,"
            + Columns.ORDER_NDX + " INTEGER NOT NULL,"
            + Columns.JOB_FLAG + " INTEGER NOT NULL,"
            + Columns.JOB_STATE + " TEXT NOT NULL,"
            + Columns.JOB_PRIORITY + " TEXT NOT NULL,"
            + Columns.PROGRESS + " INTEGER,"
            + Columns.JOB_TYPE + " TEXT NOT NULL,"
            + Columns.TICKET + " TEXT NOT NULL,"
            + Columns.ASSIGNED_BY + " TEXT NOT NULL,"
            + Columns.DURATION + " TEXT NOT NULL,"
            + Columns.DURATION_MM + " INTEGER NOT NULL,"
            + Columns.DEADLINE + " TEXT NOT NULL,"
            + Columns.DEADLINE_MS + " INTEGER NOT NULL,"
            + Columns.ASSET + " TEXT NOT NULL,"
            + Columns.STAR_RATING + " INTEGER NOT NULL,"
            + Columns.COMMENT + " TEXT NOT NULL,"
            + Columns.UPDATE_TIME + " TEXT NOT NULL,"
            + Columns.UPDATE_TIME_MS + " INTEGER NOT NULL,"
            + Columns.DESCRIPTION + " TEXT NOT NULL"
            + ");";

    public static final class Columns implements BaseColumns {
        public static final String NAME = "name";
        public static final String SITE = "site";
        public static final String PARENT = "parent";
        public static final String ORDER_NDX = "order_ndx";
        public static final String JOB_FLAG = "job_flag";
        public static final String JOB_STATE = "job_state";
        public static final String JOB_PRIORITY = "task_priority";
        public static final String PROGRESS = "progress";
        public static final String JOB_TYPE = "job_type";
        public static final String TICKET = "ticket";
        public static final String ASSIGNED_BY = "assigned_by";
        public static final String DURATION = "duration";
        public static final String DURATION_MM = "duration_mm";
        public static final String DEADLINE = "deadline";
        public static final String DEADLINE_MS = "deadline_ms";
        public static final String ASSET = "asset";
        public static final String STAR_RATING = "star_rating";
        public static final String COMMENT = "comment";
        public static final String UPDATE_TIME = "update_time";
        public static final String UPDATE_TIME_MS = "update_time_ms";
        public static final String DESCRIPTION = "description";
    }

    //
    public static HashMap<String, String> PROJECTION_MAP = new HashMap<>();

    static {
        PROJECTION_MAP.put(JobTaskTable.Columns._ID, JobTaskTable.Columns._ID);
        PROJECTION_MAP.put(JobTaskTable.Columns.NAME, JobTaskTable.Columns.NAME);
        PROJECTION_MAP.put(JobTaskTable.Columns.SITE, JobTaskTable.Columns.SITE);
        PROJECTION_MAP.put(JobTaskTable.Columns.PARENT, JobTaskTable.Columns.PARENT);
        PROJECTION_MAP.put(JobTaskTable.Columns.ORDER_NDX, JobTaskTable.Columns.ORDER_NDX);
        PROJECTION_MAP.put(JobTaskTable.Columns.JOB_FLAG, JobTaskTable.Columns.JOB_FLAG);
        PROJECTION_MAP.put(JobTaskTable.Columns.JOB_STATE, JobTaskTable.Columns.JOB_STATE);
        PROJECTION_MAP.put(JobTaskTable.Columns.JOB_PRIORITY, JobTaskTable.Columns.JOB_PRIORITY);
        PROJECTION_MAP.put(JobTaskTable.Columns.PROGRESS, JobTaskTable.Columns.PROGRESS);
        PROJECTION_MAP.put(JobTaskTable.Columns.JOB_TYPE, JobTaskTable.Columns.JOB_TYPE);
        PROJECTION_MAP.put(JobTaskTable.Columns.TICKET, JobTaskTable.Columns.TICKET);
        PROJECTION_MAP.put(JobTaskTable.Columns.ASSIGNED_BY, JobTaskTable.Columns.ASSIGNED_BY);
        PROJECTION_MAP.put(JobTaskTable.Columns.DURATION, JobTaskTable.Columns.DURATION);
        PROJECTION_MAP.put(JobTaskTable.Columns.DURATION_MM, JobTaskTable.Columns.DURATION_MM);
        PROJECTION_MAP.put(JobTaskTable.Columns.DEADLINE, JobTaskTable.Columns.DEADLINE);
        PROJECTION_MAP.put(JobTaskTable.Columns.DEADLINE_MS, JobTaskTable.Columns.DEADLINE_MS);
        PROJECTION_MAP.put(JobTaskTable.Columns.ASSET, JobTaskTable.Columns.ASSET);
        PROJECTION_MAP.put(JobTaskTable.Columns.STAR_RATING, JobTaskTable.Columns.STAR_RATING);
        PROJECTION_MAP.put(JobTaskTable.Columns.COMMENT, JobTaskTable.Columns.COMMENT);
        PROJECTION_MAP.put(JobTaskTable.Columns.UPDATE_TIME, JobTaskTable.Columns.UPDATE_TIME);
        PROJECTION_MAP.put(JobTaskTable.Columns.UPDATE_TIME_MS, JobTaskTable.Columns.UPDATE_TIME_MS);
        PROJECTION_MAP.put(JobTaskTable.Columns.DESCRIPTION, JobTaskTable.Columns.DESCRIPTION);
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public String getDefaultSortOrder() {
        return DEFAULT_SORT_ORDER;
    }

    @Override
    public String[] getDefaultProjection() {
        Set<String> keySet = JobTaskTable.PROJECTION_MAP.keySet();
        return keySet.toArray(new String[keySet.size()]);
    }
}