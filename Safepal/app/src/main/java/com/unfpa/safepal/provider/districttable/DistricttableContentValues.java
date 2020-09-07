package com.unfpa.safepal.provider.districttable;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.unfpa.safepal.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code districttable} table.
 */
public class DistricttableContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return DistricttableColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable DistricttableSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable DistricttableSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public DistricttableContentValues putServerid(@Nullable Integer value) {
        mContentValues.put(DistricttableColumns.SERVERID, value);
        return this;
    }

    public DistricttableContentValues putServeridNull() {
        mContentValues.putNull(DistricttableColumns.SERVERID);
        return this;
    }

    public DistricttableContentValues putName(@Nullable String value) {
        mContentValues.put(DistricttableColumns.NAME, value);
        return this;
    }

    public DistricttableContentValues putNameNull() {
        mContentValues.putNull(DistricttableColumns.NAME);
        return this;
    }

    public DistricttableContentValues putCreatedAt(@Nullable Date value) {
        mContentValues.put(DistricttableColumns.CREATED_AT, value == null ? null : value.getTime());
        return this;
    }

    public DistricttableContentValues putCreatedAtNull() {
        mContentValues.putNull(DistricttableColumns.CREATED_AT);
        return this;
    }

    public DistricttableContentValues putCreatedAt(@Nullable Long value) {
        mContentValues.put(DistricttableColumns.CREATED_AT, value);
        return this;
    }
}
