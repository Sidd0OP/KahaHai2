package com.app.trainview.repository

import com.app.trainview.model.train.LiveTrain
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton


data class LiveTrainWithSearchData(var liveTrain: LiveTrain, val trainNumber: String, val date: String  = "")

@Singleton
class LiveTrainRepository @Inject constructor(){

    private val _cachedData = MutableStateFlow<LiveTrainWithSearchData?>(null)
    val cacheData: StateFlow<LiveTrainWithSearchData?> = _cachedData.asStateFlow()

    fun update(train: LiveTrain)
    {
        _cachedData.update { current ->
            current?.copy(liveTrain = train)
        }
    }

    //used by search to create the object from start
    fun setInitial(data: LiveTrainWithSearchData)
    {
        _cachedData.value = data
    }

    fun clear() {
        _cachedData.value=null
    }
}