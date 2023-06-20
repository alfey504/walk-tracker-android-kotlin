package com.aab.walktracker.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.nfc.Tag
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.aab.walktracker.R
import com.aab.walktracker.utlis.LocationHandler
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import kotlinx.coroutines.*

class WalkTrackingService : Service() {

    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "LocationServiceChannel"
        private const val NOTIFICATION_CHANNEL_NAME = "Location Service"
        private const val NOTIFICATION_ID = 123
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createNotification(): Notification {
        val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setContentTitle("Location Service")
            .setContentText("Running")
            .setSmallIcon(R.mipmap.ic_launcher) // Replace with your app's icon
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        return notificationBuilder.build()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i("WalkTrackingService", "Service started")
        createNotificationChannel()
        startForeground(NOTIFICATION_ID, createNotification())
        startWalk()
        CoroutineScope(Dispatchers.IO).launch {
            checking()
        }
        return START_STICKY
    }

    // start tracking the walk
    private fun startWalk(){
        val locationHandler = LocationHandler(this)
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                locationResult.lastLocation?.let { location ->
                    Log.i("WalkTrackingService", "location = ${location.latitude}, ${location.longitude}")
                }
            }
        }
        locationHandler.startLocationUpdates(5000, locationCallback)
    }

    private suspend fun checking(){
        withContext(Dispatchers.IO){
            while (true){
                Log.i("WalkTrackerService-> checker", "Service is running")
                delay(5000)
            }
        }
    }

}