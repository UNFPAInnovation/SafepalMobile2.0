package com.unfpa.safepal.provider.districttable;

import android.net.Uri;
import android.provider.BaseColumns;

import com.unfpa.safepal.provider.SafepalProvider;

/**
 * Columns for the {@code districttable} table.
 */
public class DistricttableColumns implements BaseColumns {
    public static final String TABLE_NAME = "districttable";
    public static final Uri CONTENT_URI = Uri.parse(SafepalProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    public static final String SERVERID = "serverId";

    public static final String NAME = "name";

    public static final String CREATED_AT = "created_at";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            SERVERID,
            NAME,
            CREATED_AT
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(SERVERID) || c.contains("." + SERVERID)) return true;
            if (c.equals(NAME) || c.contains("." + NAME)) return true;
            if (c.equals(CREATED_AT) || c.contains("." + CREATED_AT)) return true;
        }
        return false;
    }

}
