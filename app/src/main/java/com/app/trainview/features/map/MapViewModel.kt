package com.app.trainview.features.map

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.trainview.model.train.LiveMapTrain
import com.app.trainview.network.RetrofitClient
import com.app.trainview.repository.LiveTrainRepository
import com.app.trainview.repository.LiveTrainWithSearchData
import com.app.trainview.services.TrainService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(private val repository: LiveTrainRepository) : ViewModel() {

    val trainClient: TrainService = RetrofitClient.retrofit.create(TrainService::class.java)

    val liveTrainFLow: StateFlow<LiveTrainWithSearchData?> = repository.cacheData
    // all trains for map
    private val _mapTrains = MutableStateFlow<List<LiveMapTrain>>(emptyList())
    val mapTrains: StateFlow<List<LiveMapTrain>> = _mapTrains.asStateFlow()


    private var updateTrainJob: Job? = null
    private var getMapTrainJob: Job? = null

    fun clearCachedTrain() {
        repository.clear()
    }

    fun getMapTrains()
    {
        getMapTrainJob?.cancel()
        getMapTrainJob = viewModelScope.launch {
            try {
                val response = trainClient.getAllTrainMapPosition()
                response.data?.let { _mapTrains.value = it }
                Log.i("map", response.data.toString())
            }catch (e: Exception){
                Log.e("map", e.toString())
            }
        }
    }


    fun updateData() {

        getMapTrains()

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