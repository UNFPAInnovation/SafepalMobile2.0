package com.unfpa.safepal.provider.faqtable;

import com.unfpa.safepal.provider.base.BaseModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Data model for the {@code faqtable} table.
 */
public interface FaqtableModel extends BaseModel {

    /**
     * Get the {@code serverid} value.
     * Can be {@code null}.
     */
    @Nullable
    Integer getServerid();

    /**
     * Get the {@code question} value.
     * Can be {@code null}.
     */
    @Nullable
    String getQuestion();

    /**
     * Get the {@code answer} value.
     * Can be {@code null}.
     */
    @Nullable
    String getAnswer();

    /**
     * Get the {@code category} value.
     * Can be {@code null}.
     */
    @Nullable
    Integer getCategory();
}
