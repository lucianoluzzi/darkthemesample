package com.lucianoluzzi.darkthemesample

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.*
import com.lucianoluzzi.darkthemesample.notifications.SettingsNotificationManager

class SettingsFragment : PreferenceFragmentCompat() {

    private val notificationsManager by lazy {
        SettingsNotificationManager(requireContext())
    }
    private val themeProvider by lazy { ThemeProvider(requireContext()) }
    private val themePreference by lazy {
        findPreference<ListPreference>(getString(R.string.theme_preferences_key))
    }
    private val notificationPreference by lazy {
        findPreference<SwitchPreferenceCompat>(getString(R.string.notification_preferences_key))
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
        setThemePreference()
        setNotificationPreference()
    }

    private fun setThemePreference() {
        themePreference?.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { _, newValue ->
                if (newValue is String) {
                    val theme = themeProvider.getTheme(newValue)
                    AppCompatDelegate.setDefaultNightMode(theme)
                }
                true
            }
        themePreference?.summaryProvider = Preference.SummaryProvider<ListPreference> { preference ->
            themeProvider.getThemeDescriptionForPreference(preference.value)
        }
    }

    private fun setNotificationPreference() {
        notificationPreference?.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { _, newValue ->
                val selectedValue = newValue as Boolean
                if (selectedValue) {
                    notificationsManager.showNotificationsTurnedOn(
                        title = "Notifications enabled",
                        text = "You have enabled the notifications"
                    )
                } else {
                    notificationsManager.showNotificationsTurnedOff(
                        title = "Notifications disabled",
                        text = "You have disabled the notifications"
                    )
                }
                true
            }
        notificationPreference?.summaryProvider = Preference.SummaryProvider<SwitchPreferenceCompat> { preference ->
            SettingsNotificationManager(requireContext()).getNotiticationsStatusDescription(preference.isChecked)
        }
    }
}