package com.app.trainview.features.map.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StationPill(
    modifier: Modifier = Modifier,
    scheduledArrival: String = "11:00 am",
    scheduledDeparture: String = "11:02 am",
    actualArrival: String = "11:00 am",
    actualDeparture: String = "11:02 am",
    stationName: String = "New Delhi",
    platform: String = "1",
) {
    Card(
        modifier = modifier.wrapContentHeight(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            //heading
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(6.dp)
            )
            {
                Text(
                    text = stationName,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Box(
                    modifier = Modifier
                        .background(
                            color = Color(0xFF7FB3F5),
                            shape = RoundedCornerShape(50)
                        )
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "Pfm. $platform",
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp,
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Column(
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    Text(
//                        text = "Scheduled",
//                        style = MaterialTheme.typography.bodyMedium,
//                        color = MaterialTheme.colorScheme.onSurfaceVariant,
//                        fontSize = 10.sp,
//
//                        )
//
//                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
//                        Text(
//                            text = scheduledArrival.toAmPmTime(),
//                            fontWeight = FontWeight.Bold,
//                            fontSize = 12.sp,
//                            color = MaterialTheme.colorScheme.onSurface
//                        )
//                        Text(
//                            text = "-",
//                            fontWeight = FontWeight.Bold,
//                            fontSize = 12.sp,
//                            color = MaterialTheme.colorScheme.onSurface
//                        )
//                        Text(
//                            text = scheduledDeparture.toAmPmTime(),
//                            fontWeight = FontWeight.Bold,
//                            fontSize = 12.sp,
//                            color = MaterialTheme.colorScheme.onSurface
//                        )
//                    }
//
//                }
//
//                Column(
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    Text(
//                        text = "Actual",
//                        style = MaterialTheme.typography.bodyMedium,
//                        color = MaterialTheme.colorScheme.onSurfaceVariant,
//                        fontSize = 10.sp,
//                    )
//
//                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp))  {
//                        Text(
//                            text = actualArrival.toAmPmTime(),
//                            fontWeight = FontWeight.Bold,
//                            fontSize = 12.sp,
//                            color = MaterialTheme.colorScheme.onSurface
//                        )
//                        Text(
//                            text = "-",
//                            fontWeight = FontWeight.Bold,
//                            fontSize = 12.sp,
//                            color = MaterialTheme.colorScheme.onSurface
//                        )
//                        Text(
//                            text = actualDeparture.toAmPmTime(),
//                            fontWeight = FontWeight.Bold,
//                            fontSize = 12.sp,
//                            color = MaterialTheme.colorScheme.onSurface
//                        )
//                    }
//
//                }
//            }




        }
    }
}


//fun String?.toAmPmTime(): String {
//    if (this.isNullOrBlank()) return "-"
//    return try {
//        val formatter = java.time.format.DateTimeFormatter.ofPattern("HH:mm")
//        val time = java.time.LocalTime.parse(this, formatter)
//        time.format(java.time.format.DateTimeFormatter.ofPattern("hh:mm a"))
//    } catch (e: Exception) {
//        this
//    }
//}

@Preview(showBackground = true)
@Composable
fun StationPillPreview() {
    StationPill()
}