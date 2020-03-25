package com.unfpa.safepal.provider.quiztable;

import com.unfpa.safepal.provider.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Data model for the {@code quiztable} table.
 */
public interface QuiztableModel extends BaseModel {

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
     * Get the {@code score} value.
     * Can be {@code null}.
     */
    @Nullable
    Integer getScore();

    /**
     * Get the {@code thumbnail} value.
     * Can be {@code null}.
     */
    @Nullable
    Integer getThumbnail();

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
}
