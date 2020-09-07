package com.unfpa.safepal.provider.organizationtable;

import java.util.Date;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.unfpa.safepal.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code organizationtable} table.
 */
public class OrganizationtableCursor extends AbstractCursor implements OrganizationtableModel {
    public OrganizationtableCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(OrganizationtableColumns._ID);
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
        Integer res = getIntegerOrNull(OrganizationtableColumns.SERVERID);
        return res;
    }

    /**
     * Get the {@code facility_name} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getFacilityName() {
        String res = getStringOrNull(OrganizationtableColumns.FACILITY_NAME);
        return res;
    }

    /**
     * Get the {@code phone_number} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getPhoneNumber() {
        String res = getStringOrNull(OrganizationtableColumns.PHONE_NUMBER);
        return res;
    }

    /**
     * Get the {@code district} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getDistrict() {
        String res = getStringOrNull(OrganizationtableColumns.DISTRICT);
        return res;
    }

    /**
     * Get the {@code address} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getAddress() {
        String res = getStringOrNull(OrganizationtableColumns.ADDRESS);
        return res;
    }

    /**
     * Get the {@code open_hour} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getOpenHour() {
        String res = getStringOrNull(OrganizationtableColumns.OPEN_HOUR);
        return res;
    }

    /**
     * Get the {@code close_hour} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getCloseHour() {
        String res = getStringOrNull(OrganizationtableColumns.CLOSE_HOUR);
        return res;
    }

    /**
     * Get the {@code link} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getLink() {
        String res = getStringOrNull(OrganizationtableColumns.LINK);
        return res;
    }

    /**
     * Get the {@code latitude} value.
     * Can be {@code null}.
     */
    @Nullable
    public Double getLatitude() {
        Double res = getDoubleOrNull(OrganizationtableColumns.LATITUDE);
        return res;
    }

    /**
     * Get the {@code longitude} value.
     * Can be {@code null}.
     */
    @Nullable
    public Double getLongitude() {
        Double res = getDoubleOrNull(OrganizationtableColumns.LONGITUDE);
        return res;
    }

    /**
     * Get the {@code created_at} value.
     * Can be {@code null}.
     */
    @Nullable
    public Date getCreatedAt() {
        Date res = getDateOrNull(OrganizationtableColumns.CREATED_AT);
        return res;
    }
}
