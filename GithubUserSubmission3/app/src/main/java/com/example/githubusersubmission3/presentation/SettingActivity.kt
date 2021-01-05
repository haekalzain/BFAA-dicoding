package com.example.githubusersubmission3.presentation

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.githubusersubmission3.Constants.Companion.ALARM_ID_REPEATING
import com.example.githubusersubmission3.Constants.Companion.SP_COLOR
import com.example.githubusersubmission3.Constants.Companion.SP_REMINDER
import com.example.githubusersubmission3.R
import com.example.githubusersubmission3.service.AlarmHelper
import kotlinx.android.synthetic.main.activity_setting.*
import java.util.*

class SettingActivity : AppCompatActivity() {


    private val tag = SettingActivity::class.simpleName
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        backGroundColor()
        setContentView(R.layout.activity_setting)

        sharedPreferences =
            this.getSharedPreferences(SettingActivity::class.simpleName, Context.MODE_PRIVATE)
        languageOnClick()
        reminderOnClick()
    }

    private fun reminderOnClick() {

        val isGreen = sharedPreferences.getBoolean(SP_COLOR, true)

        reminder.setOnClickListener {
            val isActive = sharedPreferences.getBoolean(SP_REMINDER, true)
            if (isActive) {
                onAlarm()
            } else {
                offAlarm()
            }
        }

        if (isGreen) {
            reminderView.setBackgroundResource(R.drawable.ripple_card_green)
        } else {
            reminderView.setBackgroundResource(R.drawable.ripple_card_red)
        }

    }

    private fun onAlarm() {

        reminderView.setBackgroundResource(R.drawable.ripple_card_green)
        sharedPreferences.edit().apply {
            putBoolean(SP_REMINDER, false)
            putBoolean(SP_COLOR, true)
        }.apply()

        AlarmHelper.alarm(
            this,
            "githubUserSubmission",
            getString(R.string.desc_notification),
            ALARM_ID_REPEATING,
            Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, 9)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
            }
        )

        Toast.makeText(this, getString(R.string.successful_reminder), Toast.LENGTH_SHORT).show()

    }

    private fun offAlarm() {

        reminderView.setBackgroundResource(R.drawable.ripple_card_red)
        sharedPreferences.edit().apply {
            putBoolean(SP_REMINDER, true)
            putBoolean(SP_COLOR, false)
        }.apply()

        AlarmHelper.cancelAlarm(this, ALARM_ID_REPEATING)
        Toast.makeText(this, getString(R.string.nonactive_reminder), Toast.LENGTH_SHORT).show()

    }

    private fun languageOnClick() {
        language.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }
    }

    fun backButton(view: View) {
        onBackPressed()
        overridePendingTransition(R.anim.fragment_fade_enter, R.anim.fragment_fade_exit)
    }

    private fun backGroundColor() {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
        window.navigationBarColor = ContextCompat.getColor(this, android.R.color.transparent)
        window.setBackgroundDrawableResource(R.drawable.main_background)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.fragment_fade_enter, R.anim.fragment_fade_exit)
    }
}