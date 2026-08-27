package com.app.trainview.features.map

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Location
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GpsFixed
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Train
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.trainview.features.map.components.TrainInfoCard
import com.app.trainview.features.map.components.TrainInfoLargeCard
import com.app.trainview.features.map.components.TrainMarker
import com.app.trainview.features.map.components.UserTrainMarker
import com.app.trainview.model.train.LiveMapTrain
import com.app.trainview.model.train.LiveTrain
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PinConfig
import com.google.maps.android.compose.AdvancedMarker
import com.google.maps.android.compose.ComposeMapColorScheme
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import java.lang.Math.toDegrees
import java.lang.Math.toRadians
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import com.app.trainview.R
import com.google.android.gms.maps.model.LatLngBounds


@Composable
fun MapScreen(
    goToHome: () -> Unit,
    viewModel: MapViewModel = viewModel()
) {
    val trainFlow by viewModel.liveTrainFLow.collectAsState()
    val mapTrainFlow by viewModel.mapTrains.collectAsState()
    val userLocationFlow by viewModel.userLocation.collectAsState()

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission())
    {
        isGranted -> if (isGranted) viewModel.startLocationUpdates()
    }

    BackHandler {
        viewModel.resetForNewRequest()
        goToHome()
    }

    trainFlow?.let {
        MapScreenContent(
            userLocation = userLocationFlow,
            train = it.liveTrain,
            mapTrains = mapTrainFlow,
            onRefreshClick = { viewModel.updateData() },
            amInsideTrainClick = {
                if (!viewModel.hasLocationPermission()) {
                    launcher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
                } else {
                    viewModel.startLocationUpdates()
                }

            },
            amNotInsideTrainClick = { viewModel.stopLocationUpdates() }
        )
    }
}

@Composable
fun MapScreenContent(
    userLocation: Location?,
    train: LiveTrain,
    mapTrains: List<LiveMapTrain>,
    onRefreshClick: () -> Unit,
    amInsideTrainClick: () -> Unit,
    amNotInsideTrainClick: () -> Unit,
) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(28.6139, 77.2090), 10f)
    }

    var isExpanded by remember { mutableStateOf(false) }
    var isInsideTrain by remember { mutableStateOf(false) }

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


                //user marker if inside train
                if (isInsideTrain) {
                    userLocation?.let { location ->
                        val userMarkerState = remember {
                            MarkerState(position = LatLng(location.latitude, location.longitude))
                        }

                        LaunchedEffect(location.latitude, location.longitude) {
                            userMarkerState.position = LatLng(location.latitude, location.longitude)
                            cameraPositionState.animate(
                                update = CameraUpdateFactory.newLatLng(userMarkerState.position),
                                durationMs = 800
                            )
                        }

                        UserTrainMarker(
                            state = userMarkerState,
                            rotation = 0.0f,
                            title = "user",
                            snippet = "Loc ${location.latitude} ${location.longitude} No ${train.trainNumber}"
                        )
                    }
                } else {
                    train.currentLocation.let { currentLocation ->
                        val trainMarkerState = remember {
                            MarkerState(
                                position = LatLng(currentLocation.coordinates.lat,currentLocation.coordinates.lng)
                            )
                        }

                        LaunchedEffect(currentLocation.coordinates.lat, currentLocation.coordinates.lng) {
                            trainMarkerState.position = LatLng(currentLocation.coordinates.lat, currentLocation.coordinates.lng)
                            cameraPositionState.animate(
                                update = CameraUpdateFactory.newLatLng(trainMarkerState.position),
                                durationMs = 800
                            )
                        }

                        UserTrainMarker(
                            state = trainMarkerState,
                            rotation = 0.0f,
                            title = train.trainName,
                            snippet = "Loc ${train.currentLocation.coordinates.lng} ${train.currentLocation.coordinates.lng} No ${train.trainNumber}"
                        )
                    }
                }

                var settledBounds by remember { mutableStateOf<LatLngBounds?>(null) }

                LaunchedEffect(cameraPositionState.isMoving) {
                    if (!cameraPositionState.isMoving) {
                        settledBounds = cameraPositionState.projection?.visibleRegion?.latLngBounds
                    }
                }

                LaunchedEffect(Unit) {
                    settledBounds = cameraPositionState.projection?.visibleRegion?.latLngBounds
                }

                val visibleTrains = remember(mapTrains, settledBounds, train.trainNumber) {
                    val bounds = settledBounds
                    if (bounds == null) {
                        emptyList()
                    } else {
                        mapTrains.filter {
                            it.trainNumber != train.trainNumber &&
                                    bounds.contains(LatLng(it.currentLat, it.currentLng))
                        }
                    }
                }


                //markers for map trains
                visibleTrains.forEach {
                        key(it.trainNumber) {

                            val ctx = LocalContext.current

                            val markerState = remember(it.trainNumber) {
                                MarkerState(position = LatLng(it.currentLat, it.currentLng))
                            }

                            LaunchedEffect(it.currentLat, it.currentLng) {
                                markerState.position = LatLng(it.currentLat, it.currentLng)
                            }

                            val rotation = remember(it.currentLat, it.currentLng, it.nextLat, it.nextLng) {
                                calculateBearing(it.currentLat, it.currentLng, it.nextLat, it.nextLng)
                            }

                            val trainIcon = remember {
                                val original = BitmapFactory.decodeResource(ctx.resources, R.drawable.train)
                                val scaled = Bitmap.createScaledBitmap(original, 56, 174, true)
                                BitmapDescriptorFactory.fromBitmap(scaled)
                            }

                            TrainMarker(
                                state = markerState,
                                rotation = rotation,
                                title = it.trainName,
                                snippet = "Loc ${it.currentLng} ${it.currentLng} No ${it.trainNumber}",
                                icon = trainIcon
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
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                Button(
                    onClick = {
                        if (isInsideTrain) {
                            amNotInsideTrainClick()
                        } else {
                            amInsideTrainClick()
                        }
                        isInsideTrain = !isInsideTrain
                    },
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
//                        containerColor = Color.White,
//                        contentColor = Color.Black
                    ),
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        imageVector = if (isInsideTrain) Icons.Default.GpsFixed else Icons.Default.Train,
                        contentDescription = if (isInsideTrain) "Mark as outside train" else "Mark as inside train",
//                        tint = Color.Black
                    )
                }

                SmallFloatingActionButton(
                    modifier = Modifier.size(48.dp),
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

fun calculateBearing(
    startLat: Double,
    startLng: Double,
    endLat: Double,
    endLng: Double
): Float {
    val lat1 = toRadians(startLat)
    val lat2 = toRadians(endLat)
    val dLng = toRadians(endLng - startLng)

    val y = sin(dLng) * cos(lat2)
    val x = cos(lat1) * sin(lat2) - sin(lat1) * cos(lat2) * cos(dLng)

    val bearing = toDegrees(atan2(y, x))
    return ((bearing + 360) % 360).toFloat()
}


@Preview(showBackground = true)
@Composable
fun MapScreenPreview() {
    MapScreenContent(
        userLocation = null,
        train = fakeLiveTrain(),
        mapTrains = listOf(),
        onRefreshClick = { },
        amInsideTrainClick = { },
        amNotInsideTrainClick = { }
    )
}