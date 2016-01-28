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


    private static final int URI_MATCH_PART = 28;
    private static final int URI_MATCH_PART_ID = 29;
    private static final int URI_MATCH_PART_PERSONID = 30;
    private static final int URI_MATCH_PART_JOB_ID = 31;



    private static final int URI_MATCH_SITE = 36;
    private static final int URI_MATCH_SITE_ID = 37;


    private static final UriMatcher URI_MATCHER;

    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);



        URI_MATCHER.addURI(Constant.AUTHORITY, ItemTable.TABLE_NAME, URI_MATCH_PART);
        URI_MATCHER.addURI(Constant.AUTHORITY, ItemTable.TABLE_NAME + "/#", URI_MATCH_PART_ID);
        URI_MATCHER.addURI(Constant.AUTHORITY, ItemTable.TABLE_NAME + "/by_person", URI_MATCH_PART_PERSONID);
        URI_MATCHER.addURI(Constant.AUTHORITY, ItemTable.TABLE_NAME + "/by_job", URI_MATCH_PART_JOB_ID);




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


            case URI_MATCH_PART:
                count = db.delete(ItemTable.TABLE_NAME, selection, selectionArgs);
                break;
            case URI_MATCH_PART_ID:
                id = uri.getPathSegments().get(1);
                count = db.delete(ItemTable.TABLE_NAME, ItemTable.Columns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
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



            case URI_MATCH_PART:
                return ItemTable.CONTENT_TYPE;
            case URI_MATCH_PART_ID:
                return ItemTable.CONTENT_ITEM_TYPE;

            case URI_MATCH_PART_PERSONID:
                return ItemTable.CONTENT_TYPE;
            case URI_MATCH_PART_JOB_ID:
                return ItemTable.CONTENT_TYPE;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowId = 0;
        SQLiteDatabase db = _dbHelper.getWritableDatabase();

        switch (URI_MATCHER.match(uri)) {


            case URI_MATCH_PART:
                rowId = db.insert(ItemTable.TABLE_NAME, null, values);
                if (rowId > 0) {
                    Uri result = ContentUris.withAppendedId(ItemTable.CONTENT_URI, rowId);
                    getContext().getContentResolver().notifyChange(ItemTable.CONTENT_URI, null);
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



            case URI_MATCH_PART:
                qb.setTables(ItemTable.TABLE_NAME);
                qb.setProjectionMap(ItemTable.PROJECTION_MAP);
                if (sortOrder == null) {
                    orderBy = ItemTable.DEFAULT_SORT_ORDER;
                }
                break;
            case URI_MATCH_PART_ID:
                qb.setTables(ItemTable.TABLE_NAME);
                qb.setProjectionMap(ItemTable.PROJECTION_MAP);
                qb.appendWhere(ItemTable.Columns._ID + "=" + uri.getPathSegments().get(1));
                if (sortOrder == null) {
                    orderBy = ItemTable.DEFAULT_SORT_ORDER;
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

            case URI_MATCH_PART:
                count = db.update(ItemTable.TABLE_NAME, values, selection, selectionArgs);
                break;
            case URI_MATCH_PART_ID:
                id = uri.getPathSegments().get(1);
                count = db.update(ItemTable.TABLE_NAME, values, ItemTable.Columns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;


            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}