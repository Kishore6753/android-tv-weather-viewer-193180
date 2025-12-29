package com.example.weather_app_frontend.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather_app_frontend.data.DataResult
import com.example.weather_app_frontend.data.WeatherRepository
import com.example.weather_app_frontend.data.WeatherResponse
import kotlinx.coroutines.launch

/**
 * ViewModel for Home screen. Holds the selected location and weather state.
 */
class HomeViewModel(
    private val repository: WeatherRepository = WeatherRepository()
) : ViewModel() {

    private val _location = MutableLiveData("San Francisco")
    val location: LiveData<String> = _location

    private val _weather = MutableLiveData<WeatherResponse?>()
    val weather: LiveData<WeatherResponse?> = _weather

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String?>(null)
    val error: LiveData<String?> = _error

    // PUBLIC_INTERFACE
    /**
     * Trigger loading weather for current location.
     */
    fun refresh() {
        val currentLocation = _location.value ?: return
        _loading.value = true
        _error.value = null
        viewModelScope.launch {
            when (val result = repository.loadWeather(currentLocation)) {
                is DataResult.Success -> {
                    _weather.value = result.data
                }
                is DataResult.Error -> {
                    _error.value = result.throwable.message ?: "Unknown error"
                }
            }
            _loading.value = false
        }
    }

    // PUBLIC_INTERFACE
    /**
     * Set a new location and load its weather.
     */
    fun setLocation(newLocation: String) {
        _location.value = newLocation
        refresh()
    }
}
