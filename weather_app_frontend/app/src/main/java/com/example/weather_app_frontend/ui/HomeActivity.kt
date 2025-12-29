package com.example.weather_app_frontend.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.weather_app_frontend.R

/**
 * HomeActivity renders the main TV UI with current conditions and forecast tiles.
 * D-pad navigation is enabled and focus states show animated scaling and highlight.
 */
class HomeActivity : FragmentActivity() {

    private lateinit var viewModel: HomeViewModel

    // Header views
    private lateinit var btnLocation: TextView

    // Current card
    private lateinit var cardCurrent: LinearLayout
    private lateinit var tvCurrentLocation: TextView
    private lateinit var tvTemp: TextView
    private lateinit var tvCondition: TextView
    private lateinit var tvFeelsLike: TextView
    private lateinit var tvWind: TextView
    private lateinit var tvHumidity: TextView

    // Forecast tiles
    private lateinit var tileDay1: LinearLayout
    private lateinit var tileDay2: LinearLayout
    private lateinit var tileDay3: LinearLayout
    private lateinit var tileDay4: LinearLayout
    private lateinit var tileDay5: LinearLayout
    private lateinit var tvDay1: TextView
    private lateinit var tvDay2: TextView
    private lateinit var tvDay3: TextView
    private lateinit var tvDay4: TextView
    private lateinit var tvDay5: TextView
    private lateinit var tvDay1Temp: TextView
    private lateinit var tvDay2Temp: TextView
    private lateinit var tvDay3Temp: TextView
    private lateinit var tvDay4Temp: TextView
    private lateinit var tvDay5Temp: TextView

    // Empty/Error
    private lateinit var emptyContainer: LinearLayout
    private lateinit var tvEmpty: TextView
    private lateinit var btnChangeLocation: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        bindViews()
        setupFocusAnimations()
        setupClickHandlers()
        observeViewModel()

        // Initial load with default mock location
        viewModel.refresh()
    }

    private fun bindViews() {
        btnLocation = findViewById(R.id.btnLocation)

        cardCurrent = findViewById(R.id.cardCurrent)
        tvCurrentLocation = findViewById(R.id.tvCurrentLocation)
        tvTemp = findViewById(R.id.tvTemp)
        tvCondition = findViewById(R.id.tvCondition)
        tvFeelsLike = findViewById(R.id.tvFeelsLike)
        tvWind = findViewById(R.id.tvWind)
        tvHumidity = findViewById(R.id.tvHumidity)

        tileDay1 = findViewById(R.id.tileDay1)
        tileDay2 = findViewById(R.id.tileDay2)
        tileDay3 = findViewById(R.id.tileDay3)
        tileDay4 = findViewById(R.id.tileDay4)
        tileDay5 = findViewById(R.id.tileDay5)

        tvDay1 = findViewById(R.id.tvDay1)
        tvDay2 = findViewById(R.id.tvDay2)
        tvDay3 = findViewById(R.id.tvDay3)
        tvDay4 = findViewById(R.id.tvDay4)
        tvDay5 = findViewById(R.id.tvDay5)

        tvDay1Temp = findViewById(R.id.tvDay1Temp)
        tvDay2Temp = findViewById(R.id.tvDay2Temp)
        tvDay3Temp = findViewById(R.id.tvDay3Temp)
        tvDay4Temp = findViewById(R.id.tvDay4Temp)
        tvDay5Temp = findViewById(R.id.tvDay5Temp)

        emptyContainer = findViewById(R.id.emptyContainer)
        tvEmpty = findViewById(R.id.tvEmpty)
        btnChangeLocation = findViewById(R.id.btnChangeLocation)
    }

    private fun setupClickHandlers() {
        btnLocation.setOnClickListener { showLocationDialog() }
        btnChangeLocation.setOnClickListener { showLocationDialog() }
    }

    private fun setupFocusAnimations() {
        val focusIn = AnimationUtils.loadAnimation(this, R.anim.focus_gain)
        val focusOut = AnimationUtils.loadAnimation(this, R.anim.focus_loss)

        val focusableViews = listOf(
            btnLocation, cardCurrent,
            tileDay1, tileDay2, tileDay3, tileDay4, tileDay5,
            btnChangeLocation
        )

        for (v in focusableViews) {
            v.setOnFocusChangeListener { view, hasFocus ->
                if (hasFocus) view.startAnimation(focusIn) else view.startAnimation(focusOut)
            }
        }
    }

    private fun observeViewModel() {
        viewModel.location.observe(this) { loc ->
            btnLocation.text = loc
        }
        viewModel.weather.observe(this) { resp ->
            if (resp != null) {
                emptyContainer.visibility = View.GONE
                tvCurrentLocation.text = resp.current.location
                tvTemp.text = "${resp.current.tempC}°"
                tvCondition.text = resp.current.condition
                tvFeelsLike.text = getString(R.string.feels_like, resp.current.feelsLikeC)
                tvWind.text = getString(R.string.wind, resp.current.windKph)
                tvHumidity.text = getString(R.string.humidity, resp.current.humidity)

                val days = resp.forecast
                if (days.size >= 5) {
                    tvDay1.text = days[0].day
                    tvDay1Temp.text = "${days[0].maxTempC}°/${days[0].minTempC}°"

                    tvDay2.text = days[1].day
                    tvDay2Temp.text = "${days[1].maxTempC}°/${days[1].minTempC}°"

                    tvDay3.text = days[2].day
                    tvDay3Temp.text = "${days[2].maxTempC}°/${days[2].minTempC}°"

                    tvDay4.text = days[3].day
                    tvDay4Temp.text = "${days[3].maxTempC}°/${days[3].minTempC}°"

                    tvDay5.text = days[4].day
                    tvDay5Temp.text = "${days[4].maxTempC}°/${days[4].minTempC}°"
                }
            }
        }
        viewModel.error.observe(this) { err ->
            if (err != null) {
                emptyContainer.visibility = View.VISIBLE
                tvEmpty.text = getString(R.string.error_generic)
            }
        }
    }

    private fun showLocationDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_location, null, false)
        val etSearch: EditText = dialogView.findViewById(R.id.etSearch)
        val loc1: TextView = dialogView.findViewById(R.id.loc1)
        val loc2: TextView = dialogView.findViewById(R.id.loc2)
        val loc3: TextView = dialogView.findViewById(R.id.loc3)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        val clickListener = View.OnClickListener { v ->
            val selected = (v as TextView).text.toString()
            viewModel.setLocation(selected)
            dialog.dismiss()
        }
        loc1.setOnClickListener(clickListener)
        loc2.setOnClickListener(clickListener)
        loc3.setOnClickListener(clickListener)

        // Improve D-pad usability: send focus to search or first item
        dialog.setOnShowListener {
            if (etSearch.text.isNullOrEmpty()) {
                loc1.requestFocus()
            } else {
                etSearch.requestFocus()
            }
        }

        dialog.show()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // Basic remote key handling; allow default focus navigation
        return when (keyCode) {
            KeyEvent.KEYCODE_BACK -> {
                finish()
                true
            }
            else -> super.onKeyDown(keyCode, event)
        }
    }
}
