package com.unfpa.safepal.provider.questiontable;

import com.unfpa.safepal.provider.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Data model for the {@code questiontable} table.
 */
public interface QuestiontableModel extends BaseModel {

    /**
     * Get the {@code serverid} value.
     * Can be {@code null}.
     */
    @Nullable
    String getServerid();

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
    Integer getCorrectAnswer();

    /**
     * Get the {@code position} value.
     * Can be {@code null}.
     */
    @Nullable
    Integer getPosition();

    /**
     * Get the {@code created_at} value.
     * Can be {@code null}.
     */
    @Nullable
    Date getCreatedAt();
}
