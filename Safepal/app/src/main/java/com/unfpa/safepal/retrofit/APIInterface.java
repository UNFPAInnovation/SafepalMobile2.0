package com.unfpa.safepal.retrofit;

import com.unfpa.safepal.retrofitmodels.videos.Videos;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIInterface {
    @GET("api/v1/videos")
    Call<Videos> getVideos();
}
