package com.aab.walktracker.utlis

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.aab.walktracker.R
import com.aab.walktracker.services.WalkTrackingService

class NotificationHandler {

    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "LocationServiceChannel"
        private const val NOTIFICATION_CHANNEL_NAME = "Location Service"
        private const val NOTIFICATION_ID = 123
    }

    // creates a notification channel
    fun createNotificationChannel(context: Context) {
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    // creates an instance of Notification 
    fun createNotification(context: Context): Notification {
        val notificationBuilder = NotificationCompat.Builder(context,
            NOTIFICATION_CHANNEL_ID
        )
            .setContentTitle("Location Service")
            .setContentText("Running")
            .setSmallIcon(R.mipmap.ic_launcher) // Replace with your app's icon
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        return notificationBuilder.build()
    }
}