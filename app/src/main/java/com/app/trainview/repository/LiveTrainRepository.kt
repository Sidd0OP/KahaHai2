package com.app.trainview.repository

import com.app.trainview.model.train.LiveTrain
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class LiveTrainRepository @Inject constructor(){
    private val _cachedTrain = MutableStateFlow<LiveTrain?>(null)
    val cachedTrain: StateFlow<LiveTrain?> = _cachedTrain.asStateFlow()

    fun update(train: LiveTrain) {
        _cachedTrain.value = train
    }

    fun clear() {
        _cachedTrain.value=null
    }
}