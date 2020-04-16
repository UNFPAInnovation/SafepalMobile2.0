package com.unfpa.safepal.provider.quiztable;

import java.util.Date;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.unfpa.safepal.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code quiztable} table.
 */
public class QuiztableCursor extends AbstractCursor implements QuiztableModel {
    public QuiztableCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(QuiztableColumns._ID);
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
        Integer res = getIntegerOrNull(QuiztableColumns.SERVERID);
        return res;
    }

    /**
     * Get the {@code title} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getTitle() {
        String res = getStringOrNull(QuiztableColumns.TITLE);
        return res;
    }

    /**
     * Get the {@code article} value.
     * Can be {@code null}.
     */
    @Nullable
    public Integer getArticle() {
        Integer res = getIntegerOrNull(QuiztableColumns.ARTICLE);
        return res;
    }

    /**
     * Get the {@code description} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getDescription() {
        String res = getStringOrNull(QuiztableColumns.DESCRIPTION);
        return res;
    }

    /**
     * Get the {@code category} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getCategory() {
        String res = getStringOrNull(QuiztableColumns.CATEGORY);
        return res;
    }

    /**
     * Get the {@code score} value.
     * Can be {@code null}.
     */
    @Nullable
    public Integer getScore() {
        Integer res = getIntegerOrNull(QuiztableColumns.SCORE);
        return res;
    }

    /**
     * Get the {@code thumbnail} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getThumbnail() {
        String res = getStringOrNull(QuiztableColumns.THUMBNAIL);
        return res;
    }

    /**
     * Get the {@code completion_rate} value.
     * Can be {@code null}.
     */
    @Nullable
    public Integer getCompletionRate() {
        Integer res = getIntegerOrNull(QuiztableColumns.COMPLETION_RATE);
        return res;
    }

    /**
     * Get the {@code created_at} value.
     * Can be {@code null}.
     */
    @Nullable
    public Date getCreatedAt() {
        Date res = getDateOrNull(QuiztableColumns.CREATED_AT);
        return res;
    }
}
