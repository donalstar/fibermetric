package com.guggiemedia.fibermetric.db;

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

import com.guggiemedia.fibermetric.Constant;

public class DataBaseProvider extends ContentProvider {
    public static final String LOG_TAG = DataBaseProvider.class.getName();

    //URI Matcher Targets
    private static final int URI_MATCH_HISTORY = 1;
    private static final int URI_MATCH_HISTORY_ID = 2;

    private static final int URI_MATCH_ITEM = 3;
    private static final int URI_MATCH_ITEM_ID = 4;

    private static final int URI_MATCH_ADDED_ITEM = 5;
    private static final int URI_MATCH_ADDED_ITEM_ID = 6;
    private static final int URI_MATCH_ADDED_ITEM_ITEMS = 7;

    private static final UriMatcher URI_MATCHER;

    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

        URI_MATCHER.addURI(Constant.AUTHORITY, HistoryTable.TABLE_NAME, URI_MATCH_HISTORY);
        URI_MATCHER.addURI(Constant.AUTHORITY, HistoryTable.TABLE_NAME + "/#", URI_MATCH_HISTORY_ID);

        URI_MATCHER.addURI(Constant.AUTHORITY, ItemTable.TABLE_NAME, URI_MATCH_ITEM);
        URI_MATCHER.addURI(Constant.AUTHORITY, ItemTable.TABLE_NAME + "/#", URI_MATCH_ITEM_ID);

        URI_MATCHER.addURI(Constant.AUTHORITY, AddedItemTable.TABLE_NAME, URI_MATCH_ADDED_ITEM);
        URI_MATCHER.addURI(Constant.AUTHORITY, AddedItemTable.TABLE_NAME + "/#", URI_MATCH_ADDED_ITEM_ID);
        URI_MATCHER.addURI(Constant.AUTHORITY, AddedItemTable.TABLE_NAME + "/items", URI_MATCH_ADDED_ITEM_ITEMS);
    }

    private DataBaseHelper _dbHelper;


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        String id = "";

        SQLiteDatabase db = _dbHelper.getWritableDatabase();

        switch (URI_MATCHER.match(uri)) {

            case URI_MATCH_HISTORY:
                count = db.delete(HistoryTable.TABLE_NAME, selection, selectionArgs);
                break;
            case URI_MATCH_HISTORY_ID:
                id = uri.getPathSegments().get(1);

                selection = getItemIdSelectionClause(HistoryTable.Columns._ID, id, selection);

                count = db.delete(HistoryTable.TABLE_NAME, selection, selectionArgs);
                break;

            case URI_MATCH_ITEM:
                count = db.delete(ItemTable.TABLE_NAME, selection, selectionArgs);
                break;
            case URI_MATCH_ITEM_ID:
                id = uri.getPathSegments().get(1);

                selection = getItemIdSelectionClause(ItemTable.Columns._ID, id, selection);

                count = db.delete(ItemTable.TABLE_NAME, selection, selectionArgs);
                break;

            case URI_MATCH_ADDED_ITEM:
                count = db.delete(AddedItemTable.TABLE_NAME, selection, selectionArgs);
                break;
            case URI_MATCH_ADDED_ITEM_ID:
                id = uri.getPathSegments().get(1);

                selection = getItemIdSelectionClause(ItemTable.Columns._ID, id, selection);

                count = db.delete(AddedItemTable.TABLE_NAME, selection, selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    private String getItemIdSelectionClause(String idColumn, String id, String selection) {
        String selectionClause = selection;

        if (id != null && !id.isEmpty()) {
            selectionClause = idColumn + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : "");
        }

        return selectionClause;
    }

    @Override
    public String getType(Uri uri) {
        switch (URI_MATCHER.match(uri)) {

            case URI_MATCH_HISTORY:
                return HistoryTable.CONTENT_TYPE;
            case URI_MATCH_HISTORY_ID:
                return HistoryTable.CONTENT_ITEM_TYPE;
            case URI_MATCH_ITEM:
                return ItemTable.CONTENT_TYPE;
            case URI_MATCH_ITEM_ID:
                return ItemTable.CONTENT_ITEM_TYPE;
            case URI_MATCH_ADDED_ITEM:
                return AddedItemTable.CONTENT_TYPE;
            case URI_MATCH_ADDED_ITEM_ID:
                return AddedItemTable.CONTENT_ITEM_TYPE;
            case URI_MATCH_ADDED_ITEM_ITEMS:
                return AddedItemTable.CONTENT_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowId = 0;
        SQLiteDatabase db = _dbHelper.getWritableDatabase();

        switch (URI_MATCHER.match(uri)) {

            case URI_MATCH_HISTORY:
                rowId = db.insert(HistoryTable.TABLE_NAME, null, values);
                if (rowId > 0) {
                    Uri result = ContentUris.withAppendedId(HistoryTable.CONTENT_URI, rowId);
                    getContext().getContentResolver().notifyChange(HistoryTable.CONTENT_URI, null);
                    return result;
                }
                break;

            case URI_MATCH_ITEM:
                rowId = db.insert(ItemTable.TABLE_NAME, null, values);
                if (rowId > 0) {
                    Uri result = ContentUris.withAppendedId(ItemTable.CONTENT_URI, rowId);
                    getContext().getContentResolver().notifyChange(ItemTable.CONTENT_URI, null);
                    return result;
                }
                break;

            case URI_MATCH_ADDED_ITEM:
                rowId = db.insert(AddedItemTable.TABLE_NAME, null, values);
                if (rowId > 0) {
                    Uri result = ContentUris.withAppendedId(AddedItemTable.CONTENT_URI, rowId);
                    getContext().getContentResolver().notifyChange(AddedItemTable.CONTENT_URI, null);
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

            case URI_MATCH_HISTORY:
                qb.setTables(HistoryTable.TABLE_NAME);
                qb.setProjectionMap(HistoryTable.PROJECTION_MAP);
                if (sortOrder == null) {
                    orderBy = HistoryTable.DEFAULT_SORT_ORDER;
                }
                break;
            case URI_MATCH_HISTORY_ID:
                qb.setTables(HistoryTable.TABLE_NAME);
                qb.setProjectionMap(HistoryTable.PROJECTION_MAP);
                qb.appendWhere(HistoryTable.Columns._ID + "=" + uri.getPathSegments().get(1));
                if (sortOrder == null) {
                    orderBy = HistoryTable.DEFAULT_SORT_ORDER;
                }
                break;
            case URI_MATCH_ITEM:
                qb.setTables(ItemTable.TABLE_NAME);
                qb.setProjectionMap(ItemTable.PROJECTION_MAP);
                if (sortOrder == null) {
                    orderBy = ItemTable.DEFAULT_SORT_ORDER;
                }
                break;
            case URI_MATCH_ITEM_ID:
                qb.setTables(ItemTable.TABLE_NAME);
                qb.setProjectionMap(ItemTable.PROJECTION_MAP);
                qb.appendWhere(ItemTable.Columns._ID + "=" + uri.getPathSegments().get(1));
                if (sortOrder == null) {
                    orderBy = ItemTable.DEFAULT_SORT_ORDER;
                }
                break;
            case URI_MATCH_ADDED_ITEM:
                qb.setTables(AddedItemTable.TABLE_NAME);
                qb.setProjectionMap(AddedItemTable.PROJECTION_MAP);
                if (sortOrder == null) {
                    orderBy = AddedItemTable.DEFAULT_SORT_ORDER;
                }
                break;
            case URI_MATCH_ADDED_ITEM_ID:
                qb.setTables(AddedItemTable.TABLE_NAME);
                qb.setProjectionMap(AddedItemTable.PROJECTION_MAP);
                qb.appendWhere(AddedItemTable.Columns._ID + "=" + uri.getPathSegments().get(1));
                if (sortOrder == null) {
                    orderBy = AddedItemTable.DEFAULT_SORT_ORDER;
                }
                break;

            case URI_MATCH_ADDED_ITEM_ITEMS:
                qb.setTables(AddedItemTable.TABLE_JOIN_ITEM_TABLE);
                qb.setDistinct(true);

                if (sortOrder == null) {
                    orderBy = AddedItemTable.DEFAULT_SORT_ORDER;
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

            case URI_MATCH_HISTORY:
                count = db.update(HistoryTable.TABLE_NAME, values, selection, selectionArgs);
                break;
            case URI_MATCH_HISTORY_ID:
                id = uri.getPathSegments().get(1);
                count = db.update(HistoryTable.TABLE_NAME, values, HistoryTable.Columns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            case URI_MATCH_ITEM:
                count = db.update(ItemTable.TABLE_NAME, values, selection, selectionArgs);
                break;
            case URI_MATCH_ITEM_ID:
                id = uri.getPathSegments().get(1);
                count = db.update(ItemTable.TABLE_NAME, values, ItemTable.Columns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            case URI_MATCH_ADDED_ITEM:
                count = db.update(AddedItemTable.TABLE_NAME, values, selection, selectionArgs);
                break;
            case URI_MATCH_ADDED_ITEM_ID:
                id = uri.getPathSegments().get(1);
                count = db.update(AddedItemTable.TABLE_NAME, values, AddedItemTable.Columns._ID + "=" + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}