package com.unfpa.safepal.provider.videotable;

import com.unfpa.safepal.provider.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Data model for the {@code videotable} table.
 */
public interface VideotableModel extends BaseModel {

    /**
     * Get the {@code serverid} value.
     * Can be {@code null}.
     */
    @Nullable
    Integer getServerid();

    /**
     * Get the {@code title} value.
     * Can be {@code null}.
     */
    @Nullable
    String getTitle();

    /**
     * Get the {@code description} value.
     * Can be {@code null}.
     */
    @Nullable
    String getDescription();

    /**
     * Get the {@code category} value.
     * Can be {@code null}.
     */
    @Nullable
    String getCategory();

    /**
     * Get the {@code thumbnail} value.
     * Can be {@code null}.
     */
    @Nullable
    String getThumbnail();

    /**
     * Get the {@code url} value.
     * Can be {@code null}.
     */
    @Nullable
    String getUrl();

    /**
     * Get the {@code completion_rate} value.
     * Can be {@code null}.
     */
    @Nullable
    Integer getCompletionRate();

    /**
     * Get the {@code created_at} value.
     * Can be {@code null}.
     */
    @Nullable
    Date getCreatedAt();

    /**
     * Get the {@code rating} value.
     * Can be {@code null}.
     */
    @Nullable
    Integer getRating();
}
