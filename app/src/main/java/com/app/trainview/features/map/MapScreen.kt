package com.app.trainview.features.map

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.trainview.features.map.components.TrainInfoCard
import com.app.trainview.features.map.components.TrainInfoLargeCard
import com.app.trainview.model.train.LiveMapTrain
import com.app.trainview.model.train.LiveTrain
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.AdvancedMarker
import com.google.maps.android.compose.ComposeMapColorScheme
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState


@Composable
fun MapScreen(
    goToHome: () -> Unit,
    viewModel: MapViewModel = viewModel()
) {
    val trainFlow by viewModel.liveTrainFLow.collectAsState()
    val mapTrainFlow by viewModel.mapTrains.collectAsState()

    BackHandler {
        viewModel.clearCachedTrain()
        goToHome()
    }

    trainFlow?.let {
        MapScreenContent(
            train = it.liveTrain,
            mapTrains = mapTrainFlow,
            onRefreshClick = { viewModel.updateData() }
        )
    }
}

@Composable
fun MapScreenContent(
    train: LiveTrain,
    mapTrains: List<LiveMapTrain>,
    onRefreshClick: () -> Unit
) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(28.6139, 77.2090), 10f)
    }

    var isExpanded by remember { mutableStateOf(false) }



    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets(0, 0, 0)
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                googleMapOptionsFactory = {
                    GoogleMapOptions()
                        .mapId("91c411314054a75ae0c329be")
                },
                uiSettings = MapUiSettings(
                    compassEnabled = false,
                    myLocationButtonEnabled = false,
                    zoomControlsEnabled = false,
                    mapToolbarEnabled = false
                ),
                cameraPositionState = cameraPositionState,
                mapColorScheme = ComposeMapColorScheme.DARK
            ) {
                mapTrains.forEach {
                    Log.i("marker", "draw marer")

                    key(it.trainNumber){
                        val markerState = remember(it.trainNumber) {
                            MarkerState(position = LatLng(it.currentLat, it.currentLng))
                        }

                        LaunchedEffect(it.currentLat, it.currentLng) {
                            markerState.position = LatLng(it.currentLat, it.currentLng)
                        }

                        AdvancedMarker(
                            state = markerState,
                            title = "${it.currentLat}${it.currentLng}",
                            snippet = train.trainNumber
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .windowInsetsPadding(WindowInsets.statusBars)
                    .windowInsetsPadding(WindowInsets.navigationBars)
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SmallFloatingActionButton(
                    onClick = onRefreshClick,
                    shape = CircleShape,
                    containerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                    elevation = FloatingActionButtonDefaults.elevation(
                        defaultElevation = 0.dp,
                        pressedElevation = 0.dp,
                        focusedElevation = 0.dp,
                        hoveredElevation = 0.dp
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Refresh"
                    )
                }

                when (isExpanded) {
                    true -> {
                        TrainInfoLargeCard(
                            onClick = { isExpanded = false },
                            modifier = Modifier.fillMaxWidth(),
                            trainName = train.trainName,
                            departureStation = train.train.source.name,
                            arrivalStation = train.train.destination.name,
                            stations = train.route
                        )
                    }

                    false -> {
                        TrainInfoCard(
                            onClick = { isExpanded = true },
                            modifier = Modifier.fillMaxWidth(),
                            trainName = train.trainName,
                            departureStation = train.train.source.name,
                            arrivalStation = train.train.destination.name
                        )
                    }
                }


            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MapScreenPreview() {
    MapScreenContent(
        train = fakeLiveTrain(),
        mapTrains = listOf(),
        onRefreshClick = { }
    )
}