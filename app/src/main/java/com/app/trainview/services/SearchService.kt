package com.app.trainview.services

import com.app.trainview.model.ApiResponse
import com.app.trainview.model.LiveTrain
import com.app.trainview.model.search.SearchResultData
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    @GET("v1/lookup/search/trains")
    suspend fun getTrainInfo(
        @Query("q") query: String,
        @Query("limit") limit: Int = 20)
    : ApiResponse<List<SearchResultData>>
}