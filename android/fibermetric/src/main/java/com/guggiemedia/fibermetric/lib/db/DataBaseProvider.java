package com.guggiemedia.fibermetric.lib.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import com.guggiemedia.fibermetric.lib.Constant;

public class DataBaseProvider extends ContentProvider {
    public static final String LOG_TAG = DataBaseProvider.class.getName();

    //URI Matcher Targets
    private static final int URI_MATCH_ALERT = 10;
    private static final int URI_MATCH_ALERT_ID = 11;

    private static final int URI_MATCH_CHAIN_OF_CUSTODY = 14;
    private static final int URI_MATCH_CHAIN_OF_CUSTODY_ID = 15;
    private static final int URI_MATCH_CHAT = 16;
    private static final int URI_MATCH_CHAT_ID = 17;
    private static final int URI_MATCH_CHAT_MSG = 18;
    private static final int URI_MATCH_CHAT_MSG_ID = 19;

    private static final int URI_MATCH_EVENT = 20;
    private static final int URI_MATCH_EVENT_ID = 21;

    private static final int URI_MATCH_IMAGE = 22;
    private static final int URI_MATCH_IMAGE_ID = 23;

    private static final int URI_MATCH_JOB_PART = 24;
    private static final int URI_MATCH_JOB_PART_ID = 25;
    private static final int URI_MATCH_JOBTASK = 26;
    private static final int URI_MATCH_JOBTASK_ID = 27;

    private static final int URI_MATCH_PART = 28;
    private static final int URI_MATCH_PART_ID = 29;
    private static final int URI_MATCH_PART_PERSONID = 30;
    private static final int URI_MATCH_PART_JOB_ID = 31;

    private static final int URI_MATCH_PERSON = 32;
    private static final int URI_MATCH_PERSON_ID = 33;

    private static final int URI_MATCH_PERSON_PART = 34;
    private static final int URI_MATCH_PERSON_PART_ID = 35;

    private static final int URI_MATCH_SITE = 36;
    private static final int URI_MATCH_SITE_ID = 37;

    private static final int URI_MATCH_TASK_ACTION = 38;
    private static final int URI_MATCH_TASK_ACTION_ID = 39;

    private static final int URI_MATCH_TASK_DETAIL = 40;
    private static final int URI_MATCH_TASK_DETAIL_ID = 41;

    private static final int URI_MATCH_THING = 50;
    private static final int URI_MATCH_THING_ID = 51;
    private static final int URI_MATCH_TOOL = 52;
    private static final int URI_MATCH_TOOL_ID = 53;

    private static final UriMatcher URI_MATCHER;

    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

        URI_MATCHER.addURI(Constant.AUTHORITY, AlertTable.TABLE_NAME, URI_MATCH_ALERT);
        URI_MATCHER.addURI(Constant.AUTHORITY, AlertTable.TABLE_NAME + "/#", URI_MATCH_ALERT_ID);

        URI_MATCHER.addURI(Constant.AUTHORITY, ChainOfCustodyTable.TABLE_NAME, URI_MATCH_CHAIN_OF_CUSTODY);
        URI_MATCHER.addURI(Constant.AUTHORITY, ChainOfCustodyTable.TABLE_NAME + "/#", URI_MATCH_CHAIN_OF_CUSTODY_ID);

        URI_MATCHER.addURI(Constant.AUTHORITY, ChatTable.TABLE_NAME, URI_MATCH_CHAT);
        URI_MATCHER.addURI(Constant.AUTHORITY, ChatTable.TABLE_NAME + "/#", URI_MATCH_CHAT_ID);

        URI_MATCHER.addURI(Constant.AUTHORITY, ChatMessageTable.TABLE_NAME, URI_MATCH_CHAT_MSG);
        URI_MATCHER.addURI(Constant.AUTHORITY, ChatMessageTable.TABLE_NAME + "/#", URI_MATCH_CHAT_MSG_ID);

        URI_MATCHER.addURI(Constant.AUTHORITY, EventTable.TABLE_NAME, URI_MATCH_EVENT);
        URI_MATCHER.addURI(Constant.AUTHORITY, EventTable.TABLE_NAME + "/#", URI_MATCH_EVENT_ID);

        URI_MATCHER.addURI(Constant.AUTHORITY, ImageTable.TABLE_NAME, URI_MATCH_IMAGE);
        URI_MATCHER.addURI(Constant.AUTHORITY, ImageTable.TABLE_NAME + "/#", URI_MATCH_IMAGE_ID);

        URI_MATCHER.addURI(Constant.AUTHORITY, com.guggiemedia.fibermetric.lib.db.JobPartTable.TABLE_NAME, URI_MATCH_JOB_PART);
        URI_MATCHER.addURI(Constant.AUTHORITY, com.guggiemedia.fibermetric.lib.db.JobPartTable.TABLE_NAME + "/#", URI_MATCH_JOB_PART_ID);

        URI_MATCHER.addURI(Constant.AUTHORITY, JobTaskTable.TABLE_NAME, URI_MATCH_JOBTASK);
        URI_MATCHER.addURI(Constant.AUTHORITY, JobTaskTable.TABLE_NAME + "/#", URI_MATCH_JOBTASK_ID);

        URI_MATCHER.addURI(Constant.AUTHORITY, PartTable.TABLE_NAME, URI_MATCH_PART);
        URI_MATCHER.addURI(Constant.AUTHORITY, PartTable.TABLE_NAME + "/#", URI_MATCH_PART_ID);
        URI_MATCHER.addURI(Constant.AUTHORITY, PartTable.TABLE_NAME + "/by_person", URI_MATCH_PART_PERSONID);
        URI_MATCHER.addURI(Constant.AUTHORITY, PartTable.TABLE_NAME + "/by_job", URI_MATCH_PART_JOB_ID);



        URI_MATCHER.addURI(Constant.AUTHORITY, com.guggiemedia.fibermetric.lib.db.SiteTable.TABLE_NAME, URI_MATCH_SITE);
        URI_MATCHER.addURI(Constant.AUTHORITY, com.guggiemedia.fibermetric.lib.db.SiteTable.TABLE_NAME + "/#", URI_MATCH_SITE_ID);


    }

    private DataBaseHelper _dbHelper;

    public DataBaseProvider() {
        //empty
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        String id = "";

        SQLiteDatabase db = _dbHelper.getWritableDatabase();

        switch (URI_MATCHER.match(uri)) {
            case URI_MATCH_ALERT:
                count = db.delete(AlertTable.TABLE_NAME, selection, selectionArgs);
                break;
            case URI_MATCH_ALERT_ID:
                id = uri.getPathSegments().get(1);
                count = db.delete(AlertTable.TABLE_NAME, AlertTable.Columns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            case URI_MATCH_CHAIN_OF_CUSTODY:
                count = db.delete(ChainOfCustodyTable.TABLE_NAME, selection, selectionArgs);
                break;
            case URI_MATCH_CHAIN_OF_CUSTODY_ID:
                id = uri.getPathSegments().get(1);
                count = db.delete(ChainOfCustodyTable.TABLE_NAME, ChainOfCustodyTable.Columns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            case URI_MATCH_CHAT:
                count = db.delete(ChatTable.TABLE_NAME, selection, selectionArgs);
                break;
            case URI_MATCH_CHAT_ID:
                id = uri.getPathSegments().get(1);
                count = db.delete(ChatTable.TABLE_NAME, ChatTable.Columns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            case URI_MATCH_CHAT_MSG:
                count = db.delete(ChatMessageTable.TABLE_NAME, selection, selectionArgs);
                break;
            case URI_MATCH_CHAT_MSG_ID:
                id = uri.getPathSegments().get(1);
                count = db.delete(ChatMessageTable.TABLE_NAME, ChatMessageTable.Columns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            case URI_MATCH_EVENT:
                count = db.delete(EventTable.TABLE_NAME, selection, selectionArgs);
                break;
            case URI_MATCH_EVENT_ID:
                id = uri.getPathSegments().get(1);
                count = db.delete(EventTable.TABLE_NAME, EventTable.Columns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            case URI_MATCH_IMAGE:
                count = db.delete(ImageTable.TABLE_NAME, selection, selectionArgs);
                break;
            case URI_MATCH_IMAGE_ID:
                id = uri.getPathSegments().get(1);
                count = db.delete(ImageTable.TABLE_NAME, ImageTable.Columns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            case URI_MATCH_JOB_PART:
                count = db.delete(com.guggiemedia.fibermetric.lib.db.JobPartTable.TABLE_NAME, selection, selectionArgs);
                break;
            case URI_MATCH_JOB_PART_ID:
                id = uri.getPathSegments().get(1);
                count = db.delete(com.guggiemedia.fibermetric.lib.db.JobPartTable.TABLE_NAME, com.guggiemedia.fibermetric.lib.db.JobPartTable.Columns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            case URI_MATCH_JOBTASK:
                count = db.delete(JobTaskTable.TABLE_NAME, selection, selectionArgs);
                break;
            case URI_MATCH_JOBTASK_ID:
                id = uri.getPathSegments().get(1);
                count = db.delete(JobTaskTable.TABLE_NAME, JobTaskTable.Columns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            case URI_MATCH_PART:
                count = db.delete(PartTable.TABLE_NAME, selection, selectionArgs);
                break;
            case URI_MATCH_PART_ID:
                id = uri.getPathSegments().get(1);
                count = db.delete(PartTable.TABLE_NAME, PartTable.Columns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;

            case URI_MATCH_SITE:
                count = db.delete(com.guggiemedia.fibermetric.lib.db.SiteTable.TABLE_NAME, selection, selectionArgs);
                break;
            case URI_MATCH_SITE_ID:
                id = uri.getPathSegments().get(1);
                count = db.delete(com.guggiemedia.fibermetric.lib.db.SiteTable.TABLE_NAME, com.guggiemedia.fibermetric.lib.db.SiteTable.Columns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;


            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public String getType(Uri uri) {
        switch (URI_MATCHER.match(uri)) {
            case URI_MATCH_ALERT:
                return AlertTable.CONTENT_TYPE;
            case URI_MATCH_ALERT_ID:
                return AlertTable.CONTENT_ITEM_TYPE;
            case URI_MATCH_CHAIN_OF_CUSTODY:
                return ChainOfCustodyTable.CONTENT_TYPE;
            case URI_MATCH_CHAIN_OF_CUSTODY_ID:
                return ChainOfCustodyTable.CONTENT_ITEM_TYPE;
            case URI_MATCH_CHAT:
                return ChatTable.CONTENT_TYPE;
            case URI_MATCH_CHAT_ID:
                return ChatTable.CONTENT_ITEM_TYPE;
            case URI_MATCH_CHAT_MSG:
                return ChatMessageTable.CONTENT_TYPE;
            case URI_MATCH_CHAT_MSG_ID:
                return ChatMessageTable.CONTENT_ITEM_TYPE;
            case URI_MATCH_EVENT:
                return EventTable.CONTENT_TYPE;
            case URI_MATCH_EVENT_ID:
                return EventTable.CONTENT_ITEM_TYPE;
            case URI_MATCH_IMAGE:
                return ImageTable.CONTENT_TYPE;
            case URI_MATCH_IMAGE_ID:
                return ImageTable.CONTENT_ITEM_TYPE;
            case URI_MATCH_JOB_PART:
                return com.guggiemedia.fibermetric.lib.db.JobPartTable.CONTENT_TYPE;
            case URI_MATCH_JOB_PART_ID:
                return com.guggiemedia.fibermetric.lib.db.JobPartTable.CONTENT_ITEM_TYPE;
            case URI_MATCH_JOBTASK:
                return JobTaskTable.CONTENT_TYPE;
            case URI_MATCH_JOBTASK_ID:
                return JobTaskTable.CONTENT_ITEM_TYPE;
            case URI_MATCH_PART:
                return PartTable.CONTENT_TYPE;
            case URI_MATCH_PART_ID:
                return PartTable.CONTENT_ITEM_TYPE;

            case URI_MATCH_PART_PERSONID:
                return PartTable.CONTENT_TYPE;
            case URI_MATCH_PART_JOB_ID:
                return PartTable.CONTENT_TYPE;

            case URI_MATCH_SITE:
                return com.guggiemedia.fibermetric.lib.db.SiteTable.CONTENT_TYPE;
            case URI_MATCH_SITE_ID:
                return com.guggiemedia.fibermetric.lib.db.SiteTable.CONTENT_ITEM_TYPE;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowId = 0;
        SQLiteDatabase db = _dbHelper.getWritableDatabase();

        switch (URI_MATCHER.match(uri)) {
            case URI_MATCH_ALERT:
                rowId = db.insert(AlertTable.TABLE_NAME, null, values);
                if (rowId > 0) {
                    Uri result = ContentUris.withAppendedId(AlertTable.CONTENT_URI, rowId);
                    getContext().getContentResolver().notifyChange(AlertTable.CONTENT_URI, null);
                    return result;
                }
                break;
            case URI_MATCH_CHAIN_OF_CUSTODY:
                rowId = db.insert(ChainOfCustodyTable.TABLE_NAME, null, values);
                if (rowId > 0) {
                    Uri result = ContentUris.withAppendedId(ChainOfCustodyTable.CONTENT_URI, rowId);
                    getContext().getContentResolver().notifyChange(ChainOfCustodyTable.CONTENT_URI, null);
                    return result;
                }
                break;
            case URI_MATCH_CHAT:
                rowId = db.insert(ChatTable.TABLE_NAME, null, values);
                if (rowId > 0) {
                    Uri result = ContentUris.withAppendedId(ChatTable.CONTENT_URI, rowId);
                    getContext().getContentResolver().notifyChange(ChatTable.CONTENT_URI, null);
                    return result;
                }
                break;
            case URI_MATCH_CHAT_MSG:
                rowId = db.insert(ChatMessageTable.TABLE_NAME, null, values);
                if (rowId > 0) {
                    Uri result = ContentUris.withAppendedId(ChatMessageTable.CONTENT_URI, rowId);
                    getContext().getContentResolver().notifyChange(ChatMessageTable.CONTENT_URI, null);
                    return result;
                }
                break;
            case URI_MATCH_EVENT:
                rowId = db.insert(EventTable.TABLE_NAME, null, values);
                if (rowId > 0) {
                    Uri result = ContentUris.withAppendedId(EventTable.CONTENT_URI, rowId);
                    getContext().getContentResolver().notifyChange(EventTable.CONTENT_URI, null);
                    return result;
                }
                break;
            case URI_MATCH_IMAGE:
                rowId = db.insert(ImageTable.TABLE_NAME, null, values);
                if (rowId > 0) {
                    Uri result = ContentUris.withAppendedId(ImageTable.CONTENT_URI, rowId);
                    getContext().getContentResolver().notifyChange(ImageTable.CONTENT_URI, null);
                    return result;
                }
                break;
            case URI_MATCH_JOB_PART:
                rowId = db.insert(com.guggiemedia.fibermetric.lib.db.JobPartTable.TABLE_NAME, null, values);
                if (rowId > 0) {
                    Uri result = ContentUris.withAppendedId(com.guggiemedia.fibermetric.lib.db.JobPartTable.CONTENT_URI, rowId);
                    getContext().getContentResolver().notifyChange(com.guggiemedia.fibermetric.lib.db.JobPartTable.CONTENT_URI, null);
                    return result;
                }
                break;
            case URI_MATCH_JOBTASK:
                rowId = db.insert(JobTaskTable.TABLE_NAME, null, values);
                if (rowId > 0) {
                    Uri result = ContentUris.withAppendedId(JobTaskTable.CONTENT_URI, rowId);
                    getContext().getContentResolver().notifyChange(JobTaskTable.CONTENT_URI, null);
                    return result;
                }
                break;
            case URI_MATCH_PART:
                rowId = db.insert(PartTable.TABLE_NAME, null, values);
                if (rowId > 0) {
                    Uri result = ContentUris.withAppendedId(PartTable.CONTENT_URI, rowId);
                    getContext().getContentResolver().notifyChange(PartTable.CONTENT_URI, null);
                    return result;
                }
                break;

            case URI_MATCH_SITE:
                rowId = db.insert(com.guggiemedia.fibermetric.lib.db.SiteTable.TABLE_NAME, null, values);
                if (rowId > 0) {
                    Uri result = ContentUris.withAppendedId(com.guggiemedia.fibermetric.lib.db.SiteTable.CONTENT_URI, rowId);
                    getContext().getContentResolver().notifyChange(com.guggiemedia.fibermetric.lib.db.SiteTable.CONTENT_URI, null);
                    return result;
                }
                break;


            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        throw new SQLException("insert failure:" + uri);
    }

    @Override
    public boolean onCreate() {
        _dbHelper = new DataBaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String orderBy = sortOrder;

        switch (URI_MATCHER.match(uri)) {
            case URI_MATCH_ALERT:
                qb.setTables(AlertTable.TABLE_NAME);
                qb.setProjectionMap(AlertTable.PROJECTION_MAP);
                if (sortOrder == null) {
                    orderBy = AlertTable.DEFAULT_SORT_ORDER;
                }
                break;
            case URI_MATCH_ALERT_ID:
                qb.setTables(AlertTable.TABLE_NAME);
                qb.setProjectionMap(AlertTable.PROJECTION_MAP);
                qb.appendWhere(AlertTable.Columns._ID + "=" + uri.getPathSegments().get(1));
                if (sortOrder == null) {
                    orderBy = AlertTable.DEFAULT_SORT_ORDER;
                }
                break;
            case URI_MATCH_CHAIN_OF_CUSTODY:
                qb.setTables(ChainOfCustodyTable.TABLE_NAME);
                qb.setProjectionMap(ChainOfCustodyTable.PROJECTION_MAP);
                if (sortOrder == null) {
                    orderBy = ChainOfCustodyTable.DEFAULT_SORT_ORDER;
                }
                break;
            case URI_MATCH_CHAIN_OF_CUSTODY_ID:
                qb.setTables(ChainOfCustodyTable.TABLE_NAME);
                qb.setProjectionMap(ChainOfCustodyTable.PROJECTION_MAP);
                qb.appendWhere(ChainOfCustodyTable.Columns._ID + "=" + uri.getPathSegments().get(1));
                if (sortOrder == null) {
                    orderBy = ChainOfCustodyTable.DEFAULT_SORT_ORDER;
                }
                break;
            case URI_MATCH_CHAT:
                qb.setTables(ChatTable.TABLE_NAME);
                qb.setProjectionMap(ChatTable.PROJECTION_MAP);
                if (sortOrder == null) {
                    orderBy = ChatTable.DEFAULT_SORT_ORDER;
                }
                break;
            case URI_MATCH_CHAT_ID:
                qb.setTables(ChatTable.TABLE_NAME);
                qb.setProjectionMap(ChatTable.PROJECTION_MAP);
                qb.appendWhere(ChatTable.Columns._ID + "=" + uri.getPathSegments().get(1));
                if (sortOrder == null) {
                    orderBy = ChatTable.DEFAULT_SORT_ORDER;
                }
                break;
            case URI_MATCH_CHAT_MSG:
                qb.setTables(ChatMessageTable.TABLE_JOIN_CHAT_TABLE);
                if (sortOrder == null) {
                    orderBy = PartTable.DEFAULT_SORT_ORDER;
                }
                break;
            case URI_MATCH_CHAT_MSG_ID:
                qb.setTables(ChatMessageTable.TABLE_NAME);
                qb.setProjectionMap(ChatMessageTable.PROJECTION_MAP);
                qb.appendWhere(ChatMessageTable.Columns._ID + "=" + uri.getPathSegments().get(1));
                if (sortOrder == null) {
                    orderBy = ChatMessageTable.DEFAULT_SORT_ORDER;
                }
                break;
            case URI_MATCH_EVENT:
                qb.setTables(EventTable.TABLE_NAME);
                qb.setProjectionMap(EventTable.PROJECTION_MAP);
                if (sortOrder == null) {
                    orderBy = EventTable.DEFAULT_SORT_ORDER;
                }
                break;
            case URI_MATCH_EVENT_ID:
                qb.setTables(EventTable.TABLE_NAME);
                qb.setProjectionMap(EventTable.PROJECTION_MAP);
                qb.appendWhere(EventTable.Columns._ID + "=" + uri.getPathSegments().get(1));
                if (sortOrder == null) {
                    orderBy = EventTable.DEFAULT_SORT_ORDER;
                }
                break;
            case URI_MATCH_IMAGE:
                qb.setTables(ImageTable.TABLE_NAME);
                qb.setProjectionMap(ImageTable.PROJECTION_MAP);
                if (sortOrder == null) {
                    orderBy = ImageTable.DEFAULT_SORT_ORDER;
                }
                break;
            case URI_MATCH_IMAGE_ID:
                qb.setTables(ImageTable.TABLE_NAME);
                qb.setProjectionMap(ImageTable.PROJECTION_MAP);
                qb.appendWhere(ImageTable.Columns._ID + "=" + uri.getPathSegments().get(1));
                if (sortOrder == null) {
                    orderBy = ImageTable.DEFAULT_SORT_ORDER;
                }
                break;
            case URI_MATCH_JOB_PART:
                qb.setTables(com.guggiemedia.fibermetric.lib.db.JobPartTable.TABLE_NAME);
                qb.setProjectionMap(com.guggiemedia.fibermetric.lib.db.JobPartTable.PROJECTION_MAP);
                if (sortOrder == null) {
                    orderBy = com.guggiemedia.fibermetric.lib.db.JobPartTable.DEFAULT_SORT_ORDER;
                }
                break;
            case URI_MATCH_JOB_PART_ID:
                qb.setTables(com.guggiemedia.fibermetric.lib.db.JobPartTable.TABLE_NAME);
                qb.setProjectionMap(com.guggiemedia.fibermetric.lib.db.JobPartTable.PROJECTION_MAP);
                qb.appendWhere(com.guggiemedia.fibermetric.lib.db.JobPartTable.Columns._ID + "=" + uri.getPathSegments().get(1));
                if (sortOrder == null) {
                    orderBy = com.guggiemedia.fibermetric.lib.db.JobPartTable.DEFAULT_SORT_ORDER;
                }
                break;
            case URI_MATCH_JOBTASK:
                qb.setTables(JobTaskTable.TABLE_NAME);
                qb.setProjectionMap(JobTaskTable.PROJECTION_MAP);
                if (sortOrder == null) {
                    orderBy = JobTaskTable.DEFAULT_SORT_ORDER;
                }
                break;
            case URI_MATCH_JOBTASK_ID:
                qb.setTables(JobTaskTable.TABLE_NAME);
                qb.setProjectionMap(JobTaskTable.PROJECTION_MAP);
                qb.appendWhere(JobTaskTable.Columns._ID + "=" + uri.getPathSegments().get(1));
                if (sortOrder == null) {
                    orderBy = JobTaskTable.DEFAULT_SORT_ORDER;
                }
                break;
            case URI_MATCH_PART:
                qb.setTables(PartTable.TABLE_NAME);
                qb.setProjectionMap(PartTable.PROJECTION_MAP);
                if (sortOrder == null) {
                    orderBy = PartTable.DEFAULT_SORT_ORDER;
                }
                break;
            case URI_MATCH_PART_ID:
                qb.setTables(PartTable.TABLE_NAME);
                qb.setProjectionMap(PartTable.PROJECTION_MAP);
                qb.appendWhere(PartTable.Columns._ID + "=" + uri.getPathSegments().get(1));
                if (sortOrder == null) {
                    orderBy = PartTable.DEFAULT_SORT_ORDER;
                }
                break;

            case URI_MATCH_PART_JOB_ID:
                qb.setTables(PartTable.TABLE_JOIN_PART_JOB_TABLE);
                qb.setDistinct (true);

                if (sortOrder == null) {
                    orderBy = PartTable.DEFAULT_SORT_ORDER;
                }
                break;

            case URI_MATCH_SITE:
                qb.setTables(com.guggiemedia.fibermetric.lib.db.SiteTable.TABLE_NAME);
                qb.setProjectionMap(com.guggiemedia.fibermetric.lib.db.SiteTable.PROJECTION_MAP);
                if (sortOrder == null) {
                    orderBy = com.guggiemedia.fibermetric.lib.db.SiteTable.DEFAULT_SORT_ORDER;
                }
                break;
            case URI_MATCH_SITE_ID:
                qb.setTables(com.guggiemedia.fibermetric.lib.db.SiteTable.TABLE_NAME);
                qb.setProjectionMap(com.guggiemedia.fibermetric.lib.db.SiteTable.PROJECTION_MAP);
                qb.appendWhere(com.guggiemedia.fibermetric.lib.db.SiteTable.Columns._ID + "=" + uri.getPathSegments().get(1));
                if (sortOrder == null) {
                    orderBy = com.guggiemedia.fibermetric.lib.db.SiteTable.DEFAULT_SORT_ORDER;
                }
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        SQLiteDatabase db = _dbHelper.getReadableDatabase();
        Cursor cursor = qb.query(db, projection, selection, selectionArgs, null, null, orderBy);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count = 0;
        String id = "";
        SQLiteDatabase db = _dbHelper.getWritableDatabase();

        switch (URI_MATCHER.match(uri)) {
            case URI_MATCH_ALERT:
                count = db.update(AlertTable.TABLE_NAME, values, selection, selectionArgs);
                break;
            case URI_MATCH_ALERT_ID:
                id = uri.getPathSegments().get(1);
                count = db.update(AlertTable.TABLE_NAME, values, AlertTable.Columns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            case URI_MATCH_CHAIN_OF_CUSTODY:
                count = db.update(ChainOfCustodyTable.TABLE_NAME, values, selection, selectionArgs);
                break;
            case URI_MATCH_CHAIN_OF_CUSTODY_ID:
                id = uri.getPathSegments().get(1);
                count = db.update(ChainOfCustodyTable.TABLE_NAME, values, ChainOfCustodyTable.Columns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            case URI_MATCH_CHAT:
                count = db.update(ChatTable.TABLE_NAME, values, selection, selectionArgs);
                break;
            case URI_MATCH_CHAT_ID:
                id = uri.getPathSegments().get(1);
                count = db.update(ChatTable.TABLE_NAME, values, ChatTable.Columns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            case URI_MATCH_CHAT_MSG:
                count = db.update(ChatMessageTable.TABLE_NAME, values, selection, selectionArgs);
                break;
            case URI_MATCH_CHAT_MSG_ID:
                id = uri.getPathSegments().get(1);
                count = db.update(ChatMessageTable.TABLE_NAME, values, ChatMessageTable.Columns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            case URI_MATCH_EVENT:
                count = db.update(EventTable.TABLE_NAME, values, selection, selectionArgs);
                break;
            case URI_MATCH_EVENT_ID:
                id = uri.getPathSegments().get(1);
                count = db.update(EventTable.TABLE_NAME, values, EventTable.Columns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            case URI_MATCH_IMAGE:
                count = db.update(ImageTable.TABLE_NAME, values, selection, selectionArgs);
                break;
            case URI_MATCH_IMAGE_ID:
                id = uri.getPathSegments().get(1);
                count = db.update(ImageTable.TABLE_NAME, values, ImageTable.Columns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            case URI_MATCH_JOB_PART:
                count = db.update(com.guggiemedia.fibermetric.lib.db.JobPartTable.TABLE_NAME, values, selection, selectionArgs);
                break;
            case URI_MATCH_JOB_PART_ID:
                id = uri.getPathSegments().get(1);
                count = db.update(com.guggiemedia.fibermetric.lib.db.JobPartTable.TABLE_NAME, values, com.guggiemedia.fibermetric.lib.db.JobPartTable.Columns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            case URI_MATCH_JOBTASK:
                count = db.update(JobTaskTable.TABLE_NAME, values, selection, selectionArgs);
                break;
            case URI_MATCH_JOBTASK_ID:
                id = uri.getPathSegments().get(1);
                count = db.update(JobTaskTable.TABLE_NAME, values, JobTaskTable.Columns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            case URI_MATCH_PART:
                count = db.update(PartTable.TABLE_NAME, values, selection, selectionArgs);
                break;
            case URI_MATCH_PART_ID:
                id = uri.getPathSegments().get(1);
                count = db.update(PartTable.TABLE_NAME, values, PartTable.Columns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;

            case URI_MATCH_SITE:
                count = db.update(com.guggiemedia.fibermetric.lib.db.SiteTable.TABLE_NAME, values, selection, selectionArgs);
                break;
            case URI_MATCH_SITE_ID:
                id = uri.getPathSegments().get(1);
                count = db.update(com.guggiemedia.fibermetric.lib.db.SiteTable.TABLE_NAME, values, com.guggiemedia.fibermetric.lib.db.SiteTable.Columns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;


            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}