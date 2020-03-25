package com.unfpa.safepal.provider.articletable;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.unfpa.safepal.provider.base.AbstractSelection;

/**
 * Selection for the {@code articletable} table.
 */
public class ArticletableSelection extends AbstractSelection<ArticletableSelection> {
    @Override
    protected Uri baseUri() {
        return ArticletableColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code ArticletableCursor} object, which is positioned before the first entry, or null.
     */
    public ArticletableCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new ArticletableCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public ArticletableCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code ArticletableCursor} object, which is positioned before the first entry, or null.
     */
    public ArticletableCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new ArticletableCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public ArticletableCursor query(Context context) {
        return query(context, null);
    }


    public ArticletableSelection id(long... value) {
        addEquals("articletable." + ArticletableColumns._ID, toObjectArray(value));
        return this;
    }

    public ArticletableSelection idNot(long... value) {
        addNotEquals("articletable." + ArticletableColumns._ID, toObjectArray(value));
        return this;
    }

    public ArticletableSelection orderById(boolean desc) {
        orderBy("articletable." + ArticletableColumns._ID, desc);
        return this;
    }

    public ArticletableSelection orderById() {
        return orderById(false);
    }

    public ArticletableSelection serverid(Integer... value) {
        addEquals(ArticletableColumns.SERVERID, value);
        return this;
    }

    public ArticletableSelection serveridNot(Integer... value) {
        addNotEquals(ArticletableColumns.SERVERID, value);
        return this;
    }

    public ArticletableSelection serveridGt(int value) {
        addGreaterThan(ArticletableColumns.SERVERID, value);
        return this;
    }

    public ArticletableSelection serveridGtEq(int value) {
        addGreaterThanOrEquals(ArticletableColumns.SERVERID, value);
        return this;
    }

    public ArticletableSelection serveridLt(int value) {
        addLessThan(ArticletableColumns.SERVERID, value);
        return this;
    }

    public ArticletableSelection serveridLtEq(int value) {
        addLessThanOrEquals(ArticletableColumns.SERVERID, value);
        return this;
    }

    public ArticletableSelection orderByServerid(boolean desc) {
        orderBy(ArticletableColumns.SERVERID, desc);
        return this;
    }

    public ArticletableSelection orderByServerid() {
        orderBy(ArticletableColumns.SERVERID, false);
        return this;
    }

    public ArticletableSelection title(String... value) {
        addEquals(ArticletableColumns.TITLE, value);
        return this;
    }

    public ArticletableSelection titleNot(String... value) {
        addNotEquals(ArticletableColumns.TITLE, value);
        return this;
    }

    public ArticletableSelection titleLike(String... value) {
        addLike(ArticletableColumns.TITLE, value);
        return this;
    }

    public ArticletableSelection titleContains(String... value) {
        addContains(ArticletableColumns.TITLE, value);
        return this;
    }

    public ArticletableSelection titleStartsWith(String... value) {
        addStartsWith(ArticletableColumns.TITLE, value);
        return this;
    }

    public ArticletableSelection titleEndsWith(String... value) {
        addEndsWith(ArticletableColumns.TITLE, value);
        return this;
    }

    public ArticletableSelection orderByTitle(boolean desc) {
        orderBy(ArticletableColumns.TITLE, desc);
        return this;
    }

    public ArticletableSelection orderByTitle() {
        orderBy(ArticletableColumns.TITLE, false);
        return this;
    }

    public ArticletableSelection content(String... value) {
        addEquals(ArticletableColumns.CONTENT, value);
        return this;
    }

    public ArticletableSelection contentNot(String... value) {
        addNotEquals(ArticletableColumns.CONTENT, value);
        return this;
    }

    public ArticletableSelection contentLike(String... value) {
        addLike(ArticletableColumns.CONTENT, value);
        return this;
    }

    public ArticletableSelection contentContains(String... value) {
        addContains(ArticletableColumns.CONTENT, value);
        return this;
    }

    public ArticletableSelection contentStartsWith(String... value) {
        addStartsWith(ArticletableColumns.CONTENT, value);
        return this;
    }

    public ArticletableSelection contentEndsWith(String... value) {
        addEndsWith(ArticletableColumns.CONTENT, value);
        return this;
    }

    public ArticletableSelection orderByContent(boolean desc) {
        orderBy(ArticletableColumns.CONTENT, desc);
        return this;
    }

    public ArticletableSelection orderByContent() {
        orderBy(ArticletableColumns.CONTENT, false);
        return this;
    }

    public ArticletableSelection category(String... value) {
        addEquals(ArticletableColumns.CATEGORY, value);
        return this;
    }

    public ArticletableSelection categoryNot(String... value) {
        addNotEquals(ArticletableColumns.CATEGORY, value);
        return this;
    }

    public ArticletableSelection categoryLike(String... value) {
        addLike(ArticletableColumns.CATEGORY, value);
        return this;
    }

    public ArticletableSelection categoryContains(String... value) {
        addContains(ArticletableColumns.CATEGORY, value);
        return this;
    }

    public ArticletableSelection categoryStartsWith(String... value) {
        addStartsWith(ArticletableColumns.CATEGORY, value);
        return this;
    }

    public ArticletableSelection categoryEndsWith(String... value) {
        addEndsWith(ArticletableColumns.CATEGORY, value);
        return this;
    }

    public ArticletableSelection orderByCategory(boolean desc) {
        orderBy(ArticletableColumns.CATEGORY, desc);
        return this;
    }

    public ArticletableSelection orderByCategory() {
        orderBy(ArticletableColumns.CATEGORY, false);
        return this;
    }

    public ArticletableSelection questions(String... value) {
        addEquals(ArticletableColumns.QUESTIONS, value);
        return this;
    }

    public ArticletableSelection questionsNot(String... value) {
        addNotEquals(ArticletableColumns.QUESTIONS, value);
        return this;
    }

    public ArticletableSelection questionsLike(String... value) {
        addLike(ArticletableColumns.QUESTIONS, value);
        return this;
    }

    public ArticletableSelection questionsContains(String... value) {
        addContains(ArticletableColumns.QUESTIONS, value);
        return this;
    }

    public ArticletableSelection questionsStartsWith(String... value) {
        addStartsWith(ArticletableColumns.QUESTIONS, value);
        return this;
    }

    public ArticletableSelection questionsEndsWith(String... value) {
        addEndsWith(ArticletableColumns.QUESTIONS, value);
        return this;
    }

    public ArticletableSelection orderByQuestions(boolean desc) {
        orderBy(ArticletableColumns.QUESTIONS, desc);
        return this;
    }

    public ArticletableSelection orderByQuestions() {
        orderBy(ArticletableColumns.QUESTIONS, false);
        return this;
    }

    public ArticletableSelection thumbnail(String... value) {
        addEquals(ArticletableColumns.THUMBNAIL, value);
        return this;
    }

    public ArticletableSelection thumbnailNot(String... value) {
        addNotEquals(ArticletableColumns.THUMBNAIL, value);
        return this;
    }

    public ArticletableSelection thumbnailLike(String... value) {
        addLike(ArticletableColumns.THUMBNAIL, value);
        return this;
    }

    public ArticletableSelection thumbnailContains(String... value) {
        addContains(ArticletableColumns.THUMBNAIL, value);
        return this;
    }

    public ArticletableSelection thumbnailStartsWith(String... value) {
        addStartsWith(ArticletableColumns.THUMBNAIL, value);
        return this;
    }

    public ArticletableSelection thumbnailEndsWith(String... value) {
        addEndsWith(ArticletableColumns.THUMBNAIL, value);
        return this;
    }

    public ArticletableSelection orderByThumbnail(boolean desc) {
        orderBy(ArticletableColumns.THUMBNAIL, desc);
        return this;
    }

    public ArticletableSelection orderByThumbnail() {
        orderBy(ArticletableColumns.THUMBNAIL, false);
        return this;
    }

    public ArticletableSelection completionRate(Integer... value) {
        addEquals(ArticletableColumns.COMPLETION_RATE, value);
        return this;
    }

    public ArticletableSelection completionRateNot(Integer... value) {
        addNotEquals(ArticletableColumns.COMPLETION_RATE, value);
        return this;
    }

    public ArticletableSelection completionRateGt(int value) {
        addGreaterThan(ArticletableColumns.COMPLETION_RATE, value);
        return this;
    }

    public ArticletableSelection completionRateGtEq(int value) {
        addGreaterThanOrEquals(ArticletableColumns.COMPLETION_RATE, value);
        return this;
    }

    public ArticletableSelection completionRateLt(int value) {
        addLessThan(ArticletableColumns.COMPLETION_RATE, value);
        return this;
    }

    public ArticletableSelection completionRateLtEq(int value) {
        addLessThanOrEquals(ArticletableColumns.COMPLETION_RATE, value);
        return this;
    }

    public ArticletableSelection orderByCompletionRate(boolean desc) {
        orderBy(ArticletableColumns.COMPLETION_RATE, desc);
        return this;
    }

    public ArticletableSelection orderByCompletionRate() {
        orderBy(ArticletableColumns.COMPLETION_RATE, false);
        return this;
    }

    public ArticletableSelection createdAt(Date... value) {
        addEquals(ArticletableColumns.CREATED_AT, value);
        return this;
    }

    public ArticletableSelection createdAtNot(Date... value) {
        addNotEquals(ArticletableColumns.CREATED_AT, value);
        return this;
    }

    public ArticletableSelection createdAt(Long... value) {
        addEquals(ArticletableColumns.CREATED_AT, value);
        return this;
    }

    public ArticletableSelection createdAtAfter(Date value) {
        addGreaterThan(ArticletableColumns.CREATED_AT, value);
        return this;
    }

    public ArticletableSelection createdAtAfterEq(Date value) {
        addGreaterThanOrEquals(ArticletableColumns.CREATED_AT, value);
        return this;
    }

    public ArticletableSelection createdAtBefore(Date value) {
        addLessThan(ArticletableColumns.CREATED_AT, value);
        return this;
    }

    public ArticletableSelection createdAtBeforeEq(Date value) {
        addLessThanOrEquals(ArticletableColumns.CREATED_AT, value);
        return this;
    }

    public ArticletableSelection orderByCreatedAt(boolean desc) {
        orderBy(ArticletableColumns.CREATED_AT, desc);
        return this;
    }

    public ArticletableSelection orderByCreatedAt() {
        orderBy(ArticletableColumns.CREATED_AT, false);
        return this;
    }

    public ArticletableSelection rating(Integer... value) {
        addEquals(ArticletableColumns.RATING, value);
        return this;
    }

    public ArticletableSelection ratingNot(Integer... value) {
        addNotEquals(ArticletableColumns.RATING, value);
        return this;
    }

    public ArticletableSelection ratingGt(int value) {
        addGreaterThan(ArticletableColumns.RATING, value);
        return this;
    }

    public ArticletableSelection ratingGtEq(int value) {
        addGreaterThanOrEquals(ArticletableColumns.RATING, value);
        return this;
    }

    public ArticletableSelection ratingLt(int value) {
        addLessThan(ArticletableColumns.RATING, value);
        return this;
    }

    public ArticletableSelection ratingLtEq(int value) {
        addLessThanOrEquals(ArticletableColumns.RATING, value);
        return this;
    }

    public ArticletableSelection orderByRating(boolean desc) {
        orderBy(ArticletableColumns.RATING, desc);
        return this;
    }

    public ArticletableSelection orderByRating() {
        orderBy(ArticletableColumns.RATING, false);
        return this;
    }
}
