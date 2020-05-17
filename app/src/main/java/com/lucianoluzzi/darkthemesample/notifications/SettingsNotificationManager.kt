package com.lucianoluzzi.darkthemesample.notifications

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.lucianoluzzi.darkthemesample.R

class SettingsNotificationManager(private val context: Context) {
    private val channelManager = ChannelManager(context)

    init {
        channelManager.createSettingsChannels()
    }

    fun showNotificationsTurnedOn(title: String, text: String) {
        showNotification(title, text, NotificationType.TURNED_ON)
    }

    fun showNotificationsTurnedOff(title: String, text: String) {
        showNotification(title, text, NotificationType.TURNED_OFF)
    }

    fun getNotiticationsStatusDescription(isTurnedOn: Boolean) = if (isTurnedOn)
        "Notifications enabled"
    else
        "Notifications disabled"

    private fun showNotification(title: String, text: String, type: NotificationType) {
        val priority = channelManager.getPriority(type)
        val channelId = channelManager.getChannelId(type)

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle(title)
            .setContentText(text)
            .setPriority(priority)
            .setOngoing(type == NotificationType.TURNED_OFF)
        val notification = builder.build()

        if (type == NotificationType.TURNED_OFF)
            notification.flags = Notification.FLAG_ONGOING_EVENT

        NotificationManagerCompat.from(context).notify(NotificationIdGenerator().getID(), notification)
    }

    enum class NotificationType {
        TURNED_ON, TURNED_OFF
    }
}