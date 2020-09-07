package com.unfpa.safepal.provider.articletable;

import com.unfpa.safepal.provider.base.BaseModel;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Data model for the {@code articletable} table.
 */
public interface ArticletableModel extends BaseModel {

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
     * Get the {@code content} value.
     * Can be {@code null}.
     */
    @Nullable
    String getContent();

    /**
     * Get the {@code category} value.
     * Can be {@code null}.
     */
    @Nullable
    String getCategory();

    /**
     * Get the {@code questions} value.
     * Can be {@code null}.
     */
    @Nullable
    String getQuestions();

    /**
     * Get the {@code thumbnail} value.
     * Can be {@code null}.
     */
    @Nullable
    String getThumbnail();

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
