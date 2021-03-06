package com.unfpa.safepal.provider.questiontable;

import android.net.Uri;
import android.provider.BaseColumns;

import com.unfpa.safepal.provider.SafepalProvider;

/**
 * Columns for the {@code questiontable} table.
 */
public class QuestiontableColumns implements BaseColumns {
    public static final String TABLE_NAME = "questiontable";
    public static final Uri CONTENT_URI = Uri.parse(SafepalProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    public static final String SERVERID = "serverId";

    public static final String QUIZ = "quiz";

    public static final String CONTENT = "content";

    public static final String DIFFICULTY = "difficulty";

    public static final String CORRECT_ANSWER = "correct_answer";

    public static final String POSITION_NUMBER = "position_number";

    public static final String CREATED_AT = "created_at";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            SERVERID,
            QUIZ,
            CONTENT,
            DIFFICULTY,
            CORRECT_ANSWER,
            POSITION_NUMBER,
            CREATED_AT
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(SERVERID) || c.contains("." + SERVERID)) return true;
            if (c.equals(QUIZ) || c.contains("." + QUIZ)) return true;
            if (c.equals(CONTENT) || c.contains("." + CONTENT)) return true;
            if (c.equals(DIFFICULTY) || c.contains("." + DIFFICULTY)) return true;
            if (c.equals(CORRECT_ANSWER) || c.contains("." + CORRECT_ANSWER)) return true;
            if (c.equals(POSITION_NUMBER) || c.contains("." + POSITION_NUMBER)) return true;
            if (c.equals(CREATED_AT) || c.contains("." + CREATED_AT)) return true;
        }
        return false;
    }

}
