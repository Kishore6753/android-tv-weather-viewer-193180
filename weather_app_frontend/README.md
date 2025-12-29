# Android TV Weather App Frontend

This app renders a TV-optimized weather UI using a modern, card-based layout and the Ocean Professional theme (blue primary accents with amber secondary highlights). The UI is fully navigable with a TV remote (D‑pad), with clear focus states and smooth scale transitions.

What’s included:
- Home screen with:
  - Location selector (TV-friendly dialog)
  - Current conditions card
  - 5-day forecast tiles
  - Empty/Error fallback
- Ocean Professional theme:
  - Primary: #2563EB (Ocean Blue)
  - Secondary: #F59E0B (Amber)
  - Backgrounds: deep navy surfaces
- Mock data by default (no API key required)

Architecture overview:
- data/
  - Models.kt: Data classes for current and forecast weather, plus a Result wrapper
  - WeatherService.kt: Placeholder service returning mock data
  - WeatherRepository.kt: Repository layer (swap service with Retrofit later)
- ui/
  - HomeViewModel.kt: LiveData state for location, weather, loading, and error
  - HomeActivity.kt: Binds views, handles D‑pad focus, updates UI, opens location dialog

Run:
- Open the project in Android Studio and run the app on an Android TV emulator/device.
- The app launches into HomeActivity and displays mock weather for “San Francisco”.

Wire a real weather API later:
1) Add dependencies (Retrofit and Gson are already in gradle):
   - Use the existing Retrofit and Gson in app/build.gradle.kts.
2) Create a Retrofit interface (e.g., RealWeatherService) with your chosen API.
3) Replace WeatherService in WeatherRepository with your Retrofit implementation.
4) Configure API base URL and key via environment or a secure config provider:
   - Do NOT hardcode keys in source.
   - You can use BuildConfig fields or a local.properties entry during development.
5) Update parsing and mapping to match your provider’s response structure.

Focus and TV notes:
- All interactive elements are focusable and show scale + highlight.
- Overscan-safe margins keep content visible across TV sets.
- Landscape orientation is enforced for TV.

```

Instructions to enable production API:
- Create a new service class and implement network calls.
- Inject it into WeatherRepository.
- Test on device with network permission enabled (already in manifest).
