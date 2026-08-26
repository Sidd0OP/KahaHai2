package com.app.trainview.model.train

data class LiveTrain(
    val trainNumber: String,
    val trainName: String,
    val startDate: String,
    val lastUpdatedAt: String,
    val status: String,
    val train: TrainDetails,
    val isLive: Boolean,
    val trackingMode: String,
    val currentLocation: CurrentLocation,
    val previousHalt: Halt,
    val nextHalt: Halt,
    val delayMinutes: Double,
    val route: List<Station>
)

data class CurrentLocation(
    val stationCode: String,
    val sequence: Int,
    val status: String,
    val isHalt: Boolean,
    val isActualPosition: Boolean,
    val coordinates: Coordinates,
    val distanceFromOriginKm: Double,
    val distanceFromLastStationKm: Double,
    val segmentProgress: Double,
    val delayMinutes: Double,
    val stationName: String
)

data class Coordinates(
    val lat: Double,
    val lng: Double
)

data class Halt(
    val stationCode: String,
    val stationName: String,
    val sequence: Int,
    val distance: Double
)


data class TrainDetails(
    val number: String,
    val name: String,
    val type: String,
    val category: String,
    val source: StationLocation,
    val destination: StationLocation,
    val runDays: List<String>,
    val distance: Double,
    val duration: Double,
    val avgSpeed: Double,
    val maxSpeed: Double,
    val totalHalts: Int,
    val returnTrain: String,
    val coachPosition: String
)

data class StationLocation(
    val code: String,
    val name: String,
    val lat: Double,
    val lng: Double
)

data class Station(
    val sequence: Int,
    val stationCode: String,
    val stationName: String,
    val isHalt: Boolean,
    val status: String,
    val coachPosition: String,
    val lat: Double,
    val lng: Double,
    val scheduledDeparture: String,
    val departureDay: Double,
    val actualDeparture: String,
    val delayDeparture: Double,
    val platform: String,
    val distance: Double,
    val speedToNextStationKmph: Double
)