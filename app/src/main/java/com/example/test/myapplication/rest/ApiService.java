package com.example.test.myapplication.rest;


import com.example.test.myapplication.rest.models.SearchData;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface ApiService {

    @GET("search")
    Observable<SearchData> getSongs(@Query("q") String name, @Query("type") String type, @Query("limit") int limit, @Query("offset") int offset);
}

