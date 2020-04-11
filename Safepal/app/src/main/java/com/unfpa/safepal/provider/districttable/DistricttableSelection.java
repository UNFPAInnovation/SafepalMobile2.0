package com.unfpa.safepal.provider.districttable;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.unfpa.safepal.provider.base.AbstractSelection;

/**
 * Selection for the {@code districttable} table.
 */
public class DistricttableSelection extends AbstractSelection<DistricttableSelection> {
    @Override
    protected Uri baseUri() {
        return DistricttableColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code DistricttableCursor} object, which is positioned before the first entry, or null.
     */
    public DistricttableCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new DistricttableCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public DistricttableCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code DistricttableCursor} object, which is positioned before the first entry, or null.
     */
    public DistricttableCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new DistricttableCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public DistricttableCursor query(Context context) {
        return query(context, null);
    }


    public DistricttableSelection id(long... value) {
        addEquals("districttable." + DistricttableColumns._ID, toObjectArray(value));
        return this;
    }

    public DistricttableSelection idNot(long... value) {
        addNotEquals("districttable." + DistricttableColumns._ID, toObjectArray(value));
        return this;
    }

    public DistricttableSelection orderById(boolean desc) {
        orderBy("districttable." + DistricttableColumns._ID, desc);
        return this;
    }

    public DistricttableSelection orderById() {
        return orderById(false);
    }

    public DistricttableSelection serverid(Integer... value) {
        addEquals(DistricttableColumns.SERVERID, value);
        return this;
    }

    public DistricttableSelection serveridNot(Integer... value) {
        addNotEquals(DistricttableColumns.SERVERID, value);
        return this;
    }

    public DistricttableSelection serveridGt(int value) {
        addGreaterThan(DistricttableColumns.SERVERID, value);
        return this;
    }

    public DistricttableSelection serveridGtEq(int value) {
        addGreaterThanOrEquals(DistricttableColumns.SERVERID, value);
        return this;
    }

    public DistricttableSelection serveridLt(int value) {
        addLessThan(DistricttableColumns.SERVERID, value);
        return this;
    }

    public DistricttableSelection serveridLtEq(int value) {
        addLessThanOrEquals(DistricttableColumns.SERVERID, value);
        return this;
    }

    public DistricttableSelection orderByServerid(boolean desc) {
        orderBy(DistricttableColumns.SERVERID, desc);
        return this;
    }

    public DistricttableSelection orderByServerid() {
        orderBy(DistricttableColumns.SERVERID, false);
        return this;
    }

    public DistricttableSelection name(String... value) {
        addEquals(DistricttableColumns.NAME, value);
        return this;
    }

    public DistricttableSelection nameNot(String... value) {
        addNotEquals(DistricttableColumns.NAME, value);
        return this;
    }

    public DistricttableSelection nameLike(String... value) {
        addLike(DistricttableColumns.NAME, value);
        return this;
    }

    public DistricttableSelection nameContains(String... value) {
        addContains(DistricttableColumns.NAME, value);
        return this;
    }

    public DistricttableSelection nameStartsWith(String... value) {
        addStartsWith(DistricttableColumns.NAME, value);
        return this;
    }

    public DistricttableSelection nameEndsWith(String... value) {
        addEndsWith(DistricttableColumns.NAME, value);
        return this;
    }

    public DistricttableSelection orderByName(boolean desc) {
        orderBy(DistricttableColumns.NAME, desc);
        return this;
    }

    public DistricttableSelection orderByName() {
        orderBy(DistricttableColumns.NAME, false);
        return this;
    }

    public DistricttableSelection createdAt(Date... value) {
        addEquals(DistricttableColumns.CREATED_AT, value);
        return this;
    }

    public DistricttableSelection createdAtNot(Date... value) {
        addNotEquals(DistricttableColumns.CREATED_AT, value);
        return this;
    }

    public DistricttableSelection createdAt(Long... value) {
        addEquals(DistricttableColumns.CREATED_AT, value);
        return this;
    }

    public DistricttableSelection createdAtAfter(Date value) {
        addGreaterThan(DistricttableColumns.CREATED_AT, value);
        return this;
    }

    public DistricttableSelection createdAtAfterEq(Date value) {
        addGreaterThanOrEquals(DistricttableColumns.CREATED_AT, value);
        return this;
    }

    public DistricttableSelection createdAtBefore(Date value) {
        addLessThan(DistricttableColumns.CREATED_AT, value);
        return this;
    }

    public DistricttableSelection createdAtBeforeEq(Date value) {
        addLessThanOrEquals(DistricttableColumns.CREATED_AT, value);
        return this;
    }

    public DistricttableSelection orderByCreatedAt(boolean desc) {
        orderBy(DistricttableColumns.CREATED_AT, desc);
        return this;
    }

    public DistricttableSelection orderByCreatedAt() {
        orderBy(DistricttableColumns.CREATED_AT, false);
        return this;
    }
}
