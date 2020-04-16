
package com.unfpa.safepal.retrofitmodels.questions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("answer")
    @Expose
    private String answer;
    @SerializedName("difficulty")
    @Expose
    private Integer difficulty;
    @SerializedName("position")
    @Expose
    private Integer position;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("quiz")
    @Expose
    private Integer quiz;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getQuiz() {
        return quiz;
    }

    public void setQuiz(Integer quiz) {
        this.quiz = quiz;
    }

}
