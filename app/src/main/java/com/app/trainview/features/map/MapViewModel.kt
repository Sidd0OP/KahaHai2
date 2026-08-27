package com.app.trainview.features.map

import android.location.Location
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.trainview.model.train.LiveMapTrain
import com.app.trainview.network.RetrofitClient
import com.app.trainview.repository.LiveTrainRepository
import com.app.trainview.repository.LiveTrainWithSearchData
import com.app.trainview.services.LocationTrackerService
import com.app.trainview.services.TrainService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val trainRepository: LiveTrainRepository,
    private val locationService: LocationTrackerService
) : ViewModel() {

    val trainClient: TrainService = RetrofitClient.retrofit.create(TrainService::class.java)

    //map train data
    val liveTrainFLow: StateFlow<LiveTrainWithSearchData?> = trainRepository.cacheData

    // all trains for map
    private val _mapTrains = MutableStateFlow<List<LiveMapTrain>>(emptyList())
    val mapTrains: StateFlow<List<LiveMapTrain>> = _mapTrains.asStateFlow()

    //user location
    private val _userLocation = MutableStateFlow<Location?>(null)
    val userLocation: StateFlow<Location?> = _userLocation.asStateFlow()


    private var updateTrainJob: Job? = null
    private var getMapTrainJob: Job? = null
    private var getUserLocationJob: Job? = null


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
                    response.data?.let { trainRepository.update(it) }
                    Log.i("map", response.data.toString())
                } catch (e: Exception) {
                    Log.e("map", e.toString())
                }
            }
        }
    }

    fun hasLocationPermission() : Boolean = locationService.hasLocationPermission()

    fun startLocationUpdates()
    {
        if(!hasLocationPermission())return
        getUserLocationJob?.cancel()
        getUserLocationJob = locationService.fetchLocationUpdates(intervalMs = 1000L)
            .onEach { _userLocation.value = it }
            .catch { e -> Log.e("location", e.toString()) }
            .launchIn(viewModelScope)
    }

    fun stopLocationUpdates()
    {
        getUserLocationJob?.cancel()
    }

    fun resetForNewRequest() {
        stopLocationUpdates()
        trainRepository.clear()
    }


}