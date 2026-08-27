package com.app.trainview.features.map.components

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.app.trainview.R
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.maps.android.compose.AdvancedMarker
import com.google.maps.android.compose.MarkerState

@SuppressLint("LocalContextResourcesRead")
@Composable
fun UserTrainMarker(state: MarkerState, rotation: Float, title: String, snippet: String) {
    val context = LocalContext.current

    val bitmapDescriptor = remember {
        val original = BitmapFactory.decodeResource(context.resources, R.drawable.user)
        val scaled = Bitmap.createScaledBitmap(original, 144, 144, true)
        BitmapDescriptorFactory.fromBitmap(scaled)
    }

    AdvancedMarker(
        state = state,
        title = title,
        snippet = snippet,
        icon = bitmapDescriptor,
        rotation = rotation
    )
}