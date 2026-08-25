package com.app.trainview.features.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.trainview.R
import com.app.trainview.features.search.RecentSearchItem
import com.app.trainview.features.search.SearchViewModel
import com.app.trainview.features.search.TrainSearchBar
import com.app.trainview.network.LiveTrainClient
import com.app.trainview.services.TrainService
import com.app.trainview.ui.theme.TrainViewTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun HomeScreen(goToMap: () -> Unit, viewModel: SearchViewModel = viewModel()) {

    val trainClient = LiveTrainClient.retrofit.create(TrainService::class.java)
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Kaha Hai",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily(Font(R.font.poppins_bold)),
                        color = Color.Black
                    )
                }
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.spacedBy(
                12.dp,
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = innerPadding.calculateTopPadding() + 16.dp, horizontal = 16.dp)

        ) {
            TrainSearchBar(
                query = viewModel.searchQuery,
                onQueryChange = { query -> viewModel.onSearchQueryEnter(query) },
                onDateClick = {}
            )

            RecentSearchItem(
                trainNumber = "12001",
                trainName = "Bhopal Shatabdi Express",
                fromStation = "New Delhi",
                toStation = "Rani Kamlapati",
                onClick = { /* handle tap */ }
            )
            RecentSearchItem(
                trainNumber = "12001",
                trainName = "Bhopal Shatabdi Express",
                fromStation = "New Delhi",
                toStation = "Rani Kamlapati",
                onClick = { /* handle tap */ }
            )
            RecentSearchItem(
                trainNumber = "12001",
                trainName = "Bhopal Shatabdi Express",
                fromStation = "New Delhi",
                toStation = "Rani Kamlapati",
                onClick = { /* handle tap */ }
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    TrainViewTheme {
        HomeScreen(goToMap = {})
    }
}