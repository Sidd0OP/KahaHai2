package com.app.trainview.services

import com.app.trainview.model.ApiResponse
import com.app.trainview.model.train.LiveMapTrain
import com.app.trainview.model.train.LiveTrain
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TrainService {

    @GET("/v1/trains/{number}/live")
    suspend fun getTrainLiveStatus(
        @Path("number") number: String,
        @Query("includeCoordinates") includeCoordinates : Boolean = true
    ): ApiResponse<LiveTrain>


    @GET("/v1/trains/{number}/live")
    suspend fun getTrainLiveStatusOfDate(
        @Path("number") number: String,
        @Query("includeCoordinates") includeCoordinates : Boolean = true,
        // yyyy-mm-dd
        @Query("date") date : String
    ): ApiResponse<LiveTrain>



    @GET("/v1//legacy/trains/live-map")
    suspend fun getAllTrainMapPosition(): ApiResponse<List<LiveMapTrain>>

}