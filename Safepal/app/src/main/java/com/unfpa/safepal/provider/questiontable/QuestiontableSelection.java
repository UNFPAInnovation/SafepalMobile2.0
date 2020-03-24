package com.unfpa.safepal.provider.questiontable;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.unfpa.safepal.provider.base.AbstractSelection;

/**
 * Selection for the {@code questiontable} table.
 */
public class QuestiontableSelection extends AbstractSelection<QuestiontableSelection> {
    @Override
    protected Uri baseUri() {
        return QuestiontableColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code QuestiontableCursor} object, which is positioned before the first entry, or null.
     */
    public QuestiontableCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new QuestiontableCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public QuestiontableCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code QuestiontableCursor} object, which is positioned before the first entry, or null.
     */
    public QuestiontableCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new QuestiontableCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public QuestiontableCursor query(Context context) {
        return query(context, null);
    }


    public QuestiontableSelection id(long... value) {
        addEquals("questiontable." + QuestiontableColumns._ID, toObjectArray(value));
        return this;
    }

    public QuestiontableSelection idNot(long... value) {
        addNotEquals("questiontable." + QuestiontableColumns._ID, toObjectArray(value));
        return this;
    }

    public QuestiontableSelection orderById(boolean desc) {
        orderBy("questiontable." + QuestiontableColumns._ID, desc);
        return this;
    }

    public QuestiontableSelection orderById() {
        return orderById(false);
    }

    public QuestiontableSelection serverid(String... value) {
        addEquals(QuestiontableColumns.SERVERID, value);
        return this;
    }

    public QuestiontableSelection serveridNot(String... value) {
        addNotEquals(QuestiontableColumns.SERVERID, value);
        return this;
    }

    public QuestiontableSelection serveridLike(String... value) {
        addLike(QuestiontableColumns.SERVERID, value);
        return this;
    }

    public QuestiontableSelection serveridContains(String... value) {
        addContains(QuestiontableColumns.SERVERID, value);
        return this;
    }

    public QuestiontableSelection serveridStartsWith(String... value) {
        addStartsWith(QuestiontableColumns.SERVERID, value);
        return this;
    }

    public QuestiontableSelection serveridEndsWith(String... value) {
        addEndsWith(QuestiontableColumns.SERVERID, value);
        return this;
    }

    public QuestiontableSelection orderByServerid(boolean desc) {
        orderBy(QuestiontableColumns.SERVERID, desc);
        return this;
    }

    public QuestiontableSelection orderByServerid() {
        orderBy(QuestiontableColumns.SERVERID, false);
        return this;
    }

    public QuestiontableSelection quiz(Integer... value) {
        addEquals(QuestiontableColumns.QUIZ, value);
        return this;
    }

    public QuestiontableSelection quizNot(Integer... value) {
        addNotEquals(QuestiontableColumns.QUIZ, value);
        return this;
    }

    public QuestiontableSelection quizGt(int value) {
        addGreaterThan(QuestiontableColumns.QUIZ, value);
        return this;
    }

    public QuestiontableSelection quizGtEq(int value) {
        addGreaterThanOrEquals(QuestiontableColumns.QUIZ, value);
        return this;
    }

    public QuestiontableSelection quizLt(int value) {
        addLessThan(QuestiontableColumns.QUIZ, value);
        return this;
    }

    public QuestiontableSelection quizLtEq(int value) {
        addLessThanOrEquals(QuestiontableColumns.QUIZ, value);
        return this;
    }

    public QuestiontableSelection orderByQuiz(boolean desc) {
        orderBy(QuestiontableColumns.QUIZ, desc);
        return this;
    }

    public QuestiontableSelection orderByQuiz() {
        orderBy(QuestiontableColumns.QUIZ, false);
        return this;
    }

    public QuestiontableSelection content(String... value) {
        addEquals(QuestiontableColumns.CONTENT, value);
        return this;
    }

    public QuestiontableSelection contentNot(String... value) {
        addNotEquals(QuestiontableColumns.CONTENT, value);
        return this;
    }

    public QuestiontableSelection contentLike(String... value) {
        addLike(QuestiontableColumns.CONTENT, value);
        return this;
    }

    public QuestiontableSelection contentContains(String... value) {
        addContains(QuestiontableColumns.CONTENT, value);
        return this;
    }

    public QuestiontableSelection contentStartsWith(String... value) {
        addStartsWith(QuestiontableColumns.CONTENT, value);
        return this;
    }

    public QuestiontableSelection contentEndsWith(String... value) {
        addEndsWith(QuestiontableColumns.CONTENT, value);
        return this;
    }

    public QuestiontableSelection orderByContent(boolean desc) {
        orderBy(QuestiontableColumns.CONTENT, desc);
        return this;
    }

    public QuestiontableSelection orderByContent() {
        orderBy(QuestiontableColumns.CONTENT, false);
        return this;
    }

    public QuestiontableSelection score(String... value) {
        addEquals(QuestiontableColumns.SCORE, value);
        return this;
    }

    public QuestiontableSelection scoreNot(String... value) {
        addNotEquals(QuestiontableColumns.SCORE, value);
        return this;
    }

    public QuestiontableSelection scoreLike(String... value) {
        addLike(QuestiontableColumns.SCORE, value);
        return this;
    }

    public QuestiontableSelection scoreContains(String... value) {
        addContains(QuestiontableColumns.SCORE, value);
        return this;
    }

    public QuestiontableSelection scoreStartsWith(String... value) {
        addStartsWith(QuestiontableColumns.SCORE, value);
        return this;
    }

    public QuestiontableSelection scoreEndsWith(String... value) {
        addEndsWith(QuestiontableColumns.SCORE, value);
        return this;
    }

    public QuestiontableSelection orderByScore(boolean desc) {
        orderBy(QuestiontableColumns.SCORE, desc);
        return this;
    }

    public QuestiontableSelection orderByScore() {
        orderBy(QuestiontableColumns.SCORE, false);
        return this;
    }

    public QuestiontableSelection difficulty(Integer... value) {
        addEquals(QuestiontableColumns.DIFFICULTY, value);
        return this;
    }

    public QuestiontableSelection difficultyNot(Integer... value) {
        addNotEquals(QuestiontableColumns.DIFFICULTY, value);
        return this;
    }

    public QuestiontableSelection difficultyGt(int value) {
        addGreaterThan(QuestiontableColumns.DIFFICULTY, value);
        return this;
    }

    public QuestiontableSelection difficultyGtEq(int value) {
        addGreaterThanOrEquals(QuestiontableColumns.DIFFICULTY, value);
        return this;
    }

    public QuestiontableSelection difficultyLt(int value) {
        addLessThan(QuestiontableColumns.DIFFICULTY, value);
        return this;
    }

    public QuestiontableSelection difficultyLtEq(int value) {
        addLessThanOrEquals(QuestiontableColumns.DIFFICULTY, value);
        return this;
    }

    public QuestiontableSelection orderByDifficulty(boolean desc) {
        orderBy(QuestiontableColumns.DIFFICULTY, desc);
        return this;
    }

    public QuestiontableSelection orderByDifficulty() {
        orderBy(QuestiontableColumns.DIFFICULTY, false);
        return this;
    }

    public QuestiontableSelection correctAnswer(Integer... value) {
        addEquals(QuestiontableColumns.CORRECT_ANSWER, value);
        return this;
    }

    public QuestiontableSelection correctAnswerNot(Integer... value) {
        addNotEquals(QuestiontableColumns.CORRECT_ANSWER, value);
        return this;
    }

    public QuestiontableSelection correctAnswerGt(int value) {
        addGreaterThan(QuestiontableColumns.CORRECT_ANSWER, value);
        return this;
    }

    public QuestiontableSelection correctAnswerGtEq(int value) {
        addGreaterThanOrEquals(QuestiontableColumns.CORRECT_ANSWER, value);
        return this;
    }

    public QuestiontableSelection correctAnswerLt(int value) {
        addLessThan(QuestiontableColumns.CORRECT_ANSWER, value);
        return this;
    }

    public QuestiontableSelection correctAnswerLtEq(int value) {
        addLessThanOrEquals(QuestiontableColumns.CORRECT_ANSWER, value);
        return this;
    }

    public QuestiontableSelection orderByCorrectAnswer(boolean desc) {
        orderBy(QuestiontableColumns.CORRECT_ANSWER, desc);
        return this;
    }

    public QuestiontableSelection orderByCorrectAnswer() {
        orderBy(QuestiontableColumns.CORRECT_ANSWER, false);
        return this;
    }

    public QuestiontableSelection position(Integer... value) {
        addEquals(QuestiontableColumns.POSITION, value);
        return this;
    }

    public QuestiontableSelection positionNot(Integer... value) {
        addNotEquals(QuestiontableColumns.POSITION, value);
        return this;
    }

    public QuestiontableSelection positionGt(int value) {
        addGreaterThan(QuestiontableColumns.POSITION, value);
        return this;
    }

    public QuestiontableSelection positionGtEq(int value) {
        addGreaterThanOrEquals(QuestiontableColumns.POSITION, value);
        return this;
    }

    public QuestiontableSelection positionLt(int value) {
        addLessThan(QuestiontableColumns.POSITION, value);
        return this;
    }

    public QuestiontableSelection positionLtEq(int value) {
        addLessThanOrEquals(QuestiontableColumns.POSITION, value);
        return this;
    }

    public QuestiontableSelection orderByPosition(boolean desc) {
        orderBy(QuestiontableColumns.POSITION, desc);
        return this;
    }

    public QuestiontableSelection orderByPosition() {
        orderBy(QuestiontableColumns.POSITION, false);
        return this;
    }

    public QuestiontableSelection createdAt(Date... value) {
        addEquals(QuestiontableColumns.CREATED_AT, value);
        return this;
    }

    public QuestiontableSelection createdAtNot(Date... value) {
        addNotEquals(QuestiontableColumns.CREATED_AT, value);
        return this;
    }

    public QuestiontableSelection createdAt(Long... value) {
        addEquals(QuestiontableColumns.CREATED_AT, value);
        return this;
    }

    public QuestiontableSelection createdAtAfter(Date value) {
        addGreaterThan(QuestiontableColumns.CREATED_AT, value);
        return this;
    }

    public QuestiontableSelection createdAtAfterEq(Date value) {
        addGreaterThanOrEquals(QuestiontableColumns.CREATED_AT, value);
        return this;
    }

    public QuestiontableSelection createdAtBefore(Date value) {
        addLessThan(QuestiontableColumns.CREATED_AT, value);
        return this;
    }

    public QuestiontableSelection createdAtBeforeEq(Date value) {
        addLessThanOrEquals(QuestiontableColumns.CREATED_AT, value);
        return this;
    }

    public QuestiontableSelection orderByCreatedAt(boolean desc) {
        orderBy(QuestiontableColumns.CREATED_AT, desc);
        return this;
    }

    public QuestiontableSelection orderByCreatedAt() {
        orderBy(QuestiontableColumns.CREATED_AT, false);
        return this;
    }
}
