package com.lucianoluzzi.darkthemesample.notifications

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

class ChannelManager(private val context: Context) {

    @SuppressLint("InlinedApi")
    fun createSettingsChannels() {
        createNotificationChannel(
            id = NOTIFICATIONS_OFF_CHANNEL_ID,
            name = NOTIFICATIONS_OFF_CHANNEL_NAME,
            description = NOTIFICATIONS_OFF_CHANNEL_DESCRIPTION,
            importance = NotificationManager.IMPORTANCE_HIGH
        )
        createNotificationChannel(
            id = NOTIFICATIONS_ON_CHANNEL_ID,
            name = NOTIFICATIONS_ON_CHANNEL_NAME,
            description = NOTIFICATIONS_ON_CHANNEL_DESCRIPTION,
            importance = NotificationManager.IMPORTANCE_DEFAULT
        )
    }

    fun getChannelId(type: SettingsNotificationManager.NotificationType) = when (type) {
        SettingsNotificationManager.NotificationType.TURNED_OFF -> NOTIFICATIONS_OFF_CHANNEL_ID
        SettingsNotificationManager.NotificationType.TURNED_ON -> NOTIFICATIONS_ON_CHANNEL_ID
    }

    @SuppressLint("InlinedApi")
    fun getPriority(type: SettingsNotificationManager.NotificationType) = when (type) {
        SettingsNotificationManager.NotificationType.TURNED_OFF -> NotificationManager.IMPORTANCE_HIGH
        SettingsNotificationManager.NotificationType.TURNED_ON -> NotificationManager.IMPORTANCE_DEFAULT
    }

    private fun createNotificationChannel(
        id: String,
        name: String,
        description: String,
        importance: Int
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                id,
                name,
                importance
            ).apply {
                this.description = description
            }

            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private companion object {
        private const val NOTIFICATIONS_OFF_CHANNEL_ID = "notifications_off_channel_id"
        private const val NOTIFICATIONS_ON_CHANNEL_ID = "notifications_on_channel_id"
        private const val NOTIFICATIONS_OFF_CHANNEL_NAME = "notifications_turned_off"
        private const val NOTIFICATIONS_OFF_CHANNEL_DESCRIPTION =
            "Notify when notifications are turned off"
        private const val NOTIFICATIONS_ON_CHANNEL_DESCRIPTION =
            "Notify when notifications are turned on"
        private const val NOTIFICATIONS_ON_CHANNEL_NAME = "notifications_turned_on"
    }
}