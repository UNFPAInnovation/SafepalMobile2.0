package com.unfpa.safepal.provider.questiontable;

import java.util.Date;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.unfpa.safepal.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code questiontable} table.
 */
public class QuestiontableCursor extends AbstractCursor implements QuestiontableModel {
    public QuestiontableCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(QuestiontableColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code serverid} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getServerid() {
        String res = getStringOrNull(QuestiontableColumns.SERVERID);
        return res;
    }

    /**
     * Get the {@code quiz} value.
     * Can be {@code null}.
     */
    @Nullable
    public Integer getQuiz() {
        Integer res = getIntegerOrNull(QuestiontableColumns.QUIZ);
        return res;
    }

    /**
     * Get the {@code content} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getContent() {
        String res = getStringOrNull(QuestiontableColumns.CONTENT);
        return res;
    }

    /**
     * Get the {@code difficulty} value.
     * Can be {@code null}.
     */
    @Nullable
    public Integer getDifficulty() {
        Integer res = getIntegerOrNull(QuestiontableColumns.DIFFICULTY);
        return res;
    }

    /**
     * Get the {@code correct_answer} value.
     * Can be {@code null}.
     */
    @Nullable
    public Integer getCorrectAnswer() {
        Integer res = getIntegerOrNull(QuestiontableColumns.CORRECT_ANSWER);
        return res;
    }

    /**
     * Get the {@code position} value.
     * Can be {@code null}.
     */
    @Nullable
    public Integer getPosition() {
        Integer res = getIntegerOrNull(QuestiontableColumns.POSITION);
        return res;
    }

    /**
     * Get the {@code created_at} value.
     * Can be {@code null}.
     */
    @Nullable
    public Date getCreatedAt() {
        Date res = getDateOrNull(QuestiontableColumns.CREATED_AT);
        return res;
    }
}