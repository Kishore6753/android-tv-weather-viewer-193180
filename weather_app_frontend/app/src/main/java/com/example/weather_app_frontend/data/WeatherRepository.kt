package com.example.weather_app_frontend.data

/**
 * Repository layer for weather data.
 * Swap WeatherService with a Retrofit-backed service in production.
 */
class WeatherRepository(
    private val service: WeatherService = WeatherService()
) {
    // PUBLIC_INTERFACE
    /**
     * Load weather for the provided location name.
     */
    suspend fun loadWeather(location: String): DataResult<WeatherResponse> {
        return service.getWeatherForLocation(location)
    }
}
