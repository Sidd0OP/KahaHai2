package com.app.trainview.model.train

data class LiveMapTrain(
    val trainNumber: String,
    val trainName: String,
    val type: String,
    val minsSinceDep: Int,
    val currentStation: String,
    val currentStationName: String,
    val currentLat: Double,
    val currentLng: Double,
    val departureMinutes: Int,
    val currentDay: Int,
    val nextStation: String,
    val nextStationName: String,
    val nextLat: Double,
    val nextLng: Double,
    val nextArrivalMinutes: Int,
    val currDistance: Int,
    val nextDistance: Int
)
