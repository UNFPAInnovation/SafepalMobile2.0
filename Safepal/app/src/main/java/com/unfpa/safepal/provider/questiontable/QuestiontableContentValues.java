package com.unfpa.safepal.provider.questiontable;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;

import androidx.annotation.Nullable;

import com.unfpa.safepal.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code questiontable} table.
 */
public class QuestiontableContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return QuestiontableColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable QuestiontableSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable QuestiontableSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public QuestiontableContentValues putServerid(@Nullable Integer value) {
        mContentValues.put(QuestiontableColumns.SERVERID, value);
        return this;
    }

    public QuestiontableContentValues putServeridNull() {
        mContentValues.putNull(QuestiontableColumns.SERVERID);
        return this;
    }

    public QuestiontableContentValues putQuiz(@Nullable Integer value) {
        mContentValues.put(QuestiontableColumns.QUIZ, value);
        return this;
    }

    public QuestiontableContentValues putQuizNull() {
        mContentValues.putNull(QuestiontableColumns.QUIZ);
        return this;
    }

    public QuestiontableContentValues putContent(@Nullable String value) {
        mContentValues.put(QuestiontableColumns.CONTENT, value);
        return this;
    }

    public QuestiontableContentValues putContentNull() {
        mContentValues.putNull(QuestiontableColumns.CONTENT);
        return this;
    }

    public QuestiontableContentValues putDifficulty(@Nullable Integer value) {
        mContentValues.put(QuestiontableColumns.DIFFICULTY, value);
        return this;
    }

    public QuestiontableContentValues putDifficultyNull() {
        mContentValues.putNull(QuestiontableColumns.DIFFICULTY);
        return this;
    }

    public QuestiontableContentValues putCorrectAnswer(@Nullable String value) {
        mContentValues.put(QuestiontableColumns.CORRECT_ANSWER, value);
        return this;
    }

    public QuestiontableContentValues putCorrectAnswerNull() {
        mContentValues.putNull(QuestiontableColumns.CORRECT_ANSWER);
        return this;
    }

    public QuestiontableContentValues putPositionNumber(@Nullable Integer value) {
        mContentValues.put(QuestiontableColumns.POSITION_NUMBER, value);
        return this;
    }

    public QuestiontableContentValues putPositionNumberNull() {
        mContentValues.putNull(QuestiontableColumns.POSITION_NUMBER);
        return this;
    }

    public QuestiontableContentValues putCreatedAt(@Nullable Date value) {
        mContentValues.put(QuestiontableColumns.CREATED_AT, value == null ? null : value.getTime());
        return this;
    }

    public QuestiontableContentValues putCreatedAtNull() {
        mContentValues.putNull(QuestiontableColumns.CREATED_AT);
        return this;
    }

    public QuestiontableContentValues putCreatedAt(@Nullable Long value) {
        mContentValues.put(QuestiontableColumns.CREATED_AT, value);
        return this;
    }
}
