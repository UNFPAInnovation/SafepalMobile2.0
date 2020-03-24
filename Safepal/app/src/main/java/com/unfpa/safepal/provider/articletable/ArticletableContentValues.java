package com.unfpa.safepal.provider.articletable;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.unfpa.safepal.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code articletable} table.
 */
public class ArticletableContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return ArticletableColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable ArticletableSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable ArticletableSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public ArticletableContentValues putServerid(@Nullable String value) {
        mContentValues.put(ArticletableColumns.SERVERID, value);
        return this;
    }

    public ArticletableContentValues putServeridNull() {
        mContentValues.putNull(ArticletableColumns.SERVERID);
        return this;
    }

    public ArticletableContentValues putTitle(@Nullable String value) {
        mContentValues.put(ArticletableColumns.TITLE, value);
        return this;
    }

    public ArticletableContentValues putTitleNull() {
        mContentValues.putNull(ArticletableColumns.TITLE);
        return this;
    }

    public ArticletableContentValues putContent(@Nullable String value) {
        mContentValues.put(ArticletableColumns.CONTENT, value);
        return this;
    }

    public ArticletableContentValues putContentNull() {
        mContentValues.putNull(ArticletableColumns.CONTENT);
        return this;
    }

    public ArticletableContentValues putCategory(@Nullable String value) {
        mContentValues.put(ArticletableColumns.CATEGORY, value);
        return this;
    }

    public ArticletableContentValues putCategoryNull() {
        mContentValues.putNull(ArticletableColumns.CATEGORY);
        return this;
    }

    public ArticletableContentValues putQuestions(@Nullable String value) {
        mContentValues.put(ArticletableColumns.QUESTIONS, value);
        return this;
    }

    public ArticletableContentValues putQuestionsNull() {
        mContentValues.putNull(ArticletableColumns.QUESTIONS);
        return this;
    }

    public ArticletableContentValues putThumbnail(@Nullable Integer value) {
        mContentValues.put(ArticletableColumns.THUMBNAIL, value);
        return this;
    }

    public ArticletableContentValues putThumbnailNull() {
        mContentValues.putNull(ArticletableColumns.THUMBNAIL);
        return this;
    }

    public ArticletableContentValues putCompletionRate(@Nullable Integer value) {
        mContentValues.put(ArticletableColumns.COMPLETION_RATE, value);
        return this;
    }

    public ArticletableContentValues putCompletionRateNull() {
        mContentValues.putNull(ArticletableColumns.COMPLETION_RATE);
        return this;
    }

    public ArticletableContentValues putCreatedAt(@Nullable Date value) {
        mContentValues.put(ArticletableColumns.CREATED_AT, value == null ? null : value.getTime());
        return this;
    }

    public ArticletableContentValues putCreatedAtNull() {
        mContentValues.putNull(ArticletableColumns.CREATED_AT);
        return this;
    }

    public ArticletableContentValues putCreatedAt(@Nullable Long value) {
        mContentValues.put(ArticletableColumns.CREATED_AT, value);
        return this;
    }

    public ArticletableContentValues putRating(@Nullable Integer value) {
        mContentValues.put(ArticletableColumns.RATING, value);
        return this;
    }

    public ArticletableContentValues putRatingNull() {
        mContentValues.putNull(ArticletableColumns.RATING);
        return this;
    }
}
