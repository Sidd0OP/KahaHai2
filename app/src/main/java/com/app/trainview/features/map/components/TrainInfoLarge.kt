package com.app.trainview.features.map.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.trainview.model.train.Station

@Composable
fun TrainInfoLargeCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    trainName: String = "New Delhi Bhopal Shatabdi",
    departureStation: String = "New Delhi",
    arrivalStation: String = "Rani Kamlap...",
    stations : List<Station> = listOf()
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        shape = RoundedCornerShape(40.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHighest
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                TrainInfoCard(
                    onClick = { onClick() },
                    trainName = trainName,
                    departureStation = departureStation,
                    arrivalStation = arrivalStation
                )
            }
            items(
                items = stations,
                key = { it.stationCode }
            ) { station ->
                StationPill(
                    stationName = station.stationName,
                    platform = station.platform ?: "-",
//                    scheduledTime = station.scheduledDeparture ?: station.scheduledArrival ?: "-",
//                    actualTime = station.actualDeparture ?: station.actualArrival ?: "-",
                )
            }
        }
    }
}