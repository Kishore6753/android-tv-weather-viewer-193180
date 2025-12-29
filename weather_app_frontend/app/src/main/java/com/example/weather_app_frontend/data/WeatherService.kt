package com.example.weather_app_frontend.data

import kotlinx.coroutines.delay

/**
 * Placeholder weather service.
 * Replace implementation with a real API client (Retrofit) when wiring to backend.
 */
class WeatherService {

    // PUBLIC_INTERFACE
    /**
     * Fetch weather for a given location using mock data.
     * Simulates network delay and returns predictable data for UI previews.
     */
    suspend fun getWeatherForLocation(location: String): DataResult<WeatherResponse> {
        return try {
            // Simulate network delay
            delay(350)

            val current = CurrentWeather(
                location = location,
                tempC = 18,
                feelsLikeC = 17,
                condition = "Partly Cloudy",
                windKph = 12,
                humidity = 68
            )
            val forecast = listOf(
                DailyForecast("Mon", 12, 20, "Sunny"),
                DailyForecast("Tue", 11, 19, "Cloudy"),
                DailyForecast("Wed", 10, 17, "Showers"),
                DailyForecast("Thu", 12, 18, "Partly Cloudy"),
                DailyForecast("Fri", 13, 21, "Sunny")
            )
            DataResult.Success(WeatherResponse(current, forecast))
        } catch (t: Throwable) {
            DataResult.Error(t)
        }
    }
}
