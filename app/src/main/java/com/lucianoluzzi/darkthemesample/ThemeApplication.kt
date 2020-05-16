package com.lucianoluzzi.darkthemesample

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class ThemeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val theme = ThemeProvider(this).getThemeFromPreferences()
        AppCompatDelegate.setDefaultNightMode(theme)
    }
}