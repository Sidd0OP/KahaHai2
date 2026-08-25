package com.app.trainview.features.search

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.trainview.R

@Composable
fun RecentSearchItem(
    trainNumber: String,
    trainName: String,
    fromStation: String,
    toStation: String,
    modifier: Modifier = Modifier,
    onClick: (trainNumber: String) -> Unit = {}
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(27.dp),
        color = Color.White,
        border = BorderStroke(1.dp, Color.Black.copy(alpha = 0.05f)),
        onClick = { onClick(trainNumber) }
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.Center
        ) {
            // Top row: Train number badge + Train name
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            color = Color(0xFF83BCFF),
                            shape = RoundedCornerShape(15.dp)
                        )
                        .padding(horizontal = 16.dp, vertical = 2.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = trainNumber,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = FontFamily(Font(R.font.poppins_medium)),
                        color = Color.Black
                    )
                }

                // Train name
                Text(
                    text = trainName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = FontFamily(Font(R.font.poppins_medium)),
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Bottom row: From station → To station
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                // From station pill
                StationPill(stationName = fromStation)

                // Arrow icon
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "to",
                    modifier = Modifier.size(14.dp),
                    tint = Color(0xFF1D1B20)
                )

                // To station pill
                StationPill(stationName = toStation)
            }
        }
    }
}

@Composable
private fun StationPill(stationName: String) {
    Box(
        modifier = Modifier
            .background(
                color = Color(0xFFEDF2FB),
                shape = RoundedCornerShape(15.dp)
            )
            .padding(horizontal = 12.dp, vertical = 2.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stationName,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = FontFamily(Font(R.font.poppins_regular)),
            color = Color.Black
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F5F5)
@Composable
private fun RecentSearchItemPreview() {
    RecentSearchItem(
        trainNumber = "12345",
        trainName = "Rajdhani Express",
        fromStation = "NDLS",
        toStation = "BCT"
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F5F5, name = "Long station names")
@Composable
private fun RecentSearchItemLongNamePreview() {
    RecentSearchItem(
        trainNumber = "22691",
        trainName = "Rajdhani Express Superfast Long Name",
        fromStation = "Yesvantpur",
        toStation = "Hazrat Nizamuddin"
    )
}

