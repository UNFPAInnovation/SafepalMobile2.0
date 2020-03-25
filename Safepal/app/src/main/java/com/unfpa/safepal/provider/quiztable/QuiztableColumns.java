package com.unfpa.safepal.provider.quiztable;

import android.net.Uri;
import android.provider.BaseColumns;

import com.unfpa.safepal.provider.SafepalProvider;
import com.unfpa.safepal.provider.articletable.ArticletableColumns;
import com.unfpa.safepal.provider.quiztable.QuiztableColumns;
import com.unfpa.safepal.provider.videotable.VideotableColumns;

/**
 * Columns for the {@code quiztable} table.
 */
public class QuiztableColumns implements BaseColumns {
    public static final String TABLE_NAME = "quiztable";
    public static final Uri CONTENT_URI = Uri.parse(SafepalProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    public static final String SERVERID = "serverId";

    public static final String TITLE = "title";

    public static final String DESCRIPTION = "description";

    public static final String CATEGORY = "category";

    public static final String SCORE = "score";

    public static final String THUMBNAIL = "thumbnail";

    public static final String COMPLETION_RATE = "completion_rate";

    public static final String CREATED_AT = "created_at";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            SERVERID,
            TITLE,
            DESCRIPTION,
            CATEGORY,
            SCORE,
            THUMBNAIL,
            COMPLETION_RATE,
            CREATED_AT
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(SERVERID) || c.contains("." + SERVERID)) return true;
            if (c.equals(TITLE) || c.contains("." + TITLE)) return true;
            if (c.equals(DESCRIPTION) || c.contains("." + DESCRIPTION)) return true;
            if (c.equals(CATEGORY) || c.contains("." + CATEGORY)) return true;
            if (c.equals(SCORE) || c.contains("." + SCORE)) return true;
            if (c.equals(THUMBNAIL) || c.contains("." + THUMBNAIL)) return true;
            if (c.equals(COMPLETION_RATE) || c.contains("." + COMPLETION_RATE)) return true;
            if (c.equals(CREATED_AT) || c.contains("." + CREATED_AT)) return true;
        }
        return false;
    }

}
