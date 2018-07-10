package com.androidsample.api;

import com.androidsample.beans.ApiResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface
ApiInterface {
    //http://api.nytimes.com/svc/mostpopular/v2/mostviewed/all-sections/7.json?api-key=844d1cb4cf784219853bd7e321dd5517
    @GET("mostviewed/all-sections/{date}.json?")
    Observable<ApiResponse> getMostPopularList(@Path("date") String date, @Query("api-key") String key);
}
