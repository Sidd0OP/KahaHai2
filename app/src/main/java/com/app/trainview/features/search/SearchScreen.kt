package com.app.trainview.features.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.trainview.R
import com.app.trainview.R.font.poppins_medium

@Composable
fun SearchScreen() {

}

@Composable
fun TrainSearchBar(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    selectedDate: String = "Today",
    onDateClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .shadow(
                elevation = 12.dp,
                shape = RoundedCornerShape(40.dp),
                clip = false
            )
            .background(Color.White, RoundedCornerShape(40.dp))
            .padding(start = 20.dp, end = 6.dp, top = 5.dp, bottom = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Text input
        BasicTextField(
            value = query,
            onValueChange = onQueryChange,
            modifier = Modifier.weight(1f),
            textStyle = TextStyle(
                fontFamily = FontFamily(Font(poppins_medium)),
                fontSize = 14.sp,
                color = Color.Black
            ),
            singleLine = true,
            decorationBox = { innerTextField ->
                if (query.isEmpty()) {
                    Text(
                        text = "Enter Train Name/No",
                        style = TextStyle(
                            fontFamily = FontFamily(Font(poppins_medium)),
                            fontSize = 14.sp,
                            color = Color.Black.copy(alpha = 0.3f)
                        )
                    )
                }
                innerTextField()
            }
        )

        // Date picker button
        Row(
            modifier = Modifier
                .height(42.dp)
                .background(
                    color = Color(0xFFEDF2FB),
                    shape = RoundedCornerShape(40.dp)
                )
                .clickable(onClick = onDateClick)
                .padding(horizontal = 18.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = selectedDate,
                style = TextStyle(
                    fontFamily = FontFamily(Font(poppins_medium)),
                    fontSize = 14.sp,
                    color = Color.Black
                )
            )
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "Select date",
                modifier = Modifier.size(24.dp),
                tint = Color(0xFF1D1B20)
            )
        }
    }

}

@Preview(showBackground = true, backgroundColor = 0xFFF5F5F5, name = "With query")
@Composable
private fun TrainSearchBarFilledPreview() {

    TrainSearchBar(
        query = "query",
        onQueryChange = { },
        selectedDate = "Tomorrow",
        onDateClick = {}
    )
}

