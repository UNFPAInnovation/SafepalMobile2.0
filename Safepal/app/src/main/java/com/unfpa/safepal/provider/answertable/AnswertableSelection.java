package com.unfpa.safepal.provider.answertable;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.unfpa.safepal.provider.base.AbstractSelection;

/**
 * Selection for the {@code answertable} table.
 */
public class AnswertableSelection extends AbstractSelection<AnswertableSelection> {
    @Override
    protected Uri baseUri() {
        return AnswertableColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code AnswertableCursor} object, which is positioned before the first entry, or null.
     */
    public AnswertableCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new AnswertableCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public AnswertableCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code AnswertableCursor} object, which is positioned before the first entry, or null.
     */
    public AnswertableCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new AnswertableCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public AnswertableCursor query(Context context) {
        return query(context, null);
    }


    public AnswertableSelection id(long... value) {
        addEquals("answertable." + AnswertableColumns._ID, toObjectArray(value));
        return this;
    }

    public AnswertableSelection idNot(long... value) {
        addNotEquals("answertable." + AnswertableColumns._ID, toObjectArray(value));
        return this;
    }

    public AnswertableSelection orderById(boolean desc) {
        orderBy("answertable." + AnswertableColumns._ID, desc);
        return this;
    }

    public AnswertableSelection orderById() {
        return orderById(false);
    }

    public AnswertableSelection quiz(Integer... value) {
        addEquals(AnswertableColumns.QUIZ, value);
        return this;
    }

    public AnswertableSelection quizNot(Integer... value) {
        addNotEquals(AnswertableColumns.QUIZ, value);
        return this;
    }

    public AnswertableSelection quizGt(int value) {
        addGreaterThan(AnswertableColumns.QUIZ, value);
        return this;
    }

    public AnswertableSelection quizGtEq(int value) {
        addGreaterThanOrEquals(AnswertableColumns.QUIZ, value);
        return this;
    }

    public AnswertableSelection quizLt(int value) {
        addLessThan(AnswertableColumns.QUIZ, value);
        return this;
    }

    public AnswertableSelection quizLtEq(int value) {
        addLessThanOrEquals(AnswertableColumns.QUIZ, value);
        return this;
    }

    public AnswertableSelection orderByQuiz(boolean desc) {
        orderBy(AnswertableColumns.QUIZ, desc);
        return this;
    }

    public AnswertableSelection orderByQuiz() {
        orderBy(AnswertableColumns.QUIZ, false);
        return this;
    }

    public AnswertableSelection content(String... value) {
        addEquals(AnswertableColumns.CONTENT, value);
        return this;
    }

    public AnswertableSelection contentNot(String... value) {
        addNotEquals(AnswertableColumns.CONTENT, value);
        return this;
    }

    public AnswertableSelection contentLike(String... value) {
        addLike(AnswertableColumns.CONTENT, value);
        return this;
    }

    public AnswertableSelection contentContains(String... value) {
        addContains(AnswertableColumns.CONTENT, value);
        return this;
    }

    public AnswertableSelection contentStartsWith(String... value) {
        addStartsWith(AnswertableColumns.CONTENT, value);
        return this;
    }

    public AnswertableSelection contentEndsWith(String... value) {
        addEndsWith(AnswertableColumns.CONTENT, value);
        return this;
    }

    public AnswertableSelection orderByContent(boolean desc) {
        orderBy(AnswertableColumns.CONTENT, desc);
        return this;
    }

    public AnswertableSelection orderByContent() {
        orderBy(AnswertableColumns.CONTENT, false);
        return this;
    }

    public AnswertableSelection correctAnswer(String... value) {
        addEquals(AnswertableColumns.CORRECT_ANSWER, value);
        return this;
    }

    public AnswertableSelection correctAnswerNot(String... value) {
        addNotEquals(AnswertableColumns.CORRECT_ANSWER, value);
        return this;
    }

    public AnswertableSelection correctAnswerLike(String... value) {
        addLike(AnswertableColumns.CORRECT_ANSWER, value);
        return this;
    }

    public AnswertableSelection correctAnswerContains(String... value) {
        addContains(AnswertableColumns.CORRECT_ANSWER, value);
        return this;
    }

    public AnswertableSelection correctAnswerStartsWith(String... value) {
        addStartsWith(AnswertableColumns.CORRECT_ANSWER, value);
        return this;
    }

    public AnswertableSelection correctAnswerEndsWith(String... value) {
        addEndsWith(AnswertableColumns.CORRECT_ANSWER, value);
        return this;
    }

    public AnswertableSelection orderByCorrectAnswer(boolean desc) {
        orderBy(AnswertableColumns.CORRECT_ANSWER, desc);
        return this;
    }

    public AnswertableSelection orderByCorrectAnswer() {
        orderBy(AnswertableColumns.CORRECT_ANSWER, false);
        return this;
    }

    public AnswertableSelection yourAnswer(String... value) {
        addEquals(AnswertableColumns.YOUR_ANSWER, value);
        return this;
    }

    public AnswertableSelection yourAnswerNot(String... value) {
        addNotEquals(AnswertableColumns.YOUR_ANSWER, value);
        return this;
    }

    public AnswertableSelection yourAnswerLike(String... value) {
        addLike(AnswertableColumns.YOUR_ANSWER, value);
        return this;
    }

    public AnswertableSelection yourAnswerContains(String... value) {
        addContains(AnswertableColumns.YOUR_ANSWER, value);
        return this;
    }

    public AnswertableSelection yourAnswerStartsWith(String... value) {
        addStartsWith(AnswertableColumns.YOUR_ANSWER, value);
        return this;
    }

    public AnswertableSelection yourAnswerEndsWith(String... value) {
        addEndsWith(AnswertableColumns.YOUR_ANSWER, value);
        return this;
    }

    public AnswertableSelection orderByYourAnswer(boolean desc) {
        orderBy(AnswertableColumns.YOUR_ANSWER, desc);
        return this;
    }

    public AnswertableSelection orderByYourAnswer() {
        orderBy(AnswertableColumns.YOUR_ANSWER, false);
        return this;
    }

    public AnswertableSelection positionNumber(Integer... value) {
        addEquals(AnswertableColumns.POSITION_NUMBER, value);
        return this;
    }

    public AnswertableSelection positionNumberNot(Integer... value) {
        addNotEquals(AnswertableColumns.POSITION_NUMBER, value);
        return this;
    }

    public AnswertableSelection positionNumberGt(int value) {
        addGreaterThan(AnswertableColumns.POSITION_NUMBER, value);
        return this;
    }

    public AnswertableSelection positionNumberGtEq(int value) {
        addGreaterThanOrEquals(AnswertableColumns.POSITION_NUMBER, value);
        return this;
    }

    public AnswertableSelection positionNumberLt(int value) {
        addLessThan(AnswertableColumns.POSITION_NUMBER, value);
        return this;
    }

    public AnswertableSelection positionNumberLtEq(int value) {
        addLessThanOrEquals(AnswertableColumns.POSITION_NUMBER, value);
        return this;
    }

    public AnswertableSelection orderByPositionNumber(boolean desc) {
        orderBy(AnswertableColumns.POSITION_NUMBER, desc);
        return this;
    }

    public AnswertableSelection orderByPositionNumber() {
        orderBy(AnswertableColumns.POSITION_NUMBER, false);
        return this;
    }

    public AnswertableSelection createdAt(Date... value) {
        addEquals(AnswertableColumns.CREATED_AT, value);
        return this;
    }

    public AnswertableSelection createdAtNot(Date... value) {
        addNotEquals(AnswertableColumns.CREATED_AT, value);
        return this;
    }

    public AnswertableSelection createdAt(Long... value) {
        addEquals(AnswertableColumns.CREATED_AT, value);
        return this;
    }

    public AnswertableSelection createdAtAfter(Date value) {
        addGreaterThan(AnswertableColumns.CREATED_AT, value);
        return this;
    }

    public AnswertableSelection createdAtAfterEq(Date value) {
        addGreaterThanOrEquals(AnswertableColumns.CREATED_AT, value);
        return this;
    }

    public AnswertableSelection createdAtBefore(Date value) {
        addLessThan(AnswertableColumns.CREATED_AT, value);
        return this;
    }

    public AnswertableSelection createdAtBeforeEq(Date value) {
        addLessThanOrEquals(AnswertableColumns.CREATED_AT, value);
        return this;
    }

    public AnswertableSelection orderByCreatedAt(boolean desc) {
        orderBy(AnswertableColumns.CREATED_AT, desc);
        return this;
    }

    public AnswertableSelection orderByCreatedAt() {
        orderBy(AnswertableColumns.CREATED_AT, false);
        return this;
    }
}
