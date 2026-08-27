package com.app.trainview

import android.os.Bundle
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.app.trainview.navigation.Destination
import com.app.trainview.navigation.navEntry
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000)
        .setMinUpdateIntervalMillis(5000)
        .build()
    val builder = LocationSettingsRequest.Builder()
        .addLocationRequest(locationRequest)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        enableEdgeToEdge()
        setContent {
            val backStack = remember { mutableStateListOf<Any>(Destination.Home) }

            NavDisplay(
                backStack = backStack,
                onBack = {
                    if (backStack.size > 1)backStack.removeAt(backStack.lastIndex)
                },
                entryProvider = entryProvider {
                    navEntry(backStack=backStack)
                },
                transitionSpec = {
                    slideInHorizontally(initialOffsetX = { it }) togetherWith
                            slideOutHorizontally(targetOffsetX = { -it / 3 })
                },

                popTransitionSpec = {
                    slideInHorizontally(initialOffsetX = { -it / 3 }) togetherWith
                            slideOutHorizontally(targetOffsetX = { it })
                },

                predictivePopTransitionSpec = { progress ->
                    slideInHorizontally(
                        animationSpec = tween(),
                        initialOffsetX = { -it / 3 }
                    ) togetherWith slideOutHorizontally(
                        animationSpec = tween(),
                        targetOffsetX = { it }
                    )
                }

            )
        }
    }


    override fun onResume() {
        super.onResume()
    }


}


