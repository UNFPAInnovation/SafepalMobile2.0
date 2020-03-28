package com.unfpa.safepal.provider;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.DefaultDatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import com.unfpa.safepal.BuildConfig;
import com.unfpa.safepal.provider.articletable.ArticletableColumns;
import com.unfpa.safepal.provider.organizationtable.OrganizationtableColumns;
import com.unfpa.safepal.provider.quiztable.QuiztableColumns;
import com.unfpa.safepal.provider.videotable.VideotableColumns;

public class SafepalDatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = SafepalDatabaseHelper.class.getSimpleName();

    public static final String DATABASE_FILE_NAME = "safepal.db";
    private static final int DATABASE_VERSION = 1;
    private static SafepalDatabaseHelper sInstance;
    private final Context mContext;
    private final SafepalDatabaseHelperCallbacks mOpenHelperCallbacks;

    // @formatter:off
    public static final String SQL_CREATE_TABLE_ARTICLETABLE = "CREATE TABLE IF NOT EXISTS "
            + ArticletableColumns.TABLE_NAME + " ( "
            + ArticletableColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ArticletableColumns.SERVERID + " INTEGER, "
            + ArticletableColumns.TITLE + " TEXT, "
            + ArticletableColumns.CONTENT + " TEXT, "
            + ArticletableColumns.CATEGORY + " TEXT, "
            + ArticletableColumns.QUESTIONS + " TEXT, "
            + ArticletableColumns.THUMBNAIL + " TEXT, "
            + ArticletableColumns.COMPLETION_RATE + " INTEGER DEFAULT 0, "
            + ArticletableColumns.CREATED_AT + " INTEGER, "
            + ArticletableColumns.RATING + " INTEGER DEFAULT 0 "
            + " );";

    public static final String SQL_CREATE_TABLE_ORGANIZATIONTABLE = "CREATE TABLE IF NOT EXISTS "
            + OrganizationtableColumns.TABLE_NAME + " ( "
            + OrganizationtableColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + OrganizationtableColumns.SERVERID + " INTEGER, "
            + OrganizationtableColumns.FACILITY_NAME + " TEXT, "
            + OrganizationtableColumns.PHONE_NUMBER + " TEXT, "
            + OrganizationtableColumns.DISTRICT + " TEXT, "
            + OrganizationtableColumns.ADDRESS + " TEXT, "
            + OrganizationtableColumns.OPEN_HOUR + " TEXT, "
            + OrganizationtableColumns.CLOSE_HOUR + " TEXT, "
            + OrganizationtableColumns.LINK + " TEXT, "
            + OrganizationtableColumns.LATITUDE + " REAL, "
            + OrganizationtableColumns.LONGITUDE + " REAL, "
            + OrganizationtableColumns.CREATED_AT + " INTEGER "
            + " );";

    public static final String SQL_CREATE_TABLE_QUIZTABLE = "CREATE TABLE IF NOT EXISTS "
            + QuiztableColumns.TABLE_NAME + " ( "
            + QuiztableColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + QuiztableColumns.SERVERID + " INTEGER, "
            + QuiztableColumns.TITLE + " TEXT, "
            + QuiztableColumns.DESCRIPTION + " TEXT, "
            + QuiztableColumns.CATEGORY + " TEXT, "
            + QuiztableColumns.SCORE + " INTEGER DEFAULT 0, "
            + QuiztableColumns.THUMBNAIL + " INTEGER, "
            + QuiztableColumns.COMPLETION_RATE + " INTEGER DEFAULT 0, "
            + QuiztableColumns.CREATED_AT + " INTEGER "
            + " );";

    public static final String SQL_CREATE_TABLE_VIDEOTABLE = "CREATE TABLE IF NOT EXISTS "
            + VideotableColumns.TABLE_NAME + " ( "
            + VideotableColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + VideotableColumns.SERVERID + " INTEGER, "
            + VideotableColumns.TITLE + " TEXT, "
            + VideotableColumns.DESCRIPTION + " TEXT, "
            + VideotableColumns.CATEGORY + " TEXT, "
            + VideotableColumns.THUMBNAIL + " TEXT, "
            + VideotableColumns.URL + " TEXT, "
            + VideotableColumns.DURATION + " INTEGER, "
            + VideotableColumns.COMPLETION_RATE + " INTEGER DEFAULT 0, "
            + VideotableColumns.CREATED_AT + " INTEGER, "
            + VideotableColumns.RATING + " INTEGER DEFAULT 0 "
            + " );";

    // @formatter:on

    public static SafepalDatabaseHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = newInstance(context.getApplicationContext());
        }
        return sInstance;
    }

    private static SafepalDatabaseHelper newInstance(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return newInstancePreHoneycomb(context);
        }
        return newInstancePostHoneycomb(context);
    }


    /*
     * Pre Honeycomb.
     */
    private static SafepalDatabaseHelper newInstancePreHoneycomb(Context context) {
        return new SafepalDatabaseHelper(context);
    }

    private SafepalDatabaseHelper(Context context) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
        mContext = context;
        mOpenHelperCallbacks = new SafepalDatabaseHelperCallbacks();
    }


    /*
     * Post Honeycomb.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static SafepalDatabaseHelper newInstancePostHoneycomb(Context context) {
        return new SafepalDatabaseHelper(context, new DefaultDatabaseErrorHandler());
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private SafepalDatabaseHelper(Context context, DatabaseErrorHandler errorHandler) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION, errorHandler);
        mContext = context;
        mOpenHelperCallbacks = new SafepalDatabaseHelperCallbacks();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onCreate");
        mOpenHelperCallbacks.onPreCreate(mContext, db);
        db.execSQL(SQL_CREATE_TABLE_ARTICLETABLE);
        db.execSQL(SQL_CREATE_TABLE_ORGANIZATIONTABLE);
        db.execSQL(SQL_CREATE_TABLE_QUIZTABLE);
        db.execSQL(SQL_CREATE_TABLE_VIDEOTABLE);
        mOpenHelperCallbacks.onPostCreate(mContext, db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        mOpenHelperCallbacks.onOpen(mContext, db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        mOpenHelperCallbacks.onUpgrade(mContext, db, oldVersion, newVersion);
    }
}
