package com.unfpa.safepal.store;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Kisa on 9/2/2016.
 */
public class ReportIncidentDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "reportincidenttable.db";
    private static final int DATABASE_VERSION = 3;

    public ReportIncidentDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Method is called during creation of the database
    @Override
    public void onCreate(SQLiteDatabase database) {
        ReportIncidentTable.onCreate(database);
    }

    // Method is called during an upgrade of the database,
    // e.g. if you increase the database version
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {

        ReportIncidentTable.onUpgrade(database, oldVersion, newVersion);
    }
}
