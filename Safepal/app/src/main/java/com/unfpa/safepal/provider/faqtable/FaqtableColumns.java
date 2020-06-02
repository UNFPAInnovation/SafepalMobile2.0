package com.unfpa.safepal.provider.faqtable;

import android.net.Uri;
import android.provider.BaseColumns;

import com.unfpa.safepal.provider.SafepalProvider;
import com.unfpa.safepal.provider.answertable.AnswertableColumns;
import com.unfpa.safepal.provider.articletable.ArticletableColumns;
import com.unfpa.safepal.provider.districttable.DistricttableColumns;
import com.unfpa.safepal.provider.faqtable.FaqtableColumns;
import com.unfpa.safepal.provider.organizationtable.OrganizationtableColumns;
import com.unfpa.safepal.provider.questiontable.QuestiontableColumns;
import com.unfpa.safepal.provider.quiztable.QuiztableColumns;
import com.unfpa.safepal.provider.videotable.VideotableColumns;

/**
 * Columns for the {@code faqtable} table.
 */
public class FaqtableColumns implements BaseColumns {
    public static final String TABLE_NAME = "faqtable";
    public static final Uri CONTENT_URI = Uri.parse(SafepalProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    public static final String SERVERID = "serverId";

    public static final String QUESTION = "question";

    public static final String ANSWER = "answer";

    public static final String CATEGORY = "category";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            SERVERID,
            QUESTION,
            ANSWER,
            CATEGORY
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(SERVERID) || c.contains("." + SERVERID)) return true;
            if (c.equals(QUESTION) || c.contains("." + QUESTION)) return true;
            if (c.equals(ANSWER) || c.contains("." + ANSWER)) return true;
            if (c.equals(CATEGORY) || c.contains("." + CATEGORY)) return true;
        }
        return false;
    }

}
