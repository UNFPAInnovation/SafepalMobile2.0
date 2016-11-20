package com.unfpa.safepal.store;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Kisa on 9/2/2016.
 */
public class ReportIncidentTable {

    // Database table
    public static final String TABLE_REPORT_INCIDENT = "report_incident";
    public static final String COLUMN_ID = "_id";

    public static final String COLUMN_REPORTED_BY = "reported_by";

    public static final String COLUMN_SURVIVOR_DATE_OF_BIRTH = "survivor_date_of_birth";
    public static final String COLUMN_SURVIVOR_GENDER = "survivor_gender";

    public static final String COLUMN_INCIDENT_TYPE = "incident_type";
    public static final String COLUMN_INCIDENT_LOCATION= "incident_location";
    public static final String COLUMN_INCIDENT_STORY = "incident_story";

    public static final String COLUMN_UNIQUE_IDENTIFIER = "unique_identifier";

    public static final String COLUMN_REPORTER_LOCATION_LAT = "reporter_location_lat";
    public static final String COLUMN_REPORTER_LOCATION_LNG = "reporter_location_lag";

    public static final String COLUMN_REPORTER_PHONE_NUMBER = "reporter_phone_number";
    public static final String COLUMN_REPORTER_EMAIL = "reporter_email";

    public static final String COLUMN_FLAG = "flag";




    // Database creation SQL statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_REPORT_INCIDENT
            + "("
            + COLUMN_ID + " integer primary key autoincrement, "

            + COLUMN_REPORTED_BY + " text not null, "

            + COLUMN_SURVIVOR_DATE_OF_BIRTH + " text not null, "
            + COLUMN_SURVIVOR_GENDER + " text not null, "


            + COLUMN_INCIDENT_TYPE + " text, "
            + COLUMN_INCIDENT_LOCATION + " text not null, "
            + COLUMN_INCIDENT_STORY + " text not null, "

            + COLUMN_UNIQUE_IDENTIFIER + " text, "

            + COLUMN_REPORTER_LOCATION_LAT + " text, "
            + COLUMN_REPORTER_LOCATION_LNG + " text, "

            + COLUMN_REPORTER_PHONE_NUMBER + " text, "
            + COLUMN_REPORTER_EMAIL + " text, "


            + COLUMN_FLAG + " text "
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,int newVersion) {
        Log.w(ReportIncidentTable.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_REPORT_INCIDENT);
        onCreate(database);
    }
}
