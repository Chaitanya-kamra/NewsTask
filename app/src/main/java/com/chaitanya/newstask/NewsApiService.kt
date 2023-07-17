package com.chaitanya.newstask

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface NewsApiService {
    @GET("top-headlines")
    suspend fun getTopHeadlines(@Query("country") country : String,@Query("category") category : String,@Query("apiKey") apikey : String): NewsResponse
}

