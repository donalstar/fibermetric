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

        URI_MATCHER.addURI(Constant.AUTHORITY, com.guggiemedia.fibermetric.lib.db.AlertTable.TABLE_NAME, URI_MATCH_ALERT);
        URI_MATCHER.addURI(Constant.AUTHORITY, com.guggiemedia.fibermetric.lib.db.AlertTable.TABLE_NAME + "/#", URI_MATCH_ALERT_ID);

        URI_MATCHER.addURI(Constant.AUTHORITY, com.guggiemedia.fibermetric.lib.db.ChainOfCustodyTable.TABLE_NAME, URI_MATCH_CHAIN_OF_CUSTODY);
        URI_MATCHER.addURI(Constant.AUTHORITY, com.guggiemedia.fibermetric.lib.db.ChainOfCustodyTable.TABLE_NAME + "/#", URI_MATCH_CHAIN_OF_CUSTODY_ID);

        URI_MATCHER.addURI(Constant.AUTHORITY, com.guggiemedia.fibermetric.lib.db.ChatTable.TABLE_NAME, URI_MATCH_CHAT);
        URI_MATCHER.addURI(Constant.AUTHORITY, com.guggiemedia.fibermetric.lib.db.ChatTable.TABLE_NAME + "/#", URI_MATCH_CHAT_ID);

        URI_MATCHER.addURI(Constant.AUTHORITY, com.guggiemedia.fibermetric.lib.db.ChatMessageTable.TABLE_NAME, URI_MATCH_CHAT_MSG);
        URI_MATCHER.addURI(Constant.AUTHORITY, com.guggiemedia.fibermetric.lib.db.ChatMessageTable.TABLE_NAME + "/#", URI_MATCH_CHAT_MSG_ID);

        URI_MATCHER.addURI(Constant.AUTHORITY, com.guggiemedia.fibermetric.lib.db.EventTable.TABLE_NAME, URI_MATCH_EVENT);
        URI_MATCHER.addURI(Constant.AUTHORITY, com.guggiemedia.fibermetric.lib.db.EventTable.TABLE_NAME + "/#", URI_MATCH_EVENT_ID);

        URI_MATCHER.addURI(Constant.AUTHORITY, com.guggiemedia.fibermetric.lib.db.ImageTable.TABLE_NAME, URI_MATCH_IMAGE);
        URI_MATCHER.addURI(Constant.AUTHORITY, com.guggiemedia.fibermetric.lib.db.ImageTable.TABLE_NAME + "/#", URI_MATCH_IMAGE_ID);

        URI_MATCHER.addURI(Constant.AUTHORITY, com.guggiemedia.fibermetric.lib.db.JobPartTable.TABLE_NAME, URI_MATCH_JOB_PART);
        URI_MATCHER.addURI(Constant.AUTHORITY, com.guggiemedia.fibermetric.lib.db.JobPartTable.TABLE_NAME + "/#", URI_MATCH_JOB_PART_ID);

        URI_MATCHER.addURI(Constant.AUTHORITY, com.guggiemedia.fibermetric.lib.db.JobTaskTable.TABLE_NAME, URI_MATCH_JOBTASK);
        URI_MATCHER.addURI(Constant.AUTHORITY, com.guggiemedia.fibermetric.lib.db.JobTaskTable.TABLE_NAME + "/#", URI_MATCH_JOBTASK_ID);

        URI_MATCHER.addURI(Constant.AUTHORITY, com.guggiemedia.fibermetric.lib.db.PartTable.TABLE_NAME, URI_MATCH_PART);
        URI_MATCHER.addURI(Constant.AUTHORITY, com.guggiemedia.fibermetric.lib.db.PartTable.TABLE_NAME + "/#", URI_MATCH_PART_ID);
        URI_MATCHER.addURI(Constant.AUTHORITY, com.guggiemedia.fibermetric.lib.db.PartTable.TABLE_NAME + "/by_person", URI_MATCH_PART_PERSONID);
        URI_MATCHER.addURI(Constant.AUTHORITY, com.guggiemedia.fibermetric.lib.db.PartTable.TABLE_NAME + "/by_job", URI_MATCH_PART_JOB_ID);

        URI_MATCHER.addURI(Constant.AUTHORITY, com.guggiemedia.fibermetric.lib.db.PersonTable.TABLE_NAME, URI_MATCH_PERSON);
        URI_MATCHER.addURI(Constant.AUTHORITY, com.guggiemedia.fibermetric.lib.db.PersonTable.TABLE_NAME + "/#", URI_MATCH_PERSON_ID);

        URI_MATCHER.addURI(Constant.AUTHORITY, com.guggiemedia.fibermetric.lib.db.PersonPartTable.TABLE_NAME, URI_MATCH_PERSON_PART);
        URI_MATCHER.addURI(Constant.AUTHORITY, com.guggiemedia.fibermetric.lib.db.PersonPartTable.TABLE_NAME + "/#", URI_MATCH_PERSON_PART_ID);

        URI_MATCHER.addURI(Constant.AUTHORITY, com.guggiemedia.fibermetric.lib.db.SiteTable.TABLE_NAME, URI_MATCH_SITE);
        URI_MATCHER.addURI(Constant.AUTHORITY, com.guggiemedia.fibermetric.lib.db.SiteTable.TABLE_NAME + "/#", URI_MATCH_SITE_ID);

        URI_MATCHER.addURI(Constant.AUTHORITY, com.guggiemedia.fibermetric.lib.db.TaskActionTable.TABLE_NAME, URI_MATCH_TASK_ACTION);
        URI_MATCHER.addURI(Constant.AUTHORITY, com.guggiemedia.fibermetric.lib.db.TaskActionTable.TABLE_NAME + "/#", URI_MATCH_TASK_ACTION_ID);

        URI_MATCHER.addURI(Constant.AUTHORITY, com.guggiemedia.fibermetric.lib.db.TaskDetailTable.TABLE_NAME, URI_MATCH_TASK_DETAIL);
        URI_MATCHER.addURI(Constant.AUTHORITY, com.guggiemedia.fibermetric.lib.db.TaskDetailTable.TABLE_NAME + "/#", URI_MATCH_TASK_DETAIL_ID);

        URI_MATCHER.addURI(Constant.AUTHORITY, com.guggiemedia.fibermetric.lib.db.ThingTable.TABLE_NAME, URI_MATCH_THING);
        URI_MATCHER.addURI(Constant.AUTHORITY, com.guggiemedia.fibermetric.lib.db.ThingTable.TABLE_NAME + "/#", URI_MATCH_THING_ID);

        URI_MATCHER.addURI(Constant.AUTHORITY, com.guggiemedia.fibermetric.lib.db.ToolTable.TABLE_NAME, URI_MATCH_TOOL);
        URI_MATCHER.addURI(Constant.AUTHORITY, com.guggiemedia.fibermetric.lib.db.ToolTable.TABLE_NAME + "/#", URI_MATCH_TOOL_ID);
    }

    private com.guggiemedia.fibermetric.lib.db.DataBaseHelper _dbHelper;

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
                count = db.delete(com.guggiemedia.fibermetric.lib.db.AlertTable.TABLE_NAME, selection, selectionArgs);
                break;
            case URI_MATCH_ALERT_ID:
                id = uri.getPathSegments().get(1);
                count = db.delete(com.guggiemedia.fibermetric.lib.db.AlertTable.TABLE_NAME, com.guggiemedia.fibermetric.lib.db.AlertTable.Columns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            case URI_MATCH_CHAIN_OF_CUSTODY:
                count = db.delete(com.guggiemedia.fibermetric.lib.db.ChainOfCustodyTable.TABLE_NAME, selection, selectionArgs);
                break;
            case URI_MATCH_CHAIN_OF_CUSTODY_ID:
                id = uri.getPathSegments().get(1);
                count = db.delete(com.guggiemedia.fibermetric.lib.db.ChainOfCustodyTable.TABLE_NAME, com.guggiemedia.fibermetric.lib.db.ChainOfCustodyTable.Columns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            case URI_MATCH_CHAT:
                count = db.delete(com.guggiemedia.fibermetric.lib.db.ChatTable.TABLE_NAME, selection, selectionArgs);
                break;
            case URI_MATCH_CHAT_ID:
                id = uri.getPathSegments().get(1);
                count = db.delete(com.guggiemedia.fibermetric.lib.db.ChatTable.TABLE_NAME, com.guggiemedia.fibermetric.lib.db.ChatTable.Columns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            case URI_MATCH_CHAT_MSG:
                count = db.delete(com.guggiemedia.fibermetric.lib.db.ChatMessageTable.TABLE_NAME, selection, selectionArgs);
                break;
            case URI_MATCH_CHAT_MSG_ID:
                id = uri.getPathSegments().get(1);
                count = db.delete(com.guggiemedia.fibermetric.lib.db.ChatMessageTable.TABLE_NAME, com.guggiemedia.fibermetric.lib.db.ChatMessageTable.Columns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            case URI_MATCH_EVENT:
                count = db.delete(com.guggiemedia.fibermetric.lib.db.EventTable.TABLE_NAME, selection, selectionArgs);
                break;
            case URI_MATCH_EVENT_ID:
                id = uri.getPathSegments().get(1);
                count = db.delete(com.guggiemedia.fibermetric.lib.db.EventTable.TABLE_NAME, com.guggiemedia.fibermetric.lib.db.EventTable.Columns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            case URI_MATCH_IMAGE:
                count = db.delete(com.guggiemedia.fibermetric.lib.db.ImageTable.TABLE_NAME, selection, selectionArgs);
                break;
            case URI_MATCH_IMAGE_ID:
                id = uri.getPathSegments().get(1);
                count = db.delete(com.guggiemedia.fibermetric.lib.db.ImageTable.TABLE_NAME, com.guggiemedia.fibermetric.lib.db.ImageTable.Columns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            case URI_MATCH_JOB_PART:
                count = db.delete(com.guggiemedia.fibermetric.lib.db.JobPartTable.TABLE_NAME, selection, selectionArgs);
                break;
            case URI_MATCH_JOB_PART_ID:
                id = uri.getPathSegments().get(1);
                count = db.delete(com.guggiemedia.fibermetric.lib.db.JobPartTable.TABLE_NAME, com.guggiemedia.fibermetric.lib.db.JobPartTable.Columns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            case URI_MATCH_JOBTASK:
                count = db.delete(com.guggiemedia.fibermetric.lib.db.JobTaskTable.TABLE_NAME, selection, selectionArgs);
                break;
            case URI_MATCH_JOBTASK_ID:
                id = uri.getPathSegments().get(1);
                count = db.delete(com.guggiemedia.fibermetric.lib.db.JobTaskTable.TABLE_NAME, com.guggiemedia.fibermetric.lib.db.JobTaskTable.Columns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            case URI_MATCH_PART:
                count = db.delete(com.guggiemedia.fibermetric.lib.db.PartTable.TABLE_NAME, selection, selectionArgs);
                break;
            case URI_MATCH_PART_ID:
                id = uri.getPathSegments().get(1);
                count = db.delete(com.guggiemedia.fibermetric.lib.db.PartTable.TABLE_NAME, com.guggiemedia.fibermetric.lib.db.PartTable.Columns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            case URI_MATCH_PERSON:
                count = db.delete(com.guggiemedia.fibermetric.lib.db.PersonTable.TABLE_NAME, selection, selectionArgs);
                break;
            case URI_MATCH_PERSON_ID:
                id = uri.getPathSegments().get(1);
                count = db.delete(com.guggiemedia.fibermetric.lib.db.PersonTable.TABLE_NAME, com.guggiemedia.fibermetric.lib.db.PersonTable.Columns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            case URI_MATCH_PERSON_PART:
                count = db.delete(com.guggiemedia.fibermetric.lib.db.PersonPartTable.TABLE_NAME, selection, selectionArgs);
                break;
            case URI_MATCH_PERSON_PART_ID:
                id = uri.getPathSegments().get(1);
                count = db.delete(com.guggiemedia.fibermetric.lib.db.PersonPartTable.TABLE_NAME, com.guggiemedia.fibermetric.lib.db.PersonPartTable.Columns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            case URI_MATCH_SITE:
                count = db.delete(com.guggiemedia.fibermetric.lib.db.SiteTable.TABLE_NAME, selection, selectionArgs);
                break;
            case URI_MATCH_SITE_ID:
                id = uri.getPathSegments().get(1);
                count = db.delete(com.guggiemedia.fibermetric.lib.db.SiteTable.TABLE_NAME, com.guggiemedia.fibermetric.lib.db.SiteTable.Columns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            case URI_MATCH_TASK_ACTION:
                count = db.delete(com.guggiemedia.fibermetric.lib.db.TaskActionTable.TABLE_NAME, selection, selectionArgs);
                break;
            case URI_MATCH_TASK_ACTION_ID:
                id = uri.getPathSegments().get(1);
                count = db.delete(com.guggiemedia.fibermetric.lib.db.TaskActionTable.TABLE_NAME, com.guggiemedia.fibermetric.lib.db.TaskActionTable.Columns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            case URI_MATCH_TASK_DETAIL:
                count = db.delete(com.guggiemedia.fibermetric.lib.db.TaskDetailTable.TABLE_NAME, selection, selectionArgs);
                break;
            case URI_MATCH_TASK_DETAIL_ID:
                id = uri.getPathSegments().get(1);
                count = db.delete(com.guggiemedia.fibermetric.lib.db.TaskDetailTable.TABLE_NAME, com.guggiemedia.fibermetric.lib.db.TaskDetailTable.Columns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            case URI_MATCH_TOOL:
                count = db.delete(com.guggiemedia.fibermetric.lib.db.ToolTable.TABLE_NAME, selection, selectionArgs);
                break;
            case URI_MATCH_TOOL_ID:
                id = uri.getPathSegments().get(1);
                count = db.delete(com.guggiemedia.fibermetric.lib.db.ToolTable.TABLE_NAME, com.guggiemedia.fibermetric.lib.db.ToolTable.Columns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
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
                return com.guggiemedia.fibermetric.lib.db.AlertTable.CONTENT_TYPE;
            case URI_MATCH_ALERT_ID:
                return com.guggiemedia.fibermetric.lib.db.AlertTable.CONTENT_ITEM_TYPE;
            case URI_MATCH_CHAIN_OF_CUSTODY:
                return com.guggiemedia.fibermetric.lib.db.ChainOfCustodyTable.CONTENT_TYPE;
            case URI_MATCH_CHAIN_OF_CUSTODY_ID:
                return com.guggiemedia.fibermetric.lib.db.ChainOfCustodyTable.CONTENT_ITEM_TYPE;
            case URI_MATCH_CHAT:
                return com.guggiemedia.fibermetric.lib.db.ChatTable.CONTENT_TYPE;
            case URI_MATCH_CHAT_ID:
                return com.guggiemedia.fibermetric.lib.db.ChatTable.CONTENT_ITEM_TYPE;
            case URI_MATCH_CHAT_MSG:
                return com.guggiemedia.fibermetric.lib.db.ChatMessageTable.CONTENT_TYPE;
            case URI_MATCH_CHAT_MSG_ID:
                return com.guggiemedia.fibermetric.lib.db.ChatMessageTable.CONTENT_ITEM_TYPE;
            case URI_MATCH_EVENT:
                return com.guggiemedia.fibermetric.lib.db.EventTable.CONTENT_TYPE;
            case URI_MATCH_EVENT_ID:
                return com.guggiemedia.fibermetric.lib.db.EventTable.CONTENT_ITEM_TYPE;
            case URI_MATCH_IMAGE:
                return com.guggiemedia.fibermetric.lib.db.ImageTable.CONTENT_TYPE;
            case URI_MATCH_IMAGE_ID:
                return com.guggiemedia.fibermetric.lib.db.ImageTable.CONTENT_ITEM_TYPE;
            case URI_MATCH_JOB_PART:
                return com.guggiemedia.fibermetric.lib.db.JobPartTable.CONTENT_TYPE;
            case URI_MATCH_JOB_PART_ID:
                return com.guggiemedia.fibermetric.lib.db.JobPartTable.CONTENT_ITEM_TYPE;
            case URI_MATCH_JOBTASK:
                return com.guggiemedia.fibermetric.lib.db.JobTaskTable.CONTENT_TYPE;
            case URI_MATCH_JOBTASK_ID:
                return com.guggiemedia.fibermetric.lib.db.JobTaskTable.CONTENT_ITEM_TYPE;
            case URI_MATCH_PART:
                return com.guggiemedia.fibermetric.lib.db.PartTable.CONTENT_TYPE;
            case URI_MATCH_PART_ID:
                return com.guggiemedia.fibermetric.lib.db.PartTable.CONTENT_ITEM_TYPE;
            case URI_MATCH_PERSON:
                return com.guggiemedia.fibermetric.lib.db.PersonTable.CONTENT_TYPE;
            case URI_MATCH_PERSON_ID:
                return com.guggiemedia.fibermetric.lib.db.PersonTable.CONTENT_ITEM_TYPE;
            case URI_MATCH_PART_PERSONID:
                return com.guggiemedia.fibermetric.lib.db.PartTable.CONTENT_TYPE;
            case URI_MATCH_PART_JOB_ID:
                return com.guggiemedia.fibermetric.lib.db.PartTable.CONTENT_TYPE;
            case URI_MATCH_PERSON_PART:
                return com.guggiemedia.fibermetric.lib.db.PersonPartTable.CONTENT_TYPE;
            case URI_MATCH_PERSON_PART_ID:
                return com.guggiemedia.fibermetric.lib.db.PersonPartTable.CONTENT_ITEM_TYPE;
            case URI_MATCH_SITE:
                return com.guggiemedia.fibermetric.lib.db.SiteTable.CONTENT_TYPE;
            case URI_MATCH_SITE_ID:
                return com.guggiemedia.fibermetric.lib.db.SiteTable.CONTENT_ITEM_TYPE;
            case URI_MATCH_TASK_ACTION:
                return com.guggiemedia.fibermetric.lib.db.TaskActionTable.CONTENT_TYPE;
            case URI_MATCH_TASK_ACTION_ID:
                return com.guggiemedia.fibermetric.lib.db.TaskActionTable.CONTENT_ITEM_TYPE;
            case URI_MATCH_TASK_DETAIL:
                return com.guggiemedia.fibermetric.lib.db.TaskDetailTable.CONTENT_TYPE;
            case URI_MATCH_TASK_DETAIL_ID:
                return com.guggiemedia.fibermetric.lib.db.TaskDetailTable.CONTENT_ITEM_TYPE;
            case URI_MATCH_THING:
                return com.guggiemedia.fibermetric.lib.db.ThingTable.CONTENT_TYPE;
            case URI_MATCH_THING_ID:
                return com.guggiemedia.fibermetric.lib.db.ThingTable.CONTENT_ITEM_TYPE;
            case URI_MATCH_TOOL:
                return com.guggiemedia.fibermetric.lib.db.ToolTable.CONTENT_TYPE;
            case URI_MATCH_TOOL_ID:
                return com.guggiemedia.fibermetric.lib.db.ToolTable.CONTENT_ITEM_TYPE;
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
                rowId = db.insert(com.guggiemedia.fibermetric.lib.db.AlertTable.TABLE_NAME, null, values);
                if (rowId > 0) {
                    Uri result = ContentUris.withAppendedId(com.guggiemedia.fibermetric.lib.db.AlertTable.CONTENT_URI, rowId);
                    getContext().getContentResolver().notifyChange(com.guggiemedia.fibermetric.lib.db.AlertTable.CONTENT_URI, null);
                    return result;
                }
                break;
            case URI_MATCH_CHAIN_OF_CUSTODY:
                rowId = db.insert(com.guggiemedia.fibermetric.lib.db.ChainOfCustodyTable.TABLE_NAME, null, values);
                if (rowId > 0) {
                    Uri result = ContentUris.withAppendedId(com.guggiemedia.fibermetric.lib.db.ChainOfCustodyTable.CONTENT_URI, rowId);
                    getContext().getContentResolver().notifyChange(com.guggiemedia.fibermetric.lib.db.ChainOfCustodyTable.CONTENT_URI, null);
                    return result;
                }
                break;
            case URI_MATCH_CHAT:
                rowId = db.insert(com.guggiemedia.fibermetric.lib.db.ChatTable.TABLE_NAME, null, values);
                if (rowId > 0) {
                    Uri result = ContentUris.withAppendedId(com.guggiemedia.fibermetric.lib.db.ChatTable.CONTENT_URI, rowId);
                    getContext().getContentResolver().notifyChange(com.guggiemedia.fibermetric.lib.db.ChatTable.CONTENT_URI, null);
                    return result;
                }
                break;
            case URI_MATCH_CHAT_MSG:
                rowId = db.insert(com.guggiemedia.fibermetric.lib.db.ChatMessageTable.TABLE_NAME, null, values);
                if (rowId > 0) {
                    Uri result = ContentUris.withAppendedId(com.guggiemedia.fibermetric.lib.db.ChatMessageTable.CONTENT_URI, rowId);
                    getContext().getContentResolver().notifyChange(com.guggiemedia.fibermetric.lib.db.ChatMessageTable.CONTENT_URI, null);
                    return result;
                }
                break;
            case URI_MATCH_EVENT:
                rowId = db.insert(com.guggiemedia.fibermetric.lib.db.EventTable.TABLE_NAME, null, values);
                if (rowId > 0) {
                    Uri result = ContentUris.withAppendedId(com.guggiemedia.fibermetric.lib.db.EventTable.CONTENT_URI, rowId);
                    getContext().getContentResolver().notifyChange(com.guggiemedia.fibermetric.lib.db.EventTable.CONTENT_URI, null);
                    return result;
                }
                break;
            case URI_MATCH_IMAGE:
                rowId = db.insert(com.guggiemedia.fibermetric.lib.db.ImageTable.TABLE_NAME, null, values);
                if (rowId > 0) {
                    Uri result = ContentUris.withAppendedId(com.guggiemedia.fibermetric.lib.db.ImageTable.CONTENT_URI, rowId);
                    getContext().getContentResolver().notifyChange(com.guggiemedia.fibermetric.lib.db.ImageTable.CONTENT_URI, null);
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
                rowId = db.insert(com.guggiemedia.fibermetric.lib.db.JobTaskTable.TABLE_NAME, null, values);
                if (rowId > 0) {
                    Uri result = ContentUris.withAppendedId(com.guggiemedia.fibermetric.lib.db.JobTaskTable.CONTENT_URI, rowId);
                    getContext().getContentResolver().notifyChange(com.guggiemedia.fibermetric.lib.db.JobTaskTable.CONTENT_URI, null);
                    return result;
                }
                break;
            case URI_MATCH_PART:
                rowId = db.insert(com.guggiemedia.fibermetric.lib.db.PartTable.TABLE_NAME, null, values);
                if (rowId > 0) {
                    Uri result = ContentUris.withAppendedId(com.guggiemedia.fibermetric.lib.db.PartTable.CONTENT_URI, rowId);
                    getContext().getContentResolver().notifyChange(com.guggiemedia.fibermetric.lib.db.PartTable.CONTENT_URI, null);
                    return result;
                }
                break;
            case URI_MATCH_PERSON:
                rowId = db.insert(com.guggiemedia.fibermetric.lib.db.PersonTable.TABLE_NAME, null, values);
                if (rowId > 0) {
                    Uri result = ContentUris.withAppendedId(com.guggiemedia.fibermetric.lib.db.PersonTable.CONTENT_URI, rowId);
                    getContext().getContentResolver().notifyChange(com.guggiemedia.fibermetric.lib.db.PersonTable.CONTENT_URI, null);
                    return result;
                }
                break;
            case URI_MATCH_PERSON_PART:
                rowId = db.insert(com.guggiemedia.fibermetric.lib.db.PersonPartTable.TABLE_NAME, null, values);
                if (rowId > 0) {
                    Uri result = ContentUris.withAppendedId(com.guggiemedia.fibermetric.lib.db.PersonPartTable.CONTENT_URI, rowId);
                    getContext().getContentResolver().notifyChange(com.guggiemedia.fibermetric.lib.db.PersonPartTable.CONTENT_URI, null);
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
            case URI_MATCH_TASK_ACTION:
                rowId = db.insert(com.guggiemedia.fibermetric.lib.db.TaskActionTable.TABLE_NAME, null, values);
                if (rowId > 0) {
                    Uri result = ContentUris.withAppendedId(com.guggiemedia.fibermetric.lib.db.TaskActionTable.CONTENT_URI, rowId);
                    getContext().getContentResolver().notifyChange(com.guggiemedia.fibermetric.lib.db.TaskActionTable.CONTENT_URI, null);
                    return result;
                }
                break;
            case URI_MATCH_TASK_DETAIL:
                rowId = db.insert(com.guggiemedia.fibermetric.lib.db.TaskDetailTable.TABLE_NAME, null, values);
                if (rowId > 0) {
                    Uri result = ContentUris.withAppendedId(com.guggiemedia.fibermetric.lib.db.TaskDetailTable.CONTENT_URI, rowId);
                    getContext().getContentResolver().notifyChange(com.guggiemedia.fibermetric.lib.db.TaskDetailTable.CONTENT_URI, null);
                    return result;
                }
                break;
            case URI_MATCH_THING:
                rowId = db.insert(com.guggiemedia.fibermetric.lib.db.ThingTable.TABLE_NAME, null, values);
                if (rowId > 0) {
                    Uri result = ContentUris.withAppendedId(com.guggiemedia.fibermetric.lib.db.ThingTable.CONTENT_URI, rowId);
                    getContext().getContentResolver().notifyChange(com.guggiemedia.fibermetric.lib.db.ThingTable.CONTENT_URI, null);
                    return result;
                }
                break;
            case URI_MATCH_TOOL:
                rowId = db.insert(com.guggiemedia.fibermetric.lib.db.ToolTable.TABLE_NAME, null, values);
                if (rowId > 0) {
                    Uri result = ContentUris.withAppendedId(com.guggiemedia.fibermetric.lib.db.ToolTable.CONTENT_URI, rowId);
                    getContext().getContentResolver().notifyChange(com.guggiemedia.fibermetric.lib.db.ToolTable.CONTENT_URI, null);
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
        _dbHelper = new com.guggiemedia.fibermetric.lib.db.DataBaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String orderBy = sortOrder;

        switch (URI_MATCHER.match(uri)) {
            case URI_MATCH_ALERT:
                qb.setTables(com.guggiemedia.fibermetric.lib.db.AlertTable.TABLE_NAME);
                qb.setProjectionMap(com.guggiemedia.fibermetric.lib.db.AlertTable.PROJECTION_MAP);
                if (sortOrder == null) {
                    orderBy = com.guggiemedia.fibermetric.lib.db.AlertTable.DEFAULT_SORT_ORDER;
                }
                break;
            case URI_MATCH_ALERT_ID:
                qb.setTables(com.guggiemedia.fibermetric.lib.db.AlertTable.TABLE_NAME);
                qb.setProjectionMap(com.guggiemedia.fibermetric.lib.db.AlertTable.PROJECTION_MAP);
                qb.appendWhere(com.guggiemedia.fibermetric.lib.db.AlertTable.Columns._ID + "=" + uri.getPathSegments().get(1));
                if (sortOrder == null) {
                    orderBy = com.guggiemedia.fibermetric.lib.db.AlertTable.DEFAULT_SORT_ORDER;
                }
                break;
            case URI_MATCH_CHAIN_OF_CUSTODY:
                qb.setTables(com.guggiemedia.fibermetric.lib.db.ChainOfCustodyTable.TABLE_NAME);
                qb.setProjectionMap(com.guggiemedia.fibermetric.lib.db.ChainOfCustodyTable.PROJECTION_MAP);
                if (sortOrder == null) {
                    orderBy = com.guggiemedia.fibermetric.lib.db.ChainOfCustodyTable.DEFAULT_SORT_ORDER;
                }
                break;
            case URI_MATCH_CHAIN_OF_CUSTODY_ID:
                qb.setTables(com.guggiemedia.fibermetric.lib.db.ChainOfCustodyTable.TABLE_NAME);
                qb.setProjectionMap(com.guggiemedia.fibermetric.lib.db.ChainOfCustodyTable.PROJECTION_MAP);
                qb.appendWhere(com.guggiemedia.fibermetric.lib.db.ChainOfCustodyTable.Columns._ID + "=" + uri.getPathSegments().get(1));
                if (sortOrder == null) {
                    orderBy = com.guggiemedia.fibermetric.lib.db.ChainOfCustodyTable.DEFAULT_SORT_ORDER;
                }
                break;
            case URI_MATCH_CHAT:
                qb.setTables(com.guggiemedia.fibermetric.lib.db.ChatTable.TABLE_NAME);
                qb.setProjectionMap(com.guggiemedia.fibermetric.lib.db.ChatTable.PROJECTION_MAP);
                if (sortOrder == null) {
                    orderBy = com.guggiemedia.fibermetric.lib.db.ChatTable.DEFAULT_SORT_ORDER;
                }
                break;
            case URI_MATCH_CHAT_ID:
                qb.setTables(com.guggiemedia.fibermetric.lib.db.ChatTable.TABLE_NAME);
                qb.setProjectionMap(com.guggiemedia.fibermetric.lib.db.ChatTable.PROJECTION_MAP);
                qb.appendWhere(com.guggiemedia.fibermetric.lib.db.ChatTable.Columns._ID + "=" + uri.getPathSegments().get(1));
                if (sortOrder == null) {
                    orderBy = com.guggiemedia.fibermetric.lib.db.ChatTable.DEFAULT_SORT_ORDER;
                }
                break;
            case URI_MATCH_CHAT_MSG:
                qb.setTables(com.guggiemedia.fibermetric.lib.db.ChatMessageTable.TABLE_JOIN_CHAT_TABLE);
                if (sortOrder == null) {
                    orderBy = com.guggiemedia.fibermetric.lib.db.PartTable.DEFAULT_SORT_ORDER;
                }
                break;
            case URI_MATCH_CHAT_MSG_ID:
                qb.setTables(com.guggiemedia.fibermetric.lib.db.ChatMessageTable.TABLE_NAME);
                qb.setProjectionMap(com.guggiemedia.fibermetric.lib.db.ChatMessageTable.PROJECTION_MAP);
                qb.appendWhere(com.guggiemedia.fibermetric.lib.db.ChatMessageTable.Columns._ID + "=" + uri.getPathSegments().get(1));
                if (sortOrder == null) {
                    orderBy = com.guggiemedia.fibermetric.lib.db.ChatMessageTable.DEFAULT_SORT_ORDER;
                }
                break;
            case URI_MATCH_EVENT:
                qb.setTables(com.guggiemedia.fibermetric.lib.db.EventTable.TABLE_NAME);
                qb.setProjectionMap(com.guggiemedia.fibermetric.lib.db.EventTable.PROJECTION_MAP);
                if (sortOrder == null) {
                    orderBy = com.guggiemedia.fibermetric.lib.db.EventTable.DEFAULT_SORT_ORDER;
                }
                break;
            case URI_MATCH_EVENT_ID:
                qb.setTables(com.guggiemedia.fibermetric.lib.db.EventTable.TABLE_NAME);
                qb.setProjectionMap(com.guggiemedia.fibermetric.lib.db.EventTable.PROJECTION_MAP);
                qb.appendWhere(com.guggiemedia.fibermetric.lib.db.EventTable.Columns._ID + "=" + uri.getPathSegments().get(1));
                if (sortOrder == null) {
                    orderBy = com.guggiemedia.fibermetric.lib.db.EventTable.DEFAULT_SORT_ORDER;
                }
                break;
            case URI_MATCH_IMAGE:
                qb.setTables(com.guggiemedia.fibermetric.lib.db.ImageTable.TABLE_NAME);
                qb.setProjectionMap(com.guggiemedia.fibermetric.lib.db.ImageTable.PROJECTION_MAP);
                if (sortOrder == null) {
                    orderBy = com.guggiemedia.fibermetric.lib.db.ImageTable.DEFAULT_SORT_ORDER;
                }
                break;
            case URI_MATCH_IMAGE_ID:
                qb.setTables(com.guggiemedia.fibermetric.lib.db.ImageTable.TABLE_NAME);
                qb.setProjectionMap(com.guggiemedia.fibermetric.lib.db.ImageTable.PROJECTION_MAP);
                qb.appendWhere(com.guggiemedia.fibermetric.lib.db.ImageTable.Columns._ID + "=" + uri.getPathSegments().get(1));
                if (sortOrder == null) {
                    orderBy = com.guggiemedia.fibermetric.lib.db.ImageTable.DEFAULT_SORT_ORDER;
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
                qb.setTables(com.guggiemedia.fibermetric.lib.db.JobTaskTable.TABLE_NAME);
                qb.setProjectionMap(com.guggiemedia.fibermetric.lib.db.JobTaskTable.PROJECTION_MAP);
                if (sortOrder == null) {
                    orderBy = com.guggiemedia.fibermetric.lib.db.JobTaskTable.DEFAULT_SORT_ORDER;
                }
                break;
            case URI_MATCH_JOBTASK_ID:
                qb.setTables(com.guggiemedia.fibermetric.lib.db.JobTaskTable.TABLE_NAME);
                qb.setProjectionMap(com.guggiemedia.fibermetric.lib.db.JobTaskTable.PROJECTION_MAP);
                qb.appendWhere(com.guggiemedia.fibermetric.lib.db.JobTaskTable.Columns._ID + "=" + uri.getPathSegments().get(1));
                if (sortOrder == null) {
                    orderBy = com.guggiemedia.fibermetric.lib.db.JobTaskTable.DEFAULT_SORT_ORDER;
                }
                break;
            case URI_MATCH_PART:
                qb.setTables(com.guggiemedia.fibermetric.lib.db.PartTable.TABLE_NAME);
                qb.setProjectionMap(com.guggiemedia.fibermetric.lib.db.PartTable.PROJECTION_MAP);
                if (sortOrder == null) {
                    orderBy = com.guggiemedia.fibermetric.lib.db.PartTable.DEFAULT_SORT_ORDER;
                }
                break;
            case URI_MATCH_PART_ID:
                qb.setTables(com.guggiemedia.fibermetric.lib.db.PartTable.TABLE_NAME);
                qb.setProjectionMap(com.guggiemedia.fibermetric.lib.db.PartTable.PROJECTION_MAP);
                qb.appendWhere(com.guggiemedia.fibermetric.lib.db.PartTable.Columns._ID + "=" + uri.getPathSegments().get(1));
                if (sortOrder == null) {
                    orderBy = com.guggiemedia.fibermetric.lib.db.PartTable.DEFAULT_SORT_ORDER;
                }
                break;
            case URI_MATCH_PART_PERSONID:
                qb.setTables(com.guggiemedia.fibermetric.lib.db.PartTable.TABLE_JOIN_PERSON_PART_TABLE);
                qb.setDistinct (true);

                if (sortOrder == null) {
                    orderBy = com.guggiemedia.fibermetric.lib.db.PartTable.DEFAULT_SORT_ORDER;
                }
                break;
            case URI_MATCH_PART_JOB_ID:
                qb.setTables(com.guggiemedia.fibermetric.lib.db.PartTable.TABLE_JOIN_PART_JOB_TABLE);
                qb.setDistinct (true);

                if (sortOrder == null) {
                    orderBy = com.guggiemedia.fibermetric.lib.db.PartTable.DEFAULT_SORT_ORDER;
                }
                break;
            case URI_MATCH_PERSON:
                qb.setTables(com.guggiemedia.fibermetric.lib.db.PersonTable.TABLE_NAME);
                qb.setProjectionMap(com.guggiemedia.fibermetric.lib.db.PersonTable.PROJECTION_MAP);
                if (sortOrder == null) {
                    orderBy = com.guggiemedia.fibermetric.lib.db.PersonTable.DEFAULT_SORT_ORDER;
                }
                break;
            case URI_MATCH_PERSON_ID:
                qb.setTables(com.guggiemedia.fibermetric.lib.db.PersonTable.TABLE_NAME);
                qb.setProjectionMap(com.guggiemedia.fibermetric.lib.db.PersonTable.PROJECTION_MAP);
                qb.appendWhere(com.guggiemedia.fibermetric.lib.db.PersonTable.Columns._ID + "=" + uri.getPathSegments().get(1));
                if (sortOrder == null) {
                    orderBy = com.guggiemedia.fibermetric.lib.db.PersonTable.DEFAULT_SORT_ORDER;
                }
                break;
            case URI_MATCH_PERSON_PART:
                qb.setTables(com.guggiemedia.fibermetric.lib.db.PersonPartTable.TABLE_NAME);
                qb.setProjectionMap(com.guggiemedia.fibermetric.lib.db.PersonPartTable.PROJECTION_MAP);
                if (sortOrder == null) {
                    orderBy = com.guggiemedia.fibermetric.lib.db.PersonPartTable.DEFAULT_SORT_ORDER;
                }
                break;
            case URI_MATCH_PERSON_PART_ID:
                qb.setTables(com.guggiemedia.fibermetric.lib.db.PersonPartTable.TABLE_NAME);
                qb.setProjectionMap(com.guggiemedia.fibermetric.lib.db.PersonPartTable.PROJECTION_MAP);
                qb.appendWhere(com.guggiemedia.fibermetric.lib.db.PersonPartTable.Columns._ID + "=" + uri.getPathSegments().get(1));
                if (sortOrder == null) {
                    orderBy = com.guggiemedia.fibermetric.lib.db.PersonPartTable.DEFAULT_SORT_ORDER;
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
            case URI_MATCH_TASK_ACTION:
                qb.setTables(com.guggiemedia.fibermetric.lib.db.TaskActionTable.TABLE_NAME);
                qb.setProjectionMap(com.guggiemedia.fibermetric.lib.db.TaskActionTable.PROJECTION_MAP);
                if (sortOrder == null) {
                    orderBy = com.guggiemedia.fibermetric.lib.db.TaskActionTable.DEFAULT_SORT_ORDER;
                }
                break;
            case URI_MATCH_TASK_DETAIL:
                qb.setTables(com.guggiemedia.fibermetric.lib.db.TaskDetailTable.TABLE_NAME);
                qb.setProjectionMap(com.guggiemedia.fibermetric.lib.db.TaskDetailTable.PROJECTION_MAP);
                if (sortOrder == null) {
                    orderBy = com.guggiemedia.fibermetric.lib.db.TaskDetailTable.DEFAULT_SORT_ORDER;
                }
                break;
            case URI_MATCH_TASK_DETAIL_ID:
                qb.setTables(com.guggiemedia.fibermetric.lib.db.TaskDetailTable.TABLE_NAME);
                qb.setProjectionMap(com.guggiemedia.fibermetric.lib.db.TaskDetailTable.PROJECTION_MAP);
                qb.appendWhere(com.guggiemedia.fibermetric.lib.db.TaskDetailTable.Columns._ID + "=" + uri.getPathSegments().get(1));
                if (sortOrder == null) {
                    orderBy = com.guggiemedia.fibermetric.lib.db.TaskDetailTable.DEFAULT_SORT_ORDER;
                }
                break;
            case URI_MATCH_THING:
                qb.setTables(com.guggiemedia.fibermetric.lib.db.ThingTable.TABLE_NAME);
                qb.setProjectionMap(com.guggiemedia.fibermetric.lib.db.ThingTable.PROJECTION_MAP);
                if (sortOrder == null) {
                    orderBy = com.guggiemedia.fibermetric.lib.db.ThingTable.DEFAULT_SORT_ORDER;
                }
                break;
            case URI_MATCH_THING_ID:
                qb.setTables(com.guggiemedia.fibermetric.lib.db.ThingTable.TABLE_NAME);
                qb.setProjectionMap(com.guggiemedia.fibermetric.lib.db.ThingTable.PROJECTION_MAP);
                qb.appendWhere(com.guggiemedia.fibermetric.lib.db.ThingTable.Columns._ID + "=" + uri.getPathSegments().get(1));
                if (sortOrder == null) {
                    orderBy = com.guggiemedia.fibermetric.lib.db.ThingTable.DEFAULT_SORT_ORDER;
                }
                break;
            case URI_MATCH_TOOL:
                qb.setTables(com.guggiemedia.fibermetric.lib.db.ToolTable.TABLE_NAME);
                qb.setProjectionMap(com.guggiemedia.fibermetric.lib.db.ToolTable.PROJECTION_MAP);
                if (sortOrder == null) {
                    orderBy = com.guggiemedia.fibermetric.lib.db.ToolTable.DEFAULT_SORT_ORDER;
                }
                break;
            case URI_MATCH_TOOL_ID:
                qb.setTables(com.guggiemedia.fibermetric.lib.db.ToolTable.TABLE_NAME);
                qb.setProjectionMap(com.guggiemedia.fibermetric.lib.db.ToolTable.PROJECTION_MAP);
                qb.appendWhere(com.guggiemedia.fibermetric.lib.db.ToolTable.Columns._ID + "=" + uri.getPathSegments().get(1));
                if (sortOrder == null) {
                    orderBy = com.guggiemedia.fibermetric.lib.db.ToolTable.DEFAULT_SORT_ORDER;
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
                count = db.update(com.guggiemedia.fibermetric.lib.db.AlertTable.TABLE_NAME, values, selection, selectionArgs);
                break;
            case URI_MATCH_ALERT_ID:
                id = uri.getPathSegments().get(1);
                count = db.update(com.guggiemedia.fibermetric.lib.db.AlertTable.TABLE_NAME, values, com.guggiemedia.fibermetric.lib.db.AlertTable.Columns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            case URI_MATCH_CHAIN_OF_CUSTODY:
                count = db.update(com.guggiemedia.fibermetric.lib.db.ChainOfCustodyTable.TABLE_NAME, values, selection, selectionArgs);
                break;
            case URI_MATCH_CHAIN_OF_CUSTODY_ID:
                id = uri.getPathSegments().get(1);
                count = db.update(com.guggiemedia.fibermetric.lib.db.ChainOfCustodyTable.TABLE_NAME, values, com.guggiemedia.fibermetric.lib.db.ChainOfCustodyTable.Columns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            case URI_MATCH_CHAT:
                count = db.update(com.guggiemedia.fibermetric.lib.db.ChatTable.TABLE_NAME, values, selection, selectionArgs);
                break;
            case URI_MATCH_CHAT_ID:
                id = uri.getPathSegments().get(1);
                count = db.update(com.guggiemedia.fibermetric.lib.db.ChatTable.TABLE_NAME, values, com.guggiemedia.fibermetric.lib.db.ChatTable.Columns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            case URI_MATCH_CHAT_MSG:
                count = db.update(com.guggiemedia.fibermetric.lib.db.ChatMessageTable.TABLE_NAME, values, selection, selectionArgs);
                break;
            case URI_MATCH_CHAT_MSG_ID:
                id = uri.getPathSegments().get(1);
                count = db.update(com.guggiemedia.fibermetric.lib.db.ChatMessageTable.TABLE_NAME, values, com.guggiemedia.fibermetric.lib.db.ChatMessageTable.Columns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            case URI_MATCH_EVENT:
                count = db.update(com.guggiemedia.fibermetric.lib.db.EventTable.TABLE_NAME, values, selection, selectionArgs);
                break;
            case URI_MATCH_EVENT_ID:
                id = uri.getPathSegments().get(1);
                count = db.update(com.guggiemedia.fibermetric.lib.db.EventTable.TABLE_NAME, values, com.guggiemedia.fibermetric.lib.db.EventTable.Columns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            case URI_MATCH_IMAGE:
                count = db.update(com.guggiemedia.fibermetric.lib.db.ImageTable.TABLE_NAME, values, selection, selectionArgs);
                break;
            case URI_MATCH_IMAGE_ID:
                id = uri.getPathSegments().get(1);
                count = db.update(com.guggiemedia.fibermetric.lib.db.ImageTable.TABLE_NAME, values, com.guggiemedia.fibermetric.lib.db.ImageTable.Columns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            case URI_MATCH_JOB_PART:
                count = db.update(com.guggiemedia.fibermetric.lib.db.JobPartTable.TABLE_NAME, values, selection, selectionArgs);
                break;
            case URI_MATCH_JOB_PART_ID:
                id = uri.getPathSegments().get(1);
                count = db.update(com.guggiemedia.fibermetric.lib.db.JobPartTable.TABLE_NAME, values, com.guggiemedia.fibermetric.lib.db.JobPartTable.Columns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            case URI_MATCH_JOBTASK:
                count = db.update(com.guggiemedia.fibermetric.lib.db.JobTaskTable.TABLE_NAME, values, selection, selectionArgs);
                break;
            case URI_MATCH_JOBTASK_ID:
                id = uri.getPathSegments().get(1);
                count = db.update(com.guggiemedia.fibermetric.lib.db.JobTaskTable.TABLE_NAME, values, com.guggiemedia.fibermetric.lib.db.JobTaskTable.Columns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            case URI_MATCH_PART:
                count = db.update(com.guggiemedia.fibermetric.lib.db.PartTable.TABLE_NAME, values, selection, selectionArgs);
                break;
            case URI_MATCH_PART_ID:
                id = uri.getPathSegments().get(1);
                count = db.update(com.guggiemedia.fibermetric.lib.db.PartTable.TABLE_NAME, values, com.guggiemedia.fibermetric.lib.db.PartTable.Columns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            case URI_MATCH_PERSON:
                count = db.update(com.guggiemedia.fibermetric.lib.db.PersonTable.TABLE_NAME, values, selection, selectionArgs);
                break;
            case URI_MATCH_PERSON_ID:
                id = uri.getPathSegments().get(1);
                count = db.update(com.guggiemedia.fibermetric.lib.db.PersonTable.TABLE_NAME, values, com.guggiemedia.fibermetric.lib.db.PersonTable.Columns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            case URI_MATCH_PERSON_PART:
                count = db.update(com.guggiemedia.fibermetric.lib.db.PersonPartTable.TABLE_NAME, values, selection, selectionArgs);
                break;
            case URI_MATCH_PERSON_PART_ID:
                id = uri.getPathSegments().get(1);
                count = db.update(com.guggiemedia.fibermetric.lib.db.PersonPartTable.TABLE_NAME, values, com.guggiemedia.fibermetric.lib.db.PersonPartTable.Columns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            case URI_MATCH_SITE:
                count = db.update(com.guggiemedia.fibermetric.lib.db.SiteTable.TABLE_NAME, values, selection, selectionArgs);
                break;
            case URI_MATCH_SITE_ID:
                id = uri.getPathSegments().get(1);
                count = db.update(com.guggiemedia.fibermetric.lib.db.SiteTable.TABLE_NAME, values, com.guggiemedia.fibermetric.lib.db.SiteTable.Columns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            case URI_MATCH_TASK_ACTION:
                count = db.update(com.guggiemedia.fibermetric.lib.db.TaskActionTable.TABLE_NAME, values, selection, selectionArgs);
                break;
            case URI_MATCH_TASK_ACTION_ID:
                id = uri.getPathSegments().get(1);
                count = db.update(com.guggiemedia.fibermetric.lib.db.TaskActionTable.TABLE_NAME, values, com.guggiemedia.fibermetric.lib.db.TaskActionTable.Columns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            case URI_MATCH_TASK_DETAIL:
                count = db.update(com.guggiemedia.fibermetric.lib.db.TaskDetailTable.TABLE_NAME, values, selection, selectionArgs);
                break;
            case URI_MATCH_TASK_DETAIL_ID:
                id = uri.getPathSegments().get(1);
                count = db.update(com.guggiemedia.fibermetric.lib.db.TaskDetailTable.TABLE_NAME, values, com.guggiemedia.fibermetric.lib.db.TaskDetailTable.Columns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            case URI_MATCH_THING:
                count = db.update(com.guggiemedia.fibermetric.lib.db.ThingTable.TABLE_NAME, values, selection, selectionArgs);
                break;
            case URI_MATCH_THING_ID:
                id = uri.getPathSegments().get(1);
                count = db.update(com.guggiemedia.fibermetric.lib.db.ThingTable.TABLE_NAME, values, com.guggiemedia.fibermetric.lib.db.ThingTable.Columns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            case URI_MATCH_TOOL:
                count = db.update(com.guggiemedia.fibermetric.lib.db.ToolTable.TABLE_NAME, values, selection, selectionArgs);
                break;
            case URI_MATCH_TOOL_ID:
                id = uri.getPathSegments().get(1);
                count = db.update(com.guggiemedia.fibermetric.lib.db.ToolTable.TABLE_NAME, values, com.guggiemedia.fibermetric.lib.db.ToolTable.Columns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}