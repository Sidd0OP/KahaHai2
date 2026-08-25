package com.app.trainview.features.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.trainview.R
import com.app.trainview.features.search.RecentSearchItem
import com.app.trainview.features.search.SearchViewModel
import com.app.trainview.features.search.TrainSearchBar
import com.app.trainview.network.RetrofitClient
import com.app.trainview.services.TrainService
import com.app.trainview.ui.theme.TrainViewTheme

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun HomeScreen(goToMap: () -> Unit, viewModel: SearchViewModel = viewModel()) {

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
                onDateClick = { }
            )


            if (viewModel.isSearching) {
                CircularProgressIndicator(
                    modifier = Modifier.size(32.dp),
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )
            } else {
                viewModel.searchResultDataList.forEach { data ->

                    RecentSearchItem(
                        trainNumber = data.number,
                        trainName = data.name,
                        fromStation = data.source,
                        toStation = data.dest,
                        onClick = { trainNumber: String -> viewModel.onSearchResultTap(trainNumber = trainNumber) }
                    )

                }
            }





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