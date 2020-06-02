package com.unfpa.safepal.provider;

import java.util.Arrays;

import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.unfpa.safepal.BuildConfig;
import com.unfpa.safepal.provider.base.BaseContentProvider;
import com.unfpa.safepal.provider.answertable.AnswertableColumns;
import com.unfpa.safepal.provider.articletable.ArticletableColumns;
import com.unfpa.safepal.provider.districttable.DistricttableColumns;
import com.unfpa.safepal.provider.organizationtable.OrganizationtableColumns;
import com.unfpa.safepal.provider.questiontable.QuestiontableColumns;
import com.unfpa.safepal.provider.quiztable.QuiztableColumns;
import com.unfpa.safepal.provider.videotable.VideotableColumns;

public class SafepalProvider extends BaseContentProvider {
    private static final String TAG = SafepalProvider.class.getSimpleName();

    private static final boolean DEBUG = BuildConfig.DEBUG;

    private static final String TYPE_CURSOR_ITEM = "vnd.android.cursor.item/";
    private static final String TYPE_CURSOR_DIR = "vnd.android.cursor.dir/";

    public static final String AUTHORITY = "com.unfpa.safepal.authority";
    public static final String CONTENT_URI_BASE = "content://" + AUTHORITY;

    private static final int URI_TYPE_ANSWERTABLE = 0;
    private static final int URI_TYPE_ANSWERTABLE_ID = 1;

    private static final int URI_TYPE_ARTICLETABLE = 2;
    private static final int URI_TYPE_ARTICLETABLE_ID = 3;

    private static final int URI_TYPE_DISTRICTTABLE = 4;
    private static final int URI_TYPE_DISTRICTTABLE_ID = 5;

    private static final int URI_TYPE_ORGANIZATIONTABLE = 6;
    private static final int URI_TYPE_ORGANIZATIONTABLE_ID = 7;

    private static final int URI_TYPE_QUESTIONTABLE = 8;
    private static final int URI_TYPE_QUESTIONTABLE_ID = 9;

    private static final int URI_TYPE_QUIZTABLE = 10;
    private static final int URI_TYPE_QUIZTABLE_ID = 11;

    private static final int URI_TYPE_VIDEOTABLE = 12;
    private static final int URI_TYPE_VIDEOTABLE_ID = 13;



    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(AUTHORITY, AnswertableColumns.TABLE_NAME, URI_TYPE_ANSWERTABLE);
        URI_MATCHER.addURI(AUTHORITY, AnswertableColumns.TABLE_NAME + "/#", URI_TYPE_ANSWERTABLE_ID);
        URI_MATCHER.addURI(AUTHORITY, ArticletableColumns.TABLE_NAME, URI_TYPE_ARTICLETABLE);
        URI_MATCHER.addURI(AUTHORITY, ArticletableColumns.TABLE_NAME + "/#", URI_TYPE_ARTICLETABLE_ID);
        URI_MATCHER.addURI(AUTHORITY, DistricttableColumns.TABLE_NAME, URI_TYPE_DISTRICTTABLE);
        URI_MATCHER.addURI(AUTHORITY, DistricttableColumns.TABLE_NAME + "/#", URI_TYPE_DISTRICTTABLE_ID);
        URI_MATCHER.addURI(AUTHORITY, OrganizationtableColumns.TABLE_NAME, URI_TYPE_ORGANIZATIONTABLE);
        URI_MATCHER.addURI(AUTHORITY, OrganizationtableColumns.TABLE_NAME + "/#", URI_TYPE_ORGANIZATIONTABLE_ID);
        URI_MATCHER.addURI(AUTHORITY, QuestiontableColumns.TABLE_NAME, URI_TYPE_QUESTIONTABLE);
        URI_MATCHER.addURI(AUTHORITY, QuestiontableColumns.TABLE_NAME + "/#", URI_TYPE_QUESTIONTABLE_ID);
        URI_MATCHER.addURI(AUTHORITY, QuiztableColumns.TABLE_NAME, URI_TYPE_QUIZTABLE);
        URI_MATCHER.addURI(AUTHORITY, QuiztableColumns.TABLE_NAME + "/#", URI_TYPE_QUIZTABLE_ID);
        URI_MATCHER.addURI(AUTHORITY, VideotableColumns.TABLE_NAME, URI_TYPE_VIDEOTABLE);
        URI_MATCHER.addURI(AUTHORITY, VideotableColumns.TABLE_NAME + "/#", URI_TYPE_VIDEOTABLE_ID);
    }

    @Override
    protected SQLiteOpenHelper createSqLiteOpenHelper() {
        return SafepalDatabaseHelper.getInstance(getContext());
    }

    @Override
    protected boolean hasDebug() {
        return DEBUG;
    }

    @Override
    public String getType(Uri uri) {
        int match = URI_MATCHER.match(uri);
        switch (match) {
            case URI_TYPE_ANSWERTABLE:
                return TYPE_CURSOR_DIR + AnswertableColumns.TABLE_NAME;
            case URI_TYPE_ANSWERTABLE_ID:
                return TYPE_CURSOR_ITEM + AnswertableColumns.TABLE_NAME;

            case URI_TYPE_ARTICLETABLE:
                return TYPE_CURSOR_DIR + ArticletableColumns.TABLE_NAME;
            case URI_TYPE_ARTICLETABLE_ID:
                return TYPE_CURSOR_ITEM + ArticletableColumns.TABLE_NAME;

            case URI_TYPE_DISTRICTTABLE:
                return TYPE_CURSOR_DIR + DistricttableColumns.TABLE_NAME;
            case URI_TYPE_DISTRICTTABLE_ID:
                return TYPE_CURSOR_ITEM + DistricttableColumns.TABLE_NAME;

            case URI_TYPE_ORGANIZATIONTABLE:
                return TYPE_CURSOR_DIR + OrganizationtableColumns.TABLE_NAME;
            case URI_TYPE_ORGANIZATIONTABLE_ID:
                return TYPE_CURSOR_ITEM + OrganizationtableColumns.TABLE_NAME;

            case URI_TYPE_QUESTIONTABLE:
                return TYPE_CURSOR_DIR + QuestiontableColumns.TABLE_NAME;
            case URI_TYPE_QUESTIONTABLE_ID:
                return TYPE_CURSOR_ITEM + QuestiontableColumns.TABLE_NAME;

            case URI_TYPE_QUIZTABLE:
                return TYPE_CURSOR_DIR + QuiztableColumns.TABLE_NAME;
            case URI_TYPE_QUIZTABLE_ID:
                return TYPE_CURSOR_ITEM + QuiztableColumns.TABLE_NAME;

            case URI_TYPE_VIDEOTABLE:
                return TYPE_CURSOR_DIR + VideotableColumns.TABLE_NAME;
            case URI_TYPE_VIDEOTABLE_ID:
                return TYPE_CURSOR_ITEM + VideotableColumns.TABLE_NAME;

        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if (DEBUG) Log.d(TAG, "insert uri=" + uri + " values=" + values);
        return super.insert(uri, values);
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        if (DEBUG) Log.d(TAG, "bulkInsert uri=" + uri + " values.length=" + values.length);
        return super.bulkInsert(uri, values);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (DEBUG) Log.d(TAG, "update uri=" + uri + " values=" + values + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs));
        return super.update(uri, values, selection, selectionArgs);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        if (DEBUG) Log.d(TAG, "delete uri=" + uri + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs));
        return super.delete(uri, selection, selectionArgs);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if (DEBUG)
            Log.d(TAG, "query uri=" + uri + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs) + " sortOrder=" + sortOrder
                    + " groupBy=" + uri.getQueryParameter(QUERY_GROUP_BY) + " having=" + uri.getQueryParameter(QUERY_HAVING) + " limit=" + uri.getQueryParameter(QUERY_LIMIT));
        return super.query(uri, projection, selection, selectionArgs, sortOrder);
    }

    @Override
    protected QueryParams getQueryParams(Uri uri, String selection, String[] projection) {
        QueryParams res = new QueryParams();
        String id = null;
        int matchedId = URI_MATCHER.match(uri);
        switch (matchedId) {
            case URI_TYPE_ANSWERTABLE:
            case URI_TYPE_ANSWERTABLE_ID:
                res.table = AnswertableColumns.TABLE_NAME;
                res.idColumn = AnswertableColumns._ID;
                res.tablesWithJoins = AnswertableColumns.TABLE_NAME;
                res.orderBy = AnswertableColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_ARTICLETABLE:
            case URI_TYPE_ARTICLETABLE_ID:
                res.table = ArticletableColumns.TABLE_NAME;
                res.idColumn = ArticletableColumns._ID;
                res.tablesWithJoins = ArticletableColumns.TABLE_NAME;
                res.orderBy = ArticletableColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_DISTRICTTABLE:
            case URI_TYPE_DISTRICTTABLE_ID:
                res.table = DistricttableColumns.TABLE_NAME;
                res.idColumn = DistricttableColumns._ID;
                res.tablesWithJoins = DistricttableColumns.TABLE_NAME;
                res.orderBy = DistricttableColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_ORGANIZATIONTABLE:
            case URI_TYPE_ORGANIZATIONTABLE_ID:
                res.table = OrganizationtableColumns.TABLE_NAME;
                res.idColumn = OrganizationtableColumns._ID;
                res.tablesWithJoins = OrganizationtableColumns.TABLE_NAME;
                res.orderBy = OrganizationtableColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_QUESTIONTABLE:
            case URI_TYPE_QUESTIONTABLE_ID:
                res.table = QuestiontableColumns.TABLE_NAME;
                res.idColumn = QuestiontableColumns._ID;
                res.tablesWithJoins = QuestiontableColumns.TABLE_NAME;
                res.orderBy = QuestiontableColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_QUIZTABLE:
            case URI_TYPE_QUIZTABLE_ID:
                res.table = QuiztableColumns.TABLE_NAME;
                res.idColumn = QuiztableColumns._ID;
                res.tablesWithJoins = QuiztableColumns.TABLE_NAME;
                res.orderBy = QuiztableColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_VIDEOTABLE:
            case URI_TYPE_VIDEOTABLE_ID:
                res.table = VideotableColumns.TABLE_NAME;
                res.idColumn = VideotableColumns._ID;
                res.tablesWithJoins = VideotableColumns.TABLE_NAME;
                res.orderBy = VideotableColumns.DEFAULT_ORDER;
                break;

            default:
                throw new IllegalArgumentException("The uri '" + uri + "' is not supported by this ContentProvider");
        }

        switch (matchedId) {
            case URI_TYPE_ANSWERTABLE_ID:
            case URI_TYPE_ARTICLETABLE_ID:
            case URI_TYPE_DISTRICTTABLE_ID:
            case URI_TYPE_ORGANIZATIONTABLE_ID:
            case URI_TYPE_QUESTIONTABLE_ID:
            case URI_TYPE_QUIZTABLE_ID:
            case URI_TYPE_VIDEOTABLE_ID:
                id = uri.getLastPathSegment();
        }
        if (id != null) {
            if (selection != null) {
                res.selection = res.table + "." + res.idColumn + "=" + id + " and (" + selection + ")";
            } else {
                res.selection = res.table + "." + res.idColumn + "=" + id;
            }
        } else {
            res.selection = selection;
        }
        return res;
    }
}
