package com.unfpa.safepal.retrofitmodels.faqratings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FaqRating {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("rating")
    @Expose
    private Integer rating;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("faq")
    @Expose
    private Integer faq;

    public FaqRating() {
    }

    public FaqRating(Integer rating, Integer faq) {
        this.rating = rating;
        this.faq = faq;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getFaq() {
        return faq;
    }

    public void setFaq(Integer faq) {
        this.faq = faq;
    }

}
