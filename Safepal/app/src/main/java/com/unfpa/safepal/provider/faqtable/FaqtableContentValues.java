package com.unfpa.safepal.provider.faqtable;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.unfpa.safepal.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code faqtable} table.
 */
public class FaqtableContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return FaqtableColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable FaqtableSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable FaqtableSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public FaqtableContentValues putServerid(@Nullable Integer value) {
        mContentValues.put(FaqtableColumns.SERVERID, value);
        return this;
    }

    public FaqtableContentValues putServeridNull() {
        mContentValues.putNull(FaqtableColumns.SERVERID);
        return this;
    }

    public FaqtableContentValues putQuestion(@Nullable String value) {
        mContentValues.put(FaqtableColumns.QUESTION, value);
        return this;
    }

    public FaqtableContentValues putQuestionNull() {
        mContentValues.putNull(FaqtableColumns.QUESTION);
        return this;
    }

    public FaqtableContentValues putAnswer(@Nullable String value) {
        mContentValues.put(FaqtableColumns.ANSWER, value);
        return this;
    }

    public FaqtableContentValues putAnswerNull() {
        mContentValues.putNull(FaqtableColumns.ANSWER);
        return this;
    }

    public FaqtableContentValues putCategory(@Nullable Integer value) {
        mContentValues.put(FaqtableColumns.CATEGORY, value);
        return this;
    }

    public FaqtableContentValues putCategoryNull() {
        mContentValues.putNull(FaqtableColumns.CATEGORY);
        return this;
    }
}
