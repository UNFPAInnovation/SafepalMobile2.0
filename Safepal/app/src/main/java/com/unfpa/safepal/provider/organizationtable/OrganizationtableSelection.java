package com.unfpa.safepal.provider.organizationtable;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.unfpa.safepal.provider.base.AbstractSelection;

/**
 * Selection for the {@code organizationtable} table.
 */
public class OrganizationtableSelection extends AbstractSelection<OrganizationtableSelection> {
    @Override
    protected Uri baseUri() {
        return OrganizationtableColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code OrganizationtableCursor} object, which is positioned before the first entry, or null.
     */
    public OrganizationtableCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new OrganizationtableCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public OrganizationtableCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code OrganizationtableCursor} object, which is positioned before the first entry, or null.
     */
    public OrganizationtableCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new OrganizationtableCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public OrganizationtableCursor query(Context context) {
        return query(context, null);
    }


    public OrganizationtableSelection id(long... value) {
        addEquals("organizationtable." + OrganizationtableColumns._ID, toObjectArray(value));
        return this;
    }

    public OrganizationtableSelection idNot(long... value) {
        addNotEquals("organizationtable." + OrganizationtableColumns._ID, toObjectArray(value));
        return this;
    }

    public OrganizationtableSelection orderById(boolean desc) {
        orderBy("organizationtable." + OrganizationtableColumns._ID, desc);
        return this;
    }

    public OrganizationtableSelection orderById() {
        return orderById(false);
    }

    public OrganizationtableSelection serverid(Integer... value) {
        addEquals(OrganizationtableColumns.SERVERID, value);
        return this;
    }

    public OrganizationtableSelection serveridNot(Integer... value) {
        addNotEquals(OrganizationtableColumns.SERVERID, value);
        return this;
    }

    public OrganizationtableSelection serveridGt(int value) {
        addGreaterThan(OrganizationtableColumns.SERVERID, value);
        return this;
    }

    public OrganizationtableSelection serveridGtEq(int value) {
        addGreaterThanOrEquals(OrganizationtableColumns.SERVERID, value);
        return this;
    }

    public OrganizationtableSelection serveridLt(int value) {
        addLessThan(OrganizationtableColumns.SERVERID, value);
        return this;
    }

    public OrganizationtableSelection serveridLtEq(int value) {
        addLessThanOrEquals(OrganizationtableColumns.SERVERID, value);
        return this;
    }

    public OrganizationtableSelection orderByServerid(boolean desc) {
        orderBy(OrganizationtableColumns.SERVERID, desc);
        return this;
    }

    public OrganizationtableSelection orderByServerid() {
        orderBy(OrganizationtableColumns.SERVERID, false);
        return this;
    }

    public OrganizationtableSelection facilityName(String... value) {
        addEquals(OrganizationtableColumns.FACILITY_NAME, value);
        return this;
    }

    public OrganizationtableSelection facilityNameNot(String... value) {
        addNotEquals(OrganizationtableColumns.FACILITY_NAME, value);
        return this;
    }

    public OrganizationtableSelection facilityNameLike(String... value) {
        addLike(OrganizationtableColumns.FACILITY_NAME, value);
        return this;
    }

    public OrganizationtableSelection facilityNameContains(String... value) {
        addContains(OrganizationtableColumns.FACILITY_NAME, value);
        return this;
    }

    public OrganizationtableSelection facilityNameStartsWith(String... value) {
        addStartsWith(OrganizationtableColumns.FACILITY_NAME, value);
        return this;
    }

    public OrganizationtableSelection facilityNameEndsWith(String... value) {
        addEndsWith(OrganizationtableColumns.FACILITY_NAME, value);
        return this;
    }

    public OrganizationtableSelection orderByFacilityName(boolean desc) {
        orderBy(OrganizationtableColumns.FACILITY_NAME, desc);
        return this;
    }

    public OrganizationtableSelection orderByFacilityName() {
        orderBy(OrganizationtableColumns.FACILITY_NAME, false);
        return this;
    }

    public OrganizationtableSelection phoneNumber(String... value) {
        addEquals(OrganizationtableColumns.PHONE_NUMBER, value);
        return this;
    }

    public OrganizationtableSelection phoneNumberNot(String... value) {
        addNotEquals(OrganizationtableColumns.PHONE_NUMBER, value);
        return this;
    }

    public OrganizationtableSelection phoneNumberLike(String... value) {
        addLike(OrganizationtableColumns.PHONE_NUMBER, value);
        return this;
    }

    public OrganizationtableSelection phoneNumberContains(String... value) {
        addContains(OrganizationtableColumns.PHONE_NUMBER, value);
        return this;
    }

    public OrganizationtableSelection phoneNumberStartsWith(String... value) {
        addStartsWith(OrganizationtableColumns.PHONE_NUMBER, value);
        return this;
    }

    public OrganizationtableSelection phoneNumberEndsWith(String... value) {
        addEndsWith(OrganizationtableColumns.PHONE_NUMBER, value);
        return this;
    }

    public OrganizationtableSelection orderByPhoneNumber(boolean desc) {
        orderBy(OrganizationtableColumns.PHONE_NUMBER, desc);
        return this;
    }

    public OrganizationtableSelection orderByPhoneNumber() {
        orderBy(OrganizationtableColumns.PHONE_NUMBER, false);
        return this;
    }

    public OrganizationtableSelection district(String... value) {
        addEquals(OrganizationtableColumns.DISTRICT, value);
        return this;
    }

    public OrganizationtableSelection districtNot(String... value) {
        addNotEquals(OrganizationtableColumns.DISTRICT, value);
        return this;
    }

    public OrganizationtableSelection districtLike(String... value) {
        addLike(OrganizationtableColumns.DISTRICT, value);
        return this;
    }

    public OrganizationtableSelection districtContains(String... value) {
        addContains(OrganizationtableColumns.DISTRICT, value);
        return this;
    }

    public OrganizationtableSelection districtStartsWith(String... value) {
        addStartsWith(OrganizationtableColumns.DISTRICT, value);
        return this;
    }

    public OrganizationtableSelection districtEndsWith(String... value) {
        addEndsWith(OrganizationtableColumns.DISTRICT, value);
        return this;
    }

    public OrganizationtableSelection orderByDistrict(boolean desc) {
        orderBy(OrganizationtableColumns.DISTRICT, desc);
        return this;
    }

    public OrganizationtableSelection orderByDistrict() {
        orderBy(OrganizationtableColumns.DISTRICT, false);
        return this;
    }

    public OrganizationtableSelection address(String... value) {
        addEquals(OrganizationtableColumns.ADDRESS, value);
        return this;
    }

    public OrganizationtableSelection addressNot(String... value) {
        addNotEquals(OrganizationtableColumns.ADDRESS, value);
        return this;
    }

    public OrganizationtableSelection addressLike(String... value) {
        addLike(OrganizationtableColumns.ADDRESS, value);
        return this;
    }

    public OrganizationtableSelection addressContains(String... value) {
        addContains(OrganizationtableColumns.ADDRESS, value);
        return this;
    }

    public OrganizationtableSelection addressStartsWith(String... value) {
        addStartsWith(OrganizationtableColumns.ADDRESS, value);
        return this;
    }

    public OrganizationtableSelection addressEndsWith(String... value) {
        addEndsWith(OrganizationtableColumns.ADDRESS, value);
        return this;
    }

    public OrganizationtableSelection orderByAddress(boolean desc) {
        orderBy(OrganizationtableColumns.ADDRESS, desc);
        return this;
    }

    public OrganizationtableSelection orderByAddress() {
        orderBy(OrganizationtableColumns.ADDRESS, false);
        return this;
    }

    public OrganizationtableSelection openHour(String... value) {
        addEquals(OrganizationtableColumns.OPEN_HOUR, value);
        return this;
    }

    public OrganizationtableSelection openHourNot(String... value) {
        addNotEquals(OrganizationtableColumns.OPEN_HOUR, value);
        return this;
    }

    public OrganizationtableSelection openHourLike(String... value) {
        addLike(OrganizationtableColumns.OPEN_HOUR, value);
        return this;
    }

    public OrganizationtableSelection openHourContains(String... value) {
        addContains(OrganizationtableColumns.OPEN_HOUR, value);
        return this;
    }

    public OrganizationtableSelection openHourStartsWith(String... value) {
        addStartsWith(OrganizationtableColumns.OPEN_HOUR, value);
        return this;
    }

    public OrganizationtableSelection openHourEndsWith(String... value) {
        addEndsWith(OrganizationtableColumns.OPEN_HOUR, value);
        return this;
    }

    public OrganizationtableSelection orderByOpenHour(boolean desc) {
        orderBy(OrganizationtableColumns.OPEN_HOUR, desc);
        return this;
    }

    public OrganizationtableSelection orderByOpenHour() {
        orderBy(OrganizationtableColumns.OPEN_HOUR, false);
        return this;
    }

    public OrganizationtableSelection closeHour(String... value) {
        addEquals(OrganizationtableColumns.CLOSE_HOUR, value);
        return this;
    }

    public OrganizationtableSelection closeHourNot(String... value) {
        addNotEquals(OrganizationtableColumns.CLOSE_HOUR, value);
        return this;
    }

    public OrganizationtableSelection closeHourLike(String... value) {
        addLike(OrganizationtableColumns.CLOSE_HOUR, value);
        return this;
    }

    public OrganizationtableSelection closeHourContains(String... value) {
        addContains(OrganizationtableColumns.CLOSE_HOUR, value);
        return this;
    }

    public OrganizationtableSelection closeHourStartsWith(String... value) {
        addStartsWith(OrganizationtableColumns.CLOSE_HOUR, value);
        return this;
    }

    public OrganizationtableSelection closeHourEndsWith(String... value) {
        addEndsWith(OrganizationtableColumns.CLOSE_HOUR, value);
        return this;
    }

    public OrganizationtableSelection orderByCloseHour(boolean desc) {
        orderBy(OrganizationtableColumns.CLOSE_HOUR, desc);
        return this;
    }

    public OrganizationtableSelection orderByCloseHour() {
        orderBy(OrganizationtableColumns.CLOSE_HOUR, false);
        return this;
    }

    public OrganizationtableSelection link(String... value) {
        addEquals(OrganizationtableColumns.LINK, value);
        return this;
    }

    public OrganizationtableSelection linkNot(String... value) {
        addNotEquals(OrganizationtableColumns.LINK, value);
        return this;
    }

    public OrganizationtableSelection linkLike(String... value) {
        addLike(OrganizationtableColumns.LINK, value);
        return this;
    }

    public OrganizationtableSelection linkContains(String... value) {
        addContains(OrganizationtableColumns.LINK, value);
        return this;
    }

    public OrganizationtableSelection linkStartsWith(String... value) {
        addStartsWith(OrganizationtableColumns.LINK, value);
        return this;
    }

    public OrganizationtableSelection linkEndsWith(String... value) {
        addEndsWith(OrganizationtableColumns.LINK, value);
        return this;
    }

    public OrganizationtableSelection orderByLink(boolean desc) {
        orderBy(OrganizationtableColumns.LINK, desc);
        return this;
    }

    public OrganizationtableSelection orderByLink() {
        orderBy(OrganizationtableColumns.LINK, false);
        return this;
    }

    public OrganizationtableSelection latitude(Double... value) {
        addEquals(OrganizationtableColumns.LATITUDE, value);
        return this;
    }

    public OrganizationtableSelection latitudeNot(Double... value) {
        addNotEquals(OrganizationtableColumns.LATITUDE, value);
        return this;
    }

    public OrganizationtableSelection latitudeGt(double value) {
        addGreaterThan(OrganizationtableColumns.LATITUDE, value);
        return this;
    }

    public OrganizationtableSelection latitudeGtEq(double value) {
        addGreaterThanOrEquals(OrganizationtableColumns.LATITUDE, value);
        return this;
    }

    public OrganizationtableSelection latitudeLt(double value) {
        addLessThan(OrganizationtableColumns.LATITUDE, value);
        return this;
    }

    public OrganizationtableSelection latitudeLtEq(double value) {
        addLessThanOrEquals(OrganizationtableColumns.LATITUDE, value);
        return this;
    }

    public OrganizationtableSelection orderByLatitude(boolean desc) {
        orderBy(OrganizationtableColumns.LATITUDE, desc);
        return this;
    }

    public OrganizationtableSelection orderByLatitude() {
        orderBy(OrganizationtableColumns.LATITUDE, false);
        return this;
    }

    public OrganizationtableSelection longitude(Double... value) {
        addEquals(OrganizationtableColumns.LONGITUDE, value);
        return this;
    }

    public OrganizationtableSelection longitudeNot(Double... value) {
        addNotEquals(OrganizationtableColumns.LONGITUDE, value);
        return this;
    }

    public OrganizationtableSelection longitudeGt(double value) {
        addGreaterThan(OrganizationtableColumns.LONGITUDE, value);
        return this;
    }

    public OrganizationtableSelection longitudeGtEq(double value) {
        addGreaterThanOrEquals(OrganizationtableColumns.LONGITUDE, value);
        return this;
    }

    public OrganizationtableSelection longitudeLt(double value) {
        addLessThan(OrganizationtableColumns.LONGITUDE, value);
        return this;
    }

    public OrganizationtableSelection longitudeLtEq(double value) {
        addLessThanOrEquals(OrganizationtableColumns.LONGITUDE, value);
        return this;
    }

    public OrganizationtableSelection orderByLongitude(boolean desc) {
        orderBy(OrganizationtableColumns.LONGITUDE, desc);
        return this;
    }

    public OrganizationtableSelection orderByLongitude() {
        orderBy(OrganizationtableColumns.LONGITUDE, false);
        return this;
    }

    public OrganizationtableSelection createdAt(Date... value) {
        addEquals(OrganizationtableColumns.CREATED_AT, value);
        return this;
    }

    public OrganizationtableSelection createdAtNot(Date... value) {
        addNotEquals(OrganizationtableColumns.CREATED_AT, value);
        return this;
    }

    public OrganizationtableSelection createdAt(Long... value) {
        addEquals(OrganizationtableColumns.CREATED_AT, value);
        return this;
    }

    public OrganizationtableSelection createdAtAfter(Date value) {
        addGreaterThan(OrganizationtableColumns.CREATED_AT, value);
        return this;
    }

    public OrganizationtableSelection createdAtAfterEq(Date value) {
        addGreaterThanOrEquals(OrganizationtableColumns.CREATED_AT, value);
        return this;
    }

    public OrganizationtableSelection createdAtBefore(Date value) {
        addLessThan(OrganizationtableColumns.CREATED_AT, value);
        return this;
    }

    public OrganizationtableSelection createdAtBeforeEq(Date value) {
        addLessThanOrEquals(OrganizationtableColumns.CREATED_AT, value);
        return this;
    }

    public OrganizationtableSelection orderByCreatedAt(boolean desc) {
        orderBy(OrganizationtableColumns.CREATED_AT, desc);
        return this;
    }

    public OrganizationtableSelection orderByCreatedAt() {
        orderBy(OrganizationtableColumns.CREATED_AT, false);
        return this;
    }
}
