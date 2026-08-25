package com.app.trainview.services

import com.app.trainview.model.ApiResponse
import com.app.trainview.model.LiveTrain
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TrainService {

    @GET("/v1/trains/{number}/live")
    suspend fun getTrainLiveStatus(@Path("number") number: String): ApiResponse<LiveTrain>
}