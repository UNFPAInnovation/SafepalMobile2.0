package com.unfpa.safepal.provider.answertable;

import java.util.Date;

import android.database.Cursor;

import androidx.annotation.Nullable;

import com.unfpa.safepal.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code answertable} table.
 */
public class AnswertableCursor extends AbstractCursor implements AnswertableModel {
    public AnswertableCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(AnswertableColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code quiz} value.
     * Can be {@code null}.
     */
    @Nullable
    public Integer getQuiz() {
        Integer res = getIntegerOrNull(AnswertableColumns.QUIZ);
        return res;
    }

    /**
     * Get the {@code content} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getContent() {
        String res = getStringOrNull(AnswertableColumns.CONTENT);
        return res;
    }

    /**
     * Get the {@code correct_answer} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getCorrectAnswer() {
        String res = getStringOrNull(AnswertableColumns.CORRECT_ANSWER);
        return res;
    }

    /**
     * Get the {@code your_answer} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getYourAnswer() {
        String res = getStringOrNull(AnswertableColumns.YOUR_ANSWER);
        return res;
    }

    /**
     * Get the {@code position_number} value.
     * Can be {@code null}.
     */
    @Nullable
    public Integer getPositionNumber() {
        Integer res = getIntegerOrNull(AnswertableColumns.POSITION_NUMBER);
        return res;
    }

    /**
     * Get the {@code created_at} value.
     * Can be {@code null}.
     */
    @Nullable
    public Date getCreatedAt() {
        Date res = getDateOrNull(AnswertableColumns.CREATED_AT);
        return res;
    }
}
