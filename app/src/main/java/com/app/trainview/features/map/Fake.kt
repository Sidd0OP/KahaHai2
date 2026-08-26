package com.app.trainview.features.map

import com.app.trainview.model.train.Coordinates
import com.app.trainview.model.train.CurrentLocation
import com.app.trainview.model.train.Halt
import com.app.trainview.model.train.LiveTrain
import com.app.trainview.model.train.StationLocation
import com.app.trainview.model.train.TrainDetails

// PreviewData.kt
fun fakeLiveTrain(
    trainNumber: String = "12951",
    trainName: String = "Mumbai Rajdhani",
    delayMinutes: Int = 12,
    sourceName: String = "New Delhi",
    destinationName: String = "Mumbai Central"
) = LiveTrain(
    trainNumber = trainNumber,
    trainName = trainName,
    startDate = "2026-08-26",
    lastUpdatedAt = "2026-08-26T10:30:00",
    status = "RUNNING",
    isLive = true,
    trackingMode = "GPS",
    delayMinutes = 1.0,
    train = TrainDetails(
        number = trainNumber,
        name = trainName,
        type = "Rajdhani",
        category = "Superfast",
        source = StationLocation("NDLS", sourceName, 28.6435, 77.2197),
        destination = StationLocation("BCT", destinationName, 18.9696, 72.8194),
        runDays = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"),
        distance = 1384.0,
        duration = 10.0,
        avgSpeed = 86.5,
        maxSpeed = 12.0,
        totalHalts = 5,
        returnTrain = "12952",
        coachPosition = "S1-S2-S3-B1-B2"
    ),
    currentLocation = CurrentLocation(
        stationCode = "KOTA", sequence = 3, status = "DEPARTED",
        isHalt = false, isActualPosition = true,
        coordinates = Coordinates(25.2138, 75.8648),
        distanceFromOriginKm = 465.0, distanceFromLastStationKm = 12.5,
        segmentProgress = 0.42,
        delayMinutes = 1.0,
        stationName = "Kota Junction"
    ),
    previousHalt = Halt("KOTA", "Kota Junction", 3, 465.0),
    nextHalt = Halt("RTM", "Ratlam Junction", 4, 675.0),
    route = emptyList()
)