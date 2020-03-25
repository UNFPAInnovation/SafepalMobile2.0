package com.unfpa.safepal.provider.articletable;

import java.util.Date;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.unfpa.safepal.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code articletable} table.
 */
public class ArticletableCursor extends AbstractCursor implements ArticletableModel {
    public ArticletableCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(ArticletableColumns._ID);
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
        Integer res = getIntegerOrNull(ArticletableColumns.SERVERID);
        return res;
    }

    /**
     * Get the {@code title} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getTitle() {
        String res = getStringOrNull(ArticletableColumns.TITLE);
        return res;
    }

    /**
     * Get the {@code content} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getContent() {
        String res = getStringOrNull(ArticletableColumns.CONTENT);
        return res;
    }

    /**
     * Get the {@code category} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getCategory() {
        String res = getStringOrNull(ArticletableColumns.CATEGORY);
        return res;
    }

    /**
     * Get the {@code questions} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getQuestions() {
        String res = getStringOrNull(ArticletableColumns.QUESTIONS);
        return res;
    }

    /**
     * Get the {@code thumbnail} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getThumbnail() {
        String res = getStringOrNull(ArticletableColumns.THUMBNAIL);
        return res;
    }

    /**
     * Get the {@code completion_rate} value.
     * Can be {@code null}.
     */
    @Nullable
    public Integer getCompletionRate() {
        Integer res = getIntegerOrNull(ArticletableColumns.COMPLETION_RATE);
        return res;
    }

    /**
     * Get the {@code created_at} value.
     * Can be {@code null}.
     */
    @Nullable
    public Date getCreatedAt() {
        Date res = getDateOrNull(ArticletableColumns.CREATED_AT);
        return res;
    }

    /**
     * Get the {@code rating} value.
     * Can be {@code null}.
     */
    @Nullable
    public Integer getRating() {
        Integer res = getIntegerOrNull(ArticletableColumns.RATING);
        return res;
    }
}
