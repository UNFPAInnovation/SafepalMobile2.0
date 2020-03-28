package com.unfpa.safepal.provider.organizationtable;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.unfpa.safepal.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code organizationtable} table.
 */
public class OrganizationtableContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return OrganizationtableColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable OrganizationtableSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable OrganizationtableSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public OrganizationtableContentValues putServerid(@Nullable Integer value) {
        mContentValues.put(OrganizationtableColumns.SERVERID, value);
        return this;
    }

    public OrganizationtableContentValues putServeridNull() {
        mContentValues.putNull(OrganizationtableColumns.SERVERID);
        return this;
    }

    public OrganizationtableContentValues putFacilityName(@Nullable String value) {
        mContentValues.put(OrganizationtableColumns.FACILITY_NAME, value);
        return this;
    }

    public OrganizationtableContentValues putFacilityNameNull() {
        mContentValues.putNull(OrganizationtableColumns.FACILITY_NAME);
        return this;
    }

    public OrganizationtableContentValues putPhoneNumber(@Nullable String value) {
        mContentValues.put(OrganizationtableColumns.PHONE_NUMBER, value);
        return this;
    }

    public OrganizationtableContentValues putPhoneNumberNull() {
        mContentValues.putNull(OrganizationtableColumns.PHONE_NUMBER);
        return this;
    }

    public OrganizationtableContentValues putDistrict(@Nullable String value) {
        mContentValues.put(OrganizationtableColumns.DISTRICT, value);
        return this;
    }

    public OrganizationtableContentValues putDistrictNull() {
        mContentValues.putNull(OrganizationtableColumns.DISTRICT);
        return this;
    }

    public OrganizationtableContentValues putAddress(@Nullable String value) {
        mContentValues.put(OrganizationtableColumns.ADDRESS, value);
        return this;
    }

    public OrganizationtableContentValues putAddressNull() {
        mContentValues.putNull(OrganizationtableColumns.ADDRESS);
        return this;
    }

    public OrganizationtableContentValues putOpenHour(@Nullable String value) {
        mContentValues.put(OrganizationtableColumns.OPEN_HOUR, value);
        return this;
    }

    public OrganizationtableContentValues putOpenHourNull() {
        mContentValues.putNull(OrganizationtableColumns.OPEN_HOUR);
        return this;
    }

    public OrganizationtableContentValues putCloseHour(@Nullable String value) {
        mContentValues.put(OrganizationtableColumns.CLOSE_HOUR, value);
        return this;
    }

    public OrganizationtableContentValues putCloseHourNull() {
        mContentValues.putNull(OrganizationtableColumns.CLOSE_HOUR);
        return this;
    }

    public OrganizationtableContentValues putLink(@Nullable String value) {
        mContentValues.put(OrganizationtableColumns.LINK, value);
        return this;
    }

    public OrganizationtableContentValues putLinkNull() {
        mContentValues.putNull(OrganizationtableColumns.LINK);
        return this;
    }

    public OrganizationtableContentValues putLatitude(@Nullable Double value) {
        mContentValues.put(OrganizationtableColumns.LATITUDE, value);
        return this;
    }

    public OrganizationtableContentValues putLatitudeNull() {
        mContentValues.putNull(OrganizationtableColumns.LATITUDE);
        return this;
    }

    public OrganizationtableContentValues putLongitude(@Nullable Double value) {
        mContentValues.put(OrganizationtableColumns.LONGITUDE, value);
        return this;
    }

    public OrganizationtableContentValues putLongitudeNull() {
        mContentValues.putNull(OrganizationtableColumns.LONGITUDE);
        return this;
    }

    public OrganizationtableContentValues putCreatedAt(@Nullable Date value) {
        mContentValues.put(OrganizationtableColumns.CREATED_AT, value == null ? null : value.getTime());
        return this;
    }

    public OrganizationtableContentValues putCreatedAtNull() {
        mContentValues.putNull(OrganizationtableColumns.CREATED_AT);
        return this;
    }

    public OrganizationtableContentValues putCreatedAt(@Nullable Long value) {
        mContentValues.put(OrganizationtableColumns.CREATED_AT, value);
        return this;
    }
}
