package com.app.trainview.features.map

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.trainview.network.RetrofitClient
import com.app.trainview.repository.LiveTrainRepository
import com.app.trainview.repository.LiveTrainWithSearchData
import com.app.trainview.services.TrainService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(private val repository: LiveTrainRepository) : ViewModel() {

    val liveTrainFLow: StateFlow<LiveTrainWithSearchData?> = repository.cacheData
    val trainClient: TrainService = RetrofitClient.retrofit.create(TrainService::class.java)

    private var updateTrainJob: Job? = null

    fun clearCachedTrain() {
        repository.clear()
    }

    fun updateData() {

        updateTrainJob?.cancel()

        updateTrainJob = viewModelScope.launch {
            liveTrainFLow.value?.let {liveTrain ->
                try {
                    val response = trainClient.getTrainLiveStatus(number = liveTrain.trainNumber)
                    response.data?.let { repository.update(it) }
                    Log.i("map", response.data.toString())
                } catch (e: Exception) {
                    Log.e("map", e.toString())
                }
            }
        }

    }

}