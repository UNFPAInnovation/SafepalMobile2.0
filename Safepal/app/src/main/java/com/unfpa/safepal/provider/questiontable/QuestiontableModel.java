package com.unfpa.safepal.provider.questiontable;

import com.unfpa.safepal.provider.base.BaseModel;

import java.util.Date;

import androidx.annotation.Nullable;

/**
 * Data model for the {@code questiontable} table.
 */
public interface QuestiontableModel extends BaseModel {

    /**
     * Get the {@code serverid} value.
     * Can be {@code null}.
     */
    @Nullable
    Integer getServerid();

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
     * Get the {@code difficulty} value.
     * Can be {@code null}.
     */
    @Nullable
    Integer getDifficulty();

    /**
     * Get the {@code correct_answer} value.
     * Can be {@code null}.
     */
    @Nullable
    String getCorrectAnswer();

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
