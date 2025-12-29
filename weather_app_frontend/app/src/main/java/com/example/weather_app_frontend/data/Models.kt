package com.example.weather_app_frontend.data

// PUBLIC_INTERFACE
/**
 * Represents a daily forecast item.
 */
data class DailyForecast(
    val day: String,
    val minTempC: Int,
    val maxTempC: Int,
    val condition: String
)

// PUBLIC_INTERFACE
/**
 * Represents current weather details.
 */
data class CurrentWeather(
    val location: String,
    val tempC: Int,
    val feelsLikeC: Int,
    val condition: String,
    val windKph: Int,
    val humidity: Int
)

// PUBLIC_INTERFACE
/**
 * Aggregated weather response containing current and multi-day forecast.
 */
data class WeatherResponse(
    val current: CurrentWeather,
    val forecast: List<DailyForecast>
)

// PUBLIC_INTERFACE
/**
 * Basic sealed result type to wrap success/error states.
 */
sealed class DataResult<out T> {
    data class Success<T>(val data: T): DataResult<T>()
    data class Error(val throwable: Throwable): DataResult<Nothing>()
}
