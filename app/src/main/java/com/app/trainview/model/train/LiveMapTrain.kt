package com.app.trainview.model.train

import com.google.gson.annotations.SerializedName

data class LiveMapTrain(
    @SerializedName("train_number") val trainNumber: String,
    @SerializedName("train_name") val trainName: String,
    @SerializedName("type") val type: String,
    @SerializedName("mins_since_dep") val minsSinceDep: Int,
    @SerializedName("current_station") val currentStation: String,
    @SerializedName("current_station_name") val currentStationName: String,
    @SerializedName("current_lat") val currentLat: Double,
    @SerializedName("current_lng") val currentLng: Double,
    @SerializedName("departure_minutes") val departureMinutes: Int,
    @SerializedName("current_day") val currentDay: Int,
    @SerializedName("next_station") val nextStation: String,
    @SerializedName("next_station_name") val nextStationName: String,
    @SerializedName("next_lat") val nextLat: Double,
    @SerializedName("next_lng") val nextLng: Double,
    @SerializedName("next_arrival_minutes") val nextArrivalMinutes: Int,
    @SerializedName("curr_distance") val currDistance: Double,
    @SerializedName("next_distance") val nextDistance: Double
)
