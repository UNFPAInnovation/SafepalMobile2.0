package com.unfpa.safepal.provider.faqtable;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.unfpa.safepal.provider.base.AbstractSelection;

/**
 * Selection for the {@code faqtable} table.
 */
public class FaqtableSelection extends AbstractSelection<FaqtableSelection> {
    @Override
    protected Uri baseUri() {
        return FaqtableColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code FaqtableCursor} object, which is positioned before the first entry, or null.
     */
    public FaqtableCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new FaqtableCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public FaqtableCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code FaqtableCursor} object, which is positioned before the first entry, or null.
     */
    public FaqtableCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new FaqtableCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public FaqtableCursor query(Context context) {
        return query(context, null);
    }


    public FaqtableSelection id(long... value) {
        addEquals("faqtable." + FaqtableColumns._ID, toObjectArray(value));
        return this;
    }

    public FaqtableSelection idNot(long... value) {
        addNotEquals("faqtable." + FaqtableColumns._ID, toObjectArray(value));
        return this;
    }

    public FaqtableSelection orderById(boolean desc) {
        orderBy("faqtable." + FaqtableColumns._ID, desc);
        return this;
    }

    public FaqtableSelection orderById() {
        return orderById(false);
    }

    public FaqtableSelection serverid(Integer... value) {
        addEquals(FaqtableColumns.SERVERID, value);
        return this;
    }

    public FaqtableSelection serveridNot(Integer... value) {
        addNotEquals(FaqtableColumns.SERVERID, value);
        return this;
    }

    public FaqtableSelection serveridGt(int value) {
        addGreaterThan(FaqtableColumns.SERVERID, value);
        return this;
    }

    public FaqtableSelection serveridGtEq(int value) {
        addGreaterThanOrEquals(FaqtableColumns.SERVERID, value);
        return this;
    }

    public FaqtableSelection serveridLt(int value) {
        addLessThan(FaqtableColumns.SERVERID, value);
        return this;
    }

    public FaqtableSelection serveridLtEq(int value) {
        addLessThanOrEquals(FaqtableColumns.SERVERID, value);
        return this;
    }

    public FaqtableSelection orderByServerid(boolean desc) {
        orderBy(FaqtableColumns.SERVERID, desc);
        return this;
    }

    public FaqtableSelection orderByServerid() {
        orderBy(FaqtableColumns.SERVERID, false);
        return this;
    }

    public FaqtableSelection question(String... value) {
        addEquals(FaqtableColumns.QUESTION, value);
        return this;
    }

    public FaqtableSelection questionNot(String... value) {
        addNotEquals(FaqtableColumns.QUESTION, value);
        return this;
    }

    public FaqtableSelection questionLike(String... value) {
        addLike(FaqtableColumns.QUESTION, value);
        return this;
    }

    public FaqtableSelection questionContains(String... value) {
        addContains(FaqtableColumns.QUESTION, value);
        return this;
    }

    public FaqtableSelection questionStartsWith(String... value) {
        addStartsWith(FaqtableColumns.QUESTION, value);
        return this;
    }

    public FaqtableSelection questionEndsWith(String... value) {
        addEndsWith(FaqtableColumns.QUESTION, value);
        return this;
    }

    public FaqtableSelection orderByQuestion(boolean desc) {
        orderBy(FaqtableColumns.QUESTION, desc);
        return this;
    }

    public FaqtableSelection orderByQuestion() {
        orderBy(FaqtableColumns.QUESTION, false);
        return this;
    }

    public FaqtableSelection answer(String... value) {
        addEquals(FaqtableColumns.ANSWER, value);
        return this;
    }

    public FaqtableSelection answerNot(String... value) {
        addNotEquals(FaqtableColumns.ANSWER, value);
        return this;
    }

    public FaqtableSelection answerLike(String... value) {
        addLike(FaqtableColumns.ANSWER, value);
        return this;
    }

    public FaqtableSelection answerContains(String... value) {
        addContains(FaqtableColumns.ANSWER, value);
        return this;
    }

    public FaqtableSelection answerStartsWith(String... value) {
        addStartsWith(FaqtableColumns.ANSWER, value);
        return this;
    }

    public FaqtableSelection answerEndsWith(String... value) {
        addEndsWith(FaqtableColumns.ANSWER, value);
        return this;
    }

    public FaqtableSelection orderByAnswer(boolean desc) {
        orderBy(FaqtableColumns.ANSWER, desc);
        return this;
    }

    public FaqtableSelection orderByAnswer() {
        orderBy(FaqtableColumns.ANSWER, false);
        return this;
    }

    public FaqtableSelection category(Integer... value) {
        addEquals(FaqtableColumns.CATEGORY, value);
        return this;
    }

    public FaqtableSelection categoryNot(Integer... value) {
        addNotEquals(FaqtableColumns.CATEGORY, value);
        return this;
    }

    public FaqtableSelection categoryGt(int value) {
        addGreaterThan(FaqtableColumns.CATEGORY, value);
        return this;
    }

    public FaqtableSelection categoryGtEq(int value) {
        addGreaterThanOrEquals(FaqtableColumns.CATEGORY, value);
        return this;
    }

    public FaqtableSelection categoryLt(int value) {
        addLessThan(FaqtableColumns.CATEGORY, value);
        return this;
    }

    public FaqtableSelection categoryLtEq(int value) {
        addLessThanOrEquals(FaqtableColumns.CATEGORY, value);
        return this;
    }

    public FaqtableSelection orderByCategory(boolean desc) {
        orderBy(FaqtableColumns.CATEGORY, desc);
        return this;
    }

    public FaqtableSelection orderByCategory() {
        orderBy(FaqtableColumns.CATEGORY, false);
        return this;
    }
}
