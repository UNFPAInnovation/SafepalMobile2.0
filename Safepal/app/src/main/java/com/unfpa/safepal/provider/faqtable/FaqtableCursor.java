package com.unfpa.safepal.provider.faqtable;

import java.util.Date;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.unfpa.safepal.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code faqtable} table.
 */
public class FaqtableCursor extends AbstractCursor implements FaqtableModel {
    public FaqtableCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(FaqtableColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code serverid} value.
     * Can be {@code null}.
     */
    @Nullable
    public Integer getServerid() {
        Integer res = getIntegerOrNull(FaqtableColumns.SERVERID);
        return res;
    }

    /**
     * Get the {@code question} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getQuestion() {
        String res = getStringOrNull(FaqtableColumns.QUESTION);
        return res;
    }

    /**
     * Get the {@code answer} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getAnswer() {
        String res = getStringOrNull(FaqtableColumns.ANSWER);
        return res;
    }

    /**
     * Get the {@code category} value.
     * Can be {@code null}.
     */
    @Nullable
    public Integer getCategory() {
        Integer res = getIntegerOrNull(FaqtableColumns.CATEGORY);
        return res;
    }
}
