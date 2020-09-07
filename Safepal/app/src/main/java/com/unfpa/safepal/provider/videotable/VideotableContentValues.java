package com.unfpa.safepal.provider.videotable;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.unfpa.safepal.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code videotable} table.
 */
public class VideotableContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return VideotableColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable VideotableSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable VideotableSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public VideotableContentValues putServerid(@Nullable Integer value) {
        mContentValues.put(VideotableColumns.SERVERID, value);
        return this;
    }

    public VideotableContentValues putServeridNull() {
        mContentValues.putNull(VideotableColumns.SERVERID);
        return this;
    }

    public VideotableContentValues putTitle(@Nullable String value) {
        mContentValues.put(VideotableColumns.TITLE, value);
        return this;
    }

    public VideotableContentValues putTitleNull() {
        mContentValues.putNull(VideotableColumns.TITLE);
        return this;
    }

    public VideotableContentValues putDescription(@Nullable String value) {
        mContentValues.put(VideotableColumns.DESCRIPTION, value);
        return this;
    }

    public VideotableContentValues putDescriptionNull() {
        mContentValues.putNull(VideotableColumns.DESCRIPTION);
        return this;
    }

    public VideotableContentValues putCategory(@Nullable String value) {
        mContentValues.put(VideotableColumns.CATEGORY, value);
        return this;
    }

    public VideotableContentValues putCategoryNull() {
        mContentValues.putNull(VideotableColumns.CATEGORY);
        return this;
    }

    public VideotableContentValues putThumbnail(@Nullable String value) {
        mContentValues.put(VideotableColumns.THUMBNAIL, value);
        return this;
    }

    public VideotableContentValues putThumbnailNull() {
        mContentValues.putNull(VideotableColumns.THUMBNAIL);
        return this;
    }

    public VideotableContentValues putUrl(@Nullable String value) {
        mContentValues.put(VideotableColumns.URL, value);
        return this;
    }

    public VideotableContentValues putUrlNull() {
        mContentValues.putNull(VideotableColumns.URL);
        return this;
    }

    public VideotableContentValues putDuration(@Nullable Integer value) {
        mContentValues.put(VideotableColumns.DURATION, value);
        return this;
    }

    public VideotableContentValues putDurationNull() {
        mContentValues.putNull(VideotableColumns.DURATION);
        return this;
    }

    public VideotableContentValues putCompletionRate(@Nullable Integer value) {
        mContentValues.put(VideotableColumns.COMPLETION_RATE, value);
        return this;
    }

    public VideotableContentValues putCompletionRateNull() {
        mContentValues.putNull(VideotableColumns.COMPLETION_RATE);
        return this;
    }

    public VideotableContentValues putCreatedAt(@Nullable Date value) {
        mContentValues.put(VideotableColumns.CREATED_AT, value == null ? null : value.getTime());
        return this;
    }

    public VideotableContentValues putCreatedAtNull() {
        mContentValues.putNull(VideotableColumns.CREATED_AT);
        return this;
    }

    public VideotableContentValues putCreatedAt(@Nullable Long value) {
        mContentValues.put(VideotableColumns.CREATED_AT, value);
        return this;
    }

    public VideotableContentValues putRating(@Nullable Integer value) {
        mContentValues.put(VideotableColumns.RATING, value);
        return this;
    }

    public VideotableContentValues putRatingNull() {
        mContentValues.putNull(VideotableColumns.RATING);
        return this;
    }
}
