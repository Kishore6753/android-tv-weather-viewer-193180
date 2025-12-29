package com.example.weather_app_frontend

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.example.weather_app_frontend.ui.HomeActivity

/**
 * Launcher Activity for Android TV.
 * Routes immediately to HomeActivity which renders the weather UI.
 * Keeps LEANBACK_LAUNCHER role while HomeActivity remains the UI host.
 */
class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }
}
