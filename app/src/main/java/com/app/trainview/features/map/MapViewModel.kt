package com.app.trainview.features.map

import androidx.lifecycle.ViewModel
import com.app.trainview.model.train.LiveTrain
import com.app.trainview.repository.LiveTrainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(private val repository: LiveTrainRepository) : ViewModel() {

    val cachedTrain: StateFlow<LiveTrain?> = repository.cachedTrain

    fun clearCachedTrain() {
        repository.clear()
    }


}