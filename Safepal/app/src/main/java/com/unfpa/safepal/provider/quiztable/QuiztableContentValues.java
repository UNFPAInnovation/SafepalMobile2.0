package com.unfpa.safepal.provider.quiztable;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.unfpa.safepal.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code quiztable} table.
 */
public class QuiztableContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return QuiztableColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable QuiztableSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable QuiztableSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public QuiztableContentValues putServerid(@Nullable String value) {
        mContentValues.put(QuiztableColumns.SERVERID, value);
        return this;
    }

    public QuiztableContentValues putServeridNull() {
        mContentValues.putNull(QuiztableColumns.SERVERID);
        return this;
    }

    public QuiztableContentValues putTitle(@Nullable String value) {
        mContentValues.put(QuiztableColumns.TITLE, value);
        return this;
    }

    public QuiztableContentValues putTitleNull() {
        mContentValues.putNull(QuiztableColumns.TITLE);
        return this;
    }

    public QuiztableContentValues putDescription(@Nullable String value) {
        mContentValues.put(QuiztableColumns.DESCRIPTION, value);
        return this;
    }

    public QuiztableContentValues putDescriptionNull() {
        mContentValues.putNull(QuiztableColumns.DESCRIPTION);
        return this;
    }

    public QuiztableContentValues putCategory(@Nullable String value) {
        mContentValues.put(QuiztableColumns.CATEGORY, value);
        return this;
    }

    public QuiztableContentValues putCategoryNull() {
        mContentValues.putNull(QuiztableColumns.CATEGORY);
        return this;
    }

    public QuiztableContentValues putScore(@Nullable String value) {
        mContentValues.put(QuiztableColumns.SCORE, value);
        return this;
    }

    public QuiztableContentValues putScoreNull() {
        mContentValues.putNull(QuiztableColumns.SCORE);
        return this;
    }

    public QuiztableContentValues putThumbnail(@Nullable Integer value) {
        mContentValues.put(QuiztableColumns.THUMBNAIL, value);
        return this;
    }

    public QuiztableContentValues putThumbnailNull() {
        mContentValues.putNull(QuiztableColumns.THUMBNAIL);
        return this;
    }

    public QuiztableContentValues putCompletionRate(@Nullable Integer value) {
        mContentValues.put(QuiztableColumns.COMPLETION_RATE, value);
        return this;
    }

    public QuiztableContentValues putCompletionRateNull() {
        mContentValues.putNull(QuiztableColumns.COMPLETION_RATE);
        return this;
    }

    public QuiztableContentValues putCreatedAt(@Nullable Date value) {
        mContentValues.put(QuiztableColumns.CREATED_AT, value == null ? null : value.getTime());
        return this;
    }

    public QuiztableContentValues putCreatedAtNull() {
        mContentValues.putNull(QuiztableColumns.CREATED_AT);
        return this;
    }

    public QuiztableContentValues putCreatedAt(@Nullable Long value) {
        mContentValues.put(QuiztableColumns.CREATED_AT, value);
        return this;
    }
}
