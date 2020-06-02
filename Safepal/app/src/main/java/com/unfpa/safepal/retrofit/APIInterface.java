package com.unfpa.safepal.retrofit;

import com.unfpa.safepal.retrofitmodels.articles.Articles;
import com.unfpa.safepal.retrofitmodels.districts.Districts;
import com.unfpa.safepal.retrofitmodels.faqs.Faq;
import com.unfpa.safepal.retrofitmodels.organizations.Organizations;
import com.unfpa.safepal.retrofitmodels.questions.Questions;
import com.unfpa.safepal.retrofitmodels.quizzes.Quizzes;
import com.unfpa.safepal.retrofitmodels.videos.Videos;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIInterface {
    @GET("api/v1/videos")
    Call<Videos> getVideos();

    @GET("api/v1/articles")
    Call<Articles> getArticles();

    @GET("api/v1/organizations")
    Call<Organizations> getOrganizations();

    @GET("api/v1/districts")
    Call<Districts> getDistricts();

    @GET("api/v1/questions")
    Call<Questions> getQuestions();

    @GET("api/v1/quizzes")
    Call<Quizzes> getQuizzes();

    @GET("api/v1/faqs")
    Call<Faq> getFaqs();
}
