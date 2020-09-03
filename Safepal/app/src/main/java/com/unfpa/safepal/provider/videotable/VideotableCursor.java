package com.unfpa.safepal.provider.videotable;

import java.util.Date;

import android.database.Cursor;

import androidx.annotation.Nullable;

import com.unfpa.safepal.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code videotable} table.
 */
public class VideotableCursor extends AbstractCursor implements VideotableModel {
    public VideotableCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(VideotableColumns._ID);
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
        Integer res = getIntegerOrNull(VideotableColumns.SERVERID);
        return res;
    }

    /**
     * Get the {@code title} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getTitle() {
        String res = getStringOrNull(VideotableColumns.TITLE);
        return res;
    }

    /**
     * Get the {@code description} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getDescription() {
        String res = getStringOrNull(VideotableColumns.DESCRIPTION);
        return res;
    }

    /**
     * Get the {@code category} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getCategory() {
        String res = getStringOrNull(VideotableColumns.CATEGORY);
        return res;
    }

    /**
     * Get the {@code thumbnail} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getThumbnail() {
        String res = getStringOrNull(VideotableColumns.THUMBNAIL);
        return res;
    }

    /**
     * Get the {@code url} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getUrl() {
        String res = getStringOrNull(VideotableColumns.URL);
        return res;
    }

    /**
     * Get the {@code duration} value.
     * Can be {@code null}.
     */
    @Nullable
    public Integer getDuration() {
        Integer res = getIntegerOrNull(VideotableColumns.DURATION);
        return res;
    }

    /**
     * Get the {@code completion_rate} value.
     * Can be {@code null}.
     */
    @Nullable
    public Integer getCompletionRate() {
        Integer res = getIntegerOrNull(VideotableColumns.COMPLETION_RATE);
        return res;
    }

    /**
     * Get the {@code created_at} value.
     * Can be {@code null}.
     */
    @Nullable
    public Date getCreatedAt() {
        Date res = getDateOrNull(VideotableColumns.CREATED_AT);
        return res;
    }

    /**
     * Get the {@code rating} value.
     * Can be {@code null}.
     */
    @Nullable
    public Integer getRating() {
        Integer res = getIntegerOrNull(VideotableColumns.RATING);
        return res;
    }
}
