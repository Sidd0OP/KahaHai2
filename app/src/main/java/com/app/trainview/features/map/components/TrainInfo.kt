package com.app.trainview.features.map.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TrainInfoCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    trainName: String = "Bhopal Shatabdi 12001",
    departureStation: String = "New Delhi",
    departureTime: String = "20 Nov, 11:59 PM",
    arrivalStation: String = "Rani Ka...",
    arrivalTime: String = "20 Nov, 11:59 PM",
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(40.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF83BCFF) // Light blue card background
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Train name
            Text(
                text = trainName,
                color = Color.Black,
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Station row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // Departure station pill
                Surface(
                    shape = RoundedCornerShape(40.dp),
                    color = Color.White,
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = departureStation,
                            color = Color.Black,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                        Text(
                            text = departureTime,
                            color = Color.Black,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                }

                // Arrow icon in black circle
                Surface(
                    shape = RoundedCornerShape(32.dp),
                    color = Color.Black,
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Direction",
                        tint = Color.White,
                        modifier = Modifier.padding(6.dp),
                    )
                }

                // Arrival station pill
                Surface(
                    shape = RoundedCornerShape(40.dp),
                    color = Color.White,
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = arrivalStation,
                            color = Color.Black,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                        Text(
                            text = arrivalTime,
                            color = Color.Black,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                }
            }
        }
    }
}


@Composable
private fun StationChip(
    stationName: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        color = MaterialTheme.colorScheme.surface,
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stationName,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(2.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TrainInfoCardPreview() {
    TrainInfoCard(
        onClick = {},
        trainName = "Rajdhani Express",
        departureStation = "New Delhi",
        arrivalStation = "Mumbai Central"
    )
}
