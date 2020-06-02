package com.unfpa.safepal.provider.answertable;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.unfpa.safepal.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code answertable} table.
 */
public class AnswertableContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return AnswertableColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable AnswertableSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable AnswertableSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public AnswertableContentValues putQuiz(@Nullable Integer value) {
        mContentValues.put(AnswertableColumns.QUIZ, value);
        return this;
    }

    public AnswertableContentValues putQuizNull() {
        mContentValues.putNull(AnswertableColumns.QUIZ);
        return this;
    }

    public AnswertableContentValues putContent(@Nullable String value) {
        mContentValues.put(AnswertableColumns.CONTENT, value);
        return this;
    }

    public AnswertableContentValues putContentNull() {
        mContentValues.putNull(AnswertableColumns.CONTENT);
        return this;
    }

    public AnswertableContentValues putCorrectAnswer(@Nullable String value) {
        mContentValues.put(AnswertableColumns.CORRECT_ANSWER, value);
        return this;
    }

    public AnswertableContentValues putCorrectAnswerNull() {
        mContentValues.putNull(AnswertableColumns.CORRECT_ANSWER);
        return this;
    }

    public AnswertableContentValues putYourAnswer(@Nullable String value) {
        mContentValues.put(AnswertableColumns.YOUR_ANSWER, value);
        return this;
    }

    public AnswertableContentValues putYourAnswerNull() {
        mContentValues.putNull(AnswertableColumns.YOUR_ANSWER);
        return this;
    }

    public AnswertableContentValues putPositionNumber(@Nullable Integer value) {
        mContentValues.put(AnswertableColumns.POSITION_NUMBER, value);
        return this;
    }

    public AnswertableContentValues putPositionNumberNull() {
        mContentValues.putNull(AnswertableColumns.POSITION_NUMBER);
        return this;
    }

    public AnswertableContentValues putCreatedAt(@Nullable Date value) {
        mContentValues.put(AnswertableColumns.CREATED_AT, value == null ? null : value.getTime());
        return this;
    }

    public AnswertableContentValues putCreatedAtNull() {
        mContentValues.putNull(AnswertableColumns.CREATED_AT);
        return this;
    }

    public AnswertableContentValues putCreatedAt(@Nullable Long value) {
        mContentValues.put(AnswertableColumns.CREATED_AT, value);
        return this;
    }
}
