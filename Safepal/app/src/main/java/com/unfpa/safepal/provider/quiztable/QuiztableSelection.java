package com.unfpa.safepal.provider.quiztable;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.unfpa.safepal.provider.base.AbstractSelection;

/**
 * Selection for the {@code quiztable} table.
 */
public class QuiztableSelection extends AbstractSelection<QuiztableSelection> {
    @Override
    protected Uri baseUri() {
        return QuiztableColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code QuiztableCursor} object, which is positioned before the first entry, or null.
     */
    public QuiztableCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new QuiztableCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public QuiztableCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code QuiztableCursor} object, which is positioned before the first entry, or null.
     */
    public QuiztableCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new QuiztableCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public QuiztableCursor query(Context context) {
        return query(context, null);
    }


    public QuiztableSelection id(long... value) {
        addEquals("quiztable." + QuiztableColumns._ID, toObjectArray(value));
        return this;
    }

    public QuiztableSelection idNot(long... value) {
        addNotEquals("quiztable." + QuiztableColumns._ID, toObjectArray(value));
        return this;
    }

    public QuiztableSelection orderById(boolean desc) {
        orderBy("quiztable." + QuiztableColumns._ID, desc);
        return this;
    }

    public QuiztableSelection orderById() {
        return orderById(false);
    }

    public QuiztableSelection serverid(Integer... value) {
        addEquals(QuiztableColumns.SERVERID, value);
        return this;
    }

    public QuiztableSelection serveridNot(Integer... value) {
        addNotEquals(QuiztableColumns.SERVERID, value);
        return this;
    }

    public QuiztableSelection serveridGt(int value) {
        addGreaterThan(QuiztableColumns.SERVERID, value);
        return this;
    }

    public QuiztableSelection serveridGtEq(int value) {
        addGreaterThanOrEquals(QuiztableColumns.SERVERID, value);
        return this;
    }

    public QuiztableSelection serveridLt(int value) {
        addLessThan(QuiztableColumns.SERVERID, value);
        return this;
    }

    public QuiztableSelection serveridLtEq(int value) {
        addLessThanOrEquals(QuiztableColumns.SERVERID, value);
        return this;
    }

    public QuiztableSelection orderByServerid(boolean desc) {
        orderBy(QuiztableColumns.SERVERID, desc);
        return this;
    }

    public QuiztableSelection orderByServerid() {
        orderBy(QuiztableColumns.SERVERID, false);
        return this;
    }

    public QuiztableSelection title(String... value) {
        addEquals(QuiztableColumns.TITLE, value);
        return this;
    }

    public QuiztableSelection titleNot(String... value) {
        addNotEquals(QuiztableColumns.TITLE, value);
        return this;
    }

    public QuiztableSelection titleLike(String... value) {
        addLike(QuiztableColumns.TITLE, value);
        return this;
    }

    public QuiztableSelection titleContains(String... value) {
        addContains(QuiztableColumns.TITLE, value);
        return this;
    }

    public QuiztableSelection titleStartsWith(String... value) {
        addStartsWith(QuiztableColumns.TITLE, value);
        return this;
    }

    public QuiztableSelection titleEndsWith(String... value) {
        addEndsWith(QuiztableColumns.TITLE, value);
        return this;
    }

    public QuiztableSelection orderByTitle(boolean desc) {
        orderBy(QuiztableColumns.TITLE, desc);
        return this;
    }

    public QuiztableSelection orderByTitle() {
        orderBy(QuiztableColumns.TITLE, false);
        return this;
    }

    public QuiztableSelection article(Integer... value) {
        addEquals(QuiztableColumns.ARTICLE, value);
        return this;
    }

    public QuiztableSelection articleNot(Integer... value) {
        addNotEquals(QuiztableColumns.ARTICLE, value);
        return this;
    }

    public QuiztableSelection articleGt(int value) {
        addGreaterThan(QuiztableColumns.ARTICLE, value);
        return this;
    }

    public QuiztableSelection articleGtEq(int value) {
        addGreaterThanOrEquals(QuiztableColumns.ARTICLE, value);
        return this;
    }

    public QuiztableSelection articleLt(int value) {
        addLessThan(QuiztableColumns.ARTICLE, value);
        return this;
    }

    public QuiztableSelection articleLtEq(int value) {
        addLessThanOrEquals(QuiztableColumns.ARTICLE, value);
        return this;
    }

    public QuiztableSelection orderByArticle(boolean desc) {
        orderBy(QuiztableColumns.ARTICLE, desc);
        return this;
    }

    public QuiztableSelection orderByArticle() {
        orderBy(QuiztableColumns.ARTICLE, false);
        return this;
    }

    public QuiztableSelection description(String... value) {
        addEquals(QuiztableColumns.DESCRIPTION, value);
        return this;
    }

    public QuiztableSelection descriptionNot(String... value) {
        addNotEquals(QuiztableColumns.DESCRIPTION, value);
        return this;
    }

    public QuiztableSelection descriptionLike(String... value) {
        addLike(QuiztableColumns.DESCRIPTION, value);
        return this;
    }

    public QuiztableSelection descriptionContains(String... value) {
        addContains(QuiztableColumns.DESCRIPTION, value);
        return this;
    }

    public QuiztableSelection descriptionStartsWith(String... value) {
        addStartsWith(QuiztableColumns.DESCRIPTION, value);
        return this;
    }

    public QuiztableSelection descriptionEndsWith(String... value) {
        addEndsWith(QuiztableColumns.DESCRIPTION, value);
        return this;
    }

    public QuiztableSelection orderByDescription(boolean desc) {
        orderBy(QuiztableColumns.DESCRIPTION, desc);
        return this;
    }

    public QuiztableSelection orderByDescription() {
        orderBy(QuiztableColumns.DESCRIPTION, false);
        return this;
    }

    public QuiztableSelection category(String... value) {
        addEquals(QuiztableColumns.CATEGORY, value);
        return this;
    }

    public QuiztableSelection categoryNot(String... value) {
        addNotEquals(QuiztableColumns.CATEGORY, value);
        return this;
    }

    public QuiztableSelection categoryLike(String... value) {
        addLike(QuiztableColumns.CATEGORY, value);
        return this;
    }

    public QuiztableSelection categoryContains(String... value) {
        addContains(QuiztableColumns.CATEGORY, value);
        return this;
    }

    public QuiztableSelection categoryStartsWith(String... value) {
        addStartsWith(QuiztableColumns.CATEGORY, value);
        return this;
    }

    public QuiztableSelection categoryEndsWith(String... value) {
        addEndsWith(QuiztableColumns.CATEGORY, value);
        return this;
    }

    public QuiztableSelection orderByCategory(boolean desc) {
        orderBy(QuiztableColumns.CATEGORY, desc);
        return this;
    }

    public QuiztableSelection orderByCategory() {
        orderBy(QuiztableColumns.CATEGORY, false);
        return this;
    }

    public QuiztableSelection score(Integer... value) {
        addEquals(QuiztableColumns.SCORE, value);
        return this;
    }

    public QuiztableSelection scoreNot(Integer... value) {
        addNotEquals(QuiztableColumns.SCORE, value);
        return this;
    }

    public QuiztableSelection scoreGt(int value) {
        addGreaterThan(QuiztableColumns.SCORE, value);
        return this;
    }

    public QuiztableSelection scoreGtEq(int value) {
        addGreaterThanOrEquals(QuiztableColumns.SCORE, value);
        return this;
    }

    public QuiztableSelection scoreLt(int value) {
        addLessThan(QuiztableColumns.SCORE, value);
        return this;
    }

    public QuiztableSelection scoreLtEq(int value) {
        addLessThanOrEquals(QuiztableColumns.SCORE, value);
        return this;
    }

    public QuiztableSelection orderByScore(boolean desc) {
        orderBy(QuiztableColumns.SCORE, desc);
        return this;
    }

    public QuiztableSelection orderByScore() {
        orderBy(QuiztableColumns.SCORE, false);
        return this;
    }

    public QuiztableSelection thumbnail(Integer... value) {
        addEquals(QuiztableColumns.THUMBNAIL, value);
        return this;
    }

    public QuiztableSelection thumbnailNot(Integer... value) {
        addNotEquals(QuiztableColumns.THUMBNAIL, value);
        return this;
    }

    public QuiztableSelection thumbnailGt(int value) {
        addGreaterThan(QuiztableColumns.THUMBNAIL, value);
        return this;
    }

    public QuiztableSelection thumbnailGtEq(int value) {
        addGreaterThanOrEquals(QuiztableColumns.THUMBNAIL, value);
        return this;
    }

    public QuiztableSelection thumbnailLt(int value) {
        addLessThan(QuiztableColumns.THUMBNAIL, value);
        return this;
    }

    public QuiztableSelection thumbnailLtEq(int value) {
        addLessThanOrEquals(QuiztableColumns.THUMBNAIL, value);
        return this;
    }

    public QuiztableSelection orderByThumbnail(boolean desc) {
        orderBy(QuiztableColumns.THUMBNAIL, desc);
        return this;
    }

    public QuiztableSelection orderByThumbnail() {
        orderBy(QuiztableColumns.THUMBNAIL, false);
        return this;
    }

    public QuiztableSelection completionRate(Integer... value) {
        addEquals(QuiztableColumns.COMPLETION_RATE, value);
        return this;
    }

    public QuiztableSelection completionRateNot(Integer... value) {
        addNotEquals(QuiztableColumns.COMPLETION_RATE, value);
        return this;
    }

    public QuiztableSelection completionRateGt(int value) {
        addGreaterThan(QuiztableColumns.COMPLETION_RATE, value);
        return this;
    }

    public QuiztableSelection completionRateGtEq(int value) {
        addGreaterThanOrEquals(QuiztableColumns.COMPLETION_RATE, value);
        return this;
    }

    public QuiztableSelection completionRateLt(int value) {
        addLessThan(QuiztableColumns.COMPLETION_RATE, value);
        return this;
    }

    public QuiztableSelection completionRateLtEq(int value) {
        addLessThanOrEquals(QuiztableColumns.COMPLETION_RATE, value);
        return this;
    }

    public QuiztableSelection orderByCompletionRate(boolean desc) {
        orderBy(QuiztableColumns.COMPLETION_RATE, desc);
        return this;
    }

    public QuiztableSelection orderByCompletionRate() {
        orderBy(QuiztableColumns.COMPLETION_RATE, false);
        return this;
    }

    public QuiztableSelection createdAt(Date... value) {
        addEquals(QuiztableColumns.CREATED_AT, value);
        return this;
    }

    public QuiztableSelection createdAtNot(Date... value) {
        addNotEquals(QuiztableColumns.CREATED_AT, value);
        return this;
    }

    public QuiztableSelection createdAt(Long... value) {
        addEquals(QuiztableColumns.CREATED_AT, value);
        return this;
    }

    public QuiztableSelection createdAtAfter(Date value) {
        addGreaterThan(QuiztableColumns.CREATED_AT, value);
        return this;
    }

    public QuiztableSelection createdAtAfterEq(Date value) {
        addGreaterThanOrEquals(QuiztableColumns.CREATED_AT, value);
        return this;
    }

    public QuiztableSelection createdAtBefore(Date value) {
        addLessThan(QuiztableColumns.CREATED_AT, value);
        return this;
    }

    public QuiztableSelection createdAtBeforeEq(Date value) {
        addLessThanOrEquals(QuiztableColumns.CREATED_AT, value);
        return this;
    }

    public QuiztableSelection orderByCreatedAt(boolean desc) {
        orderBy(QuiztableColumns.CREATED_AT, desc);
        return this;
    }

    public QuiztableSelection orderByCreatedAt() {
        orderBy(QuiztableColumns.CREATED_AT, false);
        return this;
    }
}
