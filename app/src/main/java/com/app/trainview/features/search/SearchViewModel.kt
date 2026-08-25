package com.app.trainview.features.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.app.trainview.model.search.SearchResultData

class SearchViewModel : ViewModel() {

    var searchQuery: String by mutableStateOf("");
//    var searchResultDataList: MutableList<SearchResultData> by mutableStateListOf<SearchResultData>()



    fun onSearchQueryEnter(query: String)
    {
        searchQuery = query;
    }

    // does network call to
    fun onSearchResultTap(trainNumber: String)
    {

    }



}