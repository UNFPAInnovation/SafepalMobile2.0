package com.unfpa.safepal.store;
import java.util.Arrays;
import java.util.HashSet;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
/**
 * Created by Kisa on 9/2/2016.
 */
public class ReportIncidentContentProvider extends ContentProvider {

    // database
    private ReportIncidentDatabaseHelper database;

    // used for the UriMacher
    private static final int INCIDENTREPORTS = 10;
    private static final int INCIDENTREPORTS_ID = 20;

    private static final String AUTHORITY = "com.unfpa.safepal.store.contentprovider";

    private static final String BASE_PATH = "reportincident";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);


    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE  + "/store";
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE  + "/reportincident";
    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sURIMatcher.addURI(AUTHORITY, BASE_PATH, INCIDENTREPORTS);
        sURIMatcher.addURI(AUTHORITY, BASE_PATH + "/#", INCIDENTREPORTS_ID);
    }





    @Override
    public boolean onCreate() {
        database = new ReportIncidentDatabaseHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,String[] selectionArgs, String sortOrder) {
        // Uisng SQLiteQueryBuilder instead of query() method
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        // check if the caller has requested a column which does not exists
        checkColumns(projection);

        // Set the table
        queryBuilder.setTables(ReportIncidentTable.TABLE_REPORT_INCIDENT);

        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
            case INCIDENTREPORTS:
                break;
            case INCIDENTREPORTS_ID:
                // adding the ID to the original query
                queryBuilder.appendWhere(ReportIncidentTable.COLUMN_ID + "="
                        + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        SQLiteDatabase db = database.getWritableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);
        // make sure that potential listeners are getting notified
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        long id = 0;
        switch (uriType) {
            case INCIDENTREPORTS:
                id = sqlDB.insert(ReportIncidentTable.TABLE_REPORT_INCIDENT, null, values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(BASE_PATH + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsDeleted = 0;
        switch (uriType) {
            case INCIDENTREPORTS:
                rowsDeleted = sqlDB.delete(ReportIncidentTable.TABLE_REPORT_INCIDENT, selection, selectionArgs);
                break;
            case INCIDENTREPORTS_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = sqlDB.delete(
                            ReportIncidentTable.TABLE_REPORT_INCIDENT,
                            ReportIncidentTable.COLUMN_ID + "=" + id,
                            null);
                } else {
                    rowsDeleted = sqlDB.delete(
                            ReportIncidentTable.TABLE_REPORT_INCIDENT,
                            ReportIncidentTable.COLUMN_ID + "=" + id + " and " + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsUpdated = 0;
        switch (uriType) {
            case INCIDENTREPORTS:
                rowsUpdated = sqlDB.update(ReportIncidentTable.TABLE_REPORT_INCIDENT,values,selection,selectionArgs);
                break;
            case INCIDENTREPORTS_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = sqlDB.update(ReportIncidentTable.TABLE_REPORT_INCIDENT,
                            values,
                            ReportIncidentTable.COLUMN_ID + "=" + id,
                            null);
                } else {
                    rowsUpdated = sqlDB.update(ReportIncidentTable.TABLE_REPORT_INCIDENT,
                            values,
                            ReportIncidentTable.COLUMN_ID + "=" + id
                                    + " and "
                                    + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }


    private void checkColumns(String[] projection) {
        String[] available = { ReportIncidentTable.COLUMN_REPORTED_BY,
                ReportIncidentTable.COLUMN_SURVIVOR_DATE_OF_BIRTH,
                ReportIncidentTable.COLUMN_SURVIVOR_GENDER,

                ReportIncidentTable.COLUMN_INCIDENT_TYPE,
                ReportIncidentTable.COLUMN_INCIDENT_LOCATION,
                ReportIncidentTable.COLUMN_INCIDENT_STORY,

                ReportIncidentTable.COLUMN_UNIQUE_IDENTIFIER,

                ReportIncidentTable.COLUMN_REPORTER_LOCATION_LAT,
                ReportIncidentTable.COLUMN_REPORTER_LOCATION_LNG,

                ReportIncidentTable.COLUMN_REPORTER_PHONE_NUMBER,
                ReportIncidentTable.COLUMN_REPORTER_EMAIL,

                ReportIncidentTable.COLUMN_FLAG,

                ReportIncidentTable.COLUMN_ID };

        if (projection != null) {
            HashSet<String> requestedColumns = new HashSet<String>(
                    Arrays.asList(projection));
            HashSet<String> availableColumns = new HashSet<String>(
                    Arrays.asList(available));
            // check if all columns which are requested are available
            if (!availableColumns.containsAll(requestedColumns)) {
                throw new IllegalArgumentException(
                        "Unknown columns in projection");
            }
        }
    }



}
