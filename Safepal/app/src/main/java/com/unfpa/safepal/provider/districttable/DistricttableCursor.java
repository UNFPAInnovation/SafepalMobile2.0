package com.unfpa.safepal.provider.districttable;

import java.util.Date;

import android.database.Cursor;

import androidx.annotation.Nullable;

import com.unfpa.safepal.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code districttable} table.
 */
public class DistricttableCursor extends AbstractCursor implements DistricttableModel {
    public DistricttableCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(DistricttableColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code serverid} value.
     * Can be {@code null}.
     */
    @Nullable
    public Integer getServerid() {
        Integer res = getIntegerOrNull(DistricttableColumns.SERVERID);
        return res;
    }

    /**
     * Get the {@code name} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getName() {
        String res = getStringOrNull(DistricttableColumns.NAME);
        return res;
    }

    /**
     * Get the {@code created_at} value.
     * Can be {@code null}.
     */
    @Nullable
    public Date getCreatedAt() {
        Date res = getDateOrNull(DistricttableColumns.CREATED_AT);
        return res;
    }
}
