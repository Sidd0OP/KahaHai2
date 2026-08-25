package com.app.trainview.features.search

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.trainview.model.search.SearchResultData
import com.app.trainview.network.RetrofitClient
import com.app.trainview.services.SearchService
import com.app.trainview.services.TrainService
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class SearchViewModel : ViewModel() {

    //network
    val searchClient: SearchService = RetrofitClient.retrofit.create(SearchService::class.java)
    val trainClient: TrainService = RetrofitClient.retrofit.create(TrainService::class.java)

    //state and search and result data
    var searchQuery: String by mutableStateOf("");
    val searchResultDataList: SnapshotStateList<SearchResultData> = mutableStateListOf()
    var isSearching by mutableStateOf(false)
        private set

    var isLoadingTrain by mutableStateOf(false)
        private set

    //coroutine for the async call
    private var searchJob: Job? = null
    private var trainJob: Job? = null

    fun onSearchQueryEnter(query: String)
    {
        //dont search while train is loading
        if(isLoadingTrain)return

        searchQuery = query
        searchJob?.cancel()

        if (query.isBlank()) {
            searchResultDataList.clear()
            return
        }

        searchJob = viewModelScope.launch {
            delay(400L.milliseconds)
            performSearch(query)
        }

    }

    private suspend fun performSearch(query: String)
    {
        Log.i("search", query)

        isSearching = true
        try {
            val response = searchClient.getTrainInfo(query = query);
            searchResultDataList.clear()
            response.data?.let { searchResultDataList.addAll(it) }

        }catch (e : Exception){
            Log.e("search", e.toString())
        } finally {
            isSearching = false
        }

    }

    // does network call to
    fun onSearchResultTap(trainNumber: String)
    {
        Log.i("search", trainNumber)

        trainJob?.cancel()

        trainJob = viewModelScope.launch {
            searchTrain(trainNumber = trainNumber)
        }

    }

    private suspend fun searchTrain(trainNumber: String)
    {
        isLoadingTrain = true
        try {
            val response = trainClient.getTrainLiveStatus(number = trainNumber)

        }catch (e : Exception){
            Log.e("search", e.toString())
        } finally {
            isSearching = false
        }
    }



}