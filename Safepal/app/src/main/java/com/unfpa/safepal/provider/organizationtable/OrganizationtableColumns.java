package com.unfpa.safepal.provider.organizationtable;

import android.net.Uri;
import android.provider.BaseColumns;

import com.unfpa.safepal.provider.SafepalProvider;
import com.unfpa.safepal.provider.articletable.ArticletableColumns;
import com.unfpa.safepal.provider.organizationtable.OrganizationtableColumns;
import com.unfpa.safepal.provider.quiztable.QuiztableColumns;
import com.unfpa.safepal.provider.videotable.VideotableColumns;

/**
 * Columns for the {@code organizationtable} table.
 */
public class OrganizationtableColumns implements BaseColumns {
    public static final String TABLE_NAME = "organizationtable";
    public static final Uri CONTENT_URI = Uri.parse(SafepalProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    public static final String SERVERID = "serverId";

    public static final String FACILITY_NAME = "facility_name";

    public static final String PHONE_NUMBER = "phone_number";

    public static final String DISTRICT = "district";

    public static final String ADDRESS = "address";

    public static final String OPEN_HOUR = "open_hour";

    public static final String CLOSE_HOUR = "close_hour";

    public static final String LINK = "link";

    public static final String LATITUDE = "latitude";

    public static final String LONGITUDE = "longitude";

    public static final String CREATED_AT = "created_at";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            SERVERID,
            FACILITY_NAME,
            PHONE_NUMBER,
            DISTRICT,
            ADDRESS,
            OPEN_HOUR,
            CLOSE_HOUR,
            LINK,
            LATITUDE,
            LONGITUDE,
            CREATED_AT
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(SERVERID) || c.contains("." + SERVERID)) return true;
            if (c.equals(FACILITY_NAME) || c.contains("." + FACILITY_NAME)) return true;
            if (c.equals(PHONE_NUMBER) || c.contains("." + PHONE_NUMBER)) return true;
            if (c.equals(DISTRICT) || c.contains("." + DISTRICT)) return true;
            if (c.equals(ADDRESS) || c.contains("." + ADDRESS)) return true;
            if (c.equals(OPEN_HOUR) || c.contains("." + OPEN_HOUR)) return true;
            if (c.equals(CLOSE_HOUR) || c.contains("." + CLOSE_HOUR)) return true;
            if (c.equals(LINK) || c.contains("." + LINK)) return true;
            if (c.equals(LATITUDE) || c.contains("." + LATITUDE)) return true;
            if (c.equals(LONGITUDE) || c.contains("." + LONGITUDE)) return true;
            if (c.equals(CREATED_AT) || c.contains("." + CREATED_AT)) return true;
        }
        return false;
    }

}
