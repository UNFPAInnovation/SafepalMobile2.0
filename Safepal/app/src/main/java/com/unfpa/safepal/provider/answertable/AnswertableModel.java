package com.unfpa.safepal.provider.answertable;

import com.unfpa.safepal.provider.base.BaseModel;

import java.util.Date;

import androidx.annotation.Nullable;

/**
 * Data model for the {@code answertable} table.
 */
public interface AnswertableModel extends BaseModel {

    /**
     * Get the {@code quiz} value.
     * Can be {@code null}.
     */
    @Nullable
    Integer getQuiz();

    /**
     * Get the {@code content} value.
     * Can be {@code null}.
     */
    @Nullable
    String getContent();

    /**
     * Get the {@code correct_answer} value.
     * Can be {@code null}.
     */
    @Nullable
    String getCorrectAnswer();

    /**
     * Get the {@code your_answer} value.
     * Can be {@code null}.
     */
    @Nullable
    String getYourAnswer();

    /**
     * Get the {@code position_number} value.
     * Can be {@code null}.
     */
    @Nullable
    Integer getPositionNumber();

    /**
     * Get the {@code created_at} value.
     * Can be {@code null}.
     */
    @Nullable
    Date getCreatedAt();
}
