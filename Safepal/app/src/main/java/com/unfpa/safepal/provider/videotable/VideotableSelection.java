package com.unfpa.safepal.provider.videotable;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.unfpa.safepal.provider.base.AbstractSelection;

/**
 * Selection for the {@code videotable} table.
 */
public class VideotableSelection extends AbstractSelection<VideotableSelection> {
    @Override
    protected Uri baseUri() {
        return VideotableColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code VideotableCursor} object, which is positioned before the first entry, or null.
     */
    public VideotableCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new VideotableCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public VideotableCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code VideotableCursor} object, which is positioned before the first entry, or null.
     */
    public VideotableCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new VideotableCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public VideotableCursor query(Context context) {
        return query(context, null);
    }


    public VideotableSelection id(long... value) {
        addEquals("videotable." + VideotableColumns._ID, toObjectArray(value));
        return this;
    }

    public VideotableSelection idNot(long... value) {
        addNotEquals("videotable." + VideotableColumns._ID, toObjectArray(value));
        return this;
    }

    public VideotableSelection orderById(boolean desc) {
        orderBy("videotable." + VideotableColumns._ID, desc);
        return this;
    }

    public VideotableSelection orderById() {
        return orderById(false);
    }

    public VideotableSelection serverid(Integer... value) {
        addEquals(VideotableColumns.SERVERID, value);
        return this;
    }

    public VideotableSelection serveridNot(Integer... value) {
        addNotEquals(VideotableColumns.SERVERID, value);
        return this;
    }

    public VideotableSelection serveridGt(int value) {
        addGreaterThan(VideotableColumns.SERVERID, value);
        return this;
    }

    public VideotableSelection serveridGtEq(int value) {
        addGreaterThanOrEquals(VideotableColumns.SERVERID, value);
        return this;
    }

    public VideotableSelection serveridLt(int value) {
        addLessThan(VideotableColumns.SERVERID, value);
        return this;
    }

    public VideotableSelection serveridLtEq(int value) {
        addLessThanOrEquals(VideotableColumns.SERVERID, value);
        return this;
    }

    public VideotableSelection orderByServerid(boolean desc) {
        orderBy(VideotableColumns.SERVERID, desc);
        return this;
    }

    public VideotableSelection orderByServerid() {
        orderBy(VideotableColumns.SERVERID, false);
        return this;
    }

    public VideotableSelection title(String... value) {
        addEquals(VideotableColumns.TITLE, value);
        return this;
    }

    public VideotableSelection titleNot(String... value) {
        addNotEquals(VideotableColumns.TITLE, value);
        return this;
    }

    public VideotableSelection titleLike(String... value) {
        addLike(VideotableColumns.TITLE, value);
        return this;
    }

    public VideotableSelection titleContains(String... value) {
        addContains(VideotableColumns.TITLE, value);
        return this;
    }

    public VideotableSelection titleStartsWith(String... value) {
        addStartsWith(VideotableColumns.TITLE, value);
        return this;
    }

    public VideotableSelection titleEndsWith(String... value) {
        addEndsWith(VideotableColumns.TITLE, value);
        return this;
    }

    public VideotableSelection orderByTitle(boolean desc) {
        orderBy(VideotableColumns.TITLE, desc);
        return this;
    }

    public VideotableSelection orderByTitle() {
        orderBy(VideotableColumns.TITLE, false);
        return this;
    }

    public VideotableSelection description(String... value) {
        addEquals(VideotableColumns.DESCRIPTION, value);
        return this;
    }

    public VideotableSelection descriptionNot(String... value) {
        addNotEquals(VideotableColumns.DESCRIPTION, value);
        return this;
    }

    public VideotableSelection descriptionLike(String... value) {
        addLike(VideotableColumns.DESCRIPTION, value);
        return this;
    }

    public VideotableSelection descriptionContains(String... value) {
        addContains(VideotableColumns.DESCRIPTION, value);
        return this;
    }

    public VideotableSelection descriptionStartsWith(String... value) {
        addStartsWith(VideotableColumns.DESCRIPTION, value);
        return this;
    }

    public VideotableSelection descriptionEndsWith(String... value) {
        addEndsWith(VideotableColumns.DESCRIPTION, value);
        return this;
    }

    public VideotableSelection orderByDescription(boolean desc) {
        orderBy(VideotableColumns.DESCRIPTION, desc);
        return this;
    }

    public VideotableSelection orderByDescription() {
        orderBy(VideotableColumns.DESCRIPTION, false);
        return this;
    }

    public VideotableSelection category(String... value) {
        addEquals(VideotableColumns.CATEGORY, value);
        return this;
    }

    public VideotableSelection categoryNot(String... value) {
        addNotEquals(VideotableColumns.CATEGORY, value);
        return this;
    }

    public VideotableSelection categoryLike(String... value) {
        addLike(VideotableColumns.CATEGORY, value);
        return this;
    }

    public VideotableSelection categoryContains(String... value) {
        addContains(VideotableColumns.CATEGORY, value);
        return this;
    }

    public VideotableSelection categoryStartsWith(String... value) {
        addStartsWith(VideotableColumns.CATEGORY, value);
        return this;
    }

    public VideotableSelection categoryEndsWith(String... value) {
        addEndsWith(VideotableColumns.CATEGORY, value);
        return this;
    }

    public VideotableSelection orderByCategory(boolean desc) {
        orderBy(VideotableColumns.CATEGORY, desc);
        return this;
    }

    public VideotableSelection orderByCategory() {
        orderBy(VideotableColumns.CATEGORY, false);
        return this;
    }

    public VideotableSelection thumbnail(String... value) {
        addEquals(VideotableColumns.THUMBNAIL, value);
        return this;
    }

    public VideotableSelection thumbnailNot(String... value) {
        addNotEquals(VideotableColumns.THUMBNAIL, value);
        return this;
    }

    public VideotableSelection thumbnailLike(String... value) {
        addLike(VideotableColumns.THUMBNAIL, value);
        return this;
    }

    public VideotableSelection thumbnailContains(String... value) {
        addContains(VideotableColumns.THUMBNAIL, value);
        return this;
    }

    public VideotableSelection thumbnailStartsWith(String... value) {
        addStartsWith(VideotableColumns.THUMBNAIL, value);
        return this;
    }

    public VideotableSelection thumbnailEndsWith(String... value) {
        addEndsWith(VideotableColumns.THUMBNAIL, value);
        return this;
    }

    public VideotableSelection orderByThumbnail(boolean desc) {
        orderBy(VideotableColumns.THUMBNAIL, desc);
        return this;
    }

    public VideotableSelection orderByThumbnail() {
        orderBy(VideotableColumns.THUMBNAIL, false);
        return this;
    }

    public VideotableSelection url(String... value) {
        addEquals(VideotableColumns.URL, value);
        return this;
    }

    public VideotableSelection urlNot(String... value) {
        addNotEquals(VideotableColumns.URL, value);
        return this;
    }

    public VideotableSelection urlLike(String... value) {
        addLike(VideotableColumns.URL, value);
        return this;
    }

    public VideotableSelection urlContains(String... value) {
        addContains(VideotableColumns.URL, value);
        return this;
    }

    public VideotableSelection urlStartsWith(String... value) {
        addStartsWith(VideotableColumns.URL, value);
        return this;
    }

    public VideotableSelection urlEndsWith(String... value) {
        addEndsWith(VideotableColumns.URL, value);
        return this;
    }

    public VideotableSelection orderByUrl(boolean desc) {
        orderBy(VideotableColumns.URL, desc);
        return this;
    }

    public VideotableSelection orderByUrl() {
        orderBy(VideotableColumns.URL, false);
        return this;
    }

    public VideotableSelection completionRate(Integer... value) {
        addEquals(VideotableColumns.COMPLETION_RATE, value);
        return this;
    }

    public VideotableSelection completionRateNot(Integer... value) {
        addNotEquals(VideotableColumns.COMPLETION_RATE, value);
        return this;
    }

    public VideotableSelection completionRateGt(int value) {
        addGreaterThan(VideotableColumns.COMPLETION_RATE, value);
        return this;
    }

    public VideotableSelection completionRateGtEq(int value) {
        addGreaterThanOrEquals(VideotableColumns.COMPLETION_RATE, value);
        return this;
    }

    public VideotableSelection completionRateLt(int value) {
        addLessThan(VideotableColumns.COMPLETION_RATE, value);
        return this;
    }

    public VideotableSelection completionRateLtEq(int value) {
        addLessThanOrEquals(VideotableColumns.COMPLETION_RATE, value);
        return this;
    }

    public VideotableSelection orderByCompletionRate(boolean desc) {
        orderBy(VideotableColumns.COMPLETION_RATE, desc);
        return this;
    }

    public VideotableSelection orderByCompletionRate() {
        orderBy(VideotableColumns.COMPLETION_RATE, false);
        return this;
    }

    public VideotableSelection createdAt(Date... value) {
        addEquals(VideotableColumns.CREATED_AT, value);
        return this;
    }

    public VideotableSelection createdAtNot(Date... value) {
        addNotEquals(VideotableColumns.CREATED_AT, value);
        return this;
    }

    public VideotableSelection createdAt(Long... value) {
        addEquals(VideotableColumns.CREATED_AT, value);
        return this;
    }

    public VideotableSelection createdAtAfter(Date value) {
        addGreaterThan(VideotableColumns.CREATED_AT, value);
        return this;
    }

    public VideotableSelection createdAtAfterEq(Date value) {
        addGreaterThanOrEquals(VideotableColumns.CREATED_AT, value);
        return this;
    }

    public VideotableSelection createdAtBefore(Date value) {
        addLessThan(VideotableColumns.CREATED_AT, value);
        return this;
    }

    public VideotableSelection createdAtBeforeEq(Date value) {
        addLessThanOrEquals(VideotableColumns.CREATED_AT, value);
        return this;
    }

    public VideotableSelection orderByCreatedAt(boolean desc) {
        orderBy(VideotableColumns.CREATED_AT, desc);
        return this;
    }

    public VideotableSelection orderByCreatedAt() {
        orderBy(VideotableColumns.CREATED_AT, false);
        return this;
    }

    public VideotableSelection rating(Integer... value) {
        addEquals(VideotableColumns.RATING, value);
        return this;
    }

    public VideotableSelection ratingNot(Integer... value) {
        addNotEquals(VideotableColumns.RATING, value);
        return this;
    }

    public VideotableSelection ratingGt(int value) {
        addGreaterThan(VideotableColumns.RATING, value);
        return this;
    }

    public VideotableSelection ratingGtEq(int value) {
        addGreaterThanOrEquals(VideotableColumns.RATING, value);
        return this;
    }

    public VideotableSelection ratingLt(int value) {
        addLessThan(VideotableColumns.RATING, value);
        return this;
    }

    public VideotableSelection ratingLtEq(int value) {
        addLessThanOrEquals(VideotableColumns.RATING, value);
        return this;
    }

    public VideotableSelection orderByRating(boolean desc) {
        orderBy(VideotableColumns.RATING, desc);
        return this;
    }

    public VideotableSelection orderByRating() {
        orderBy(VideotableColumns.RATING, false);
        return this;
    }
}
