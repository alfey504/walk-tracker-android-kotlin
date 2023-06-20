package com.aab.walktracker.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.nfc.Tag
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.aab.walktracker.R
import com.aab.walktracker.utlis.LocationHandler
import com.aab.walktracker.utlis.NotificationHandler
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import kotlinx.coroutines.*

class WalkTrackingService : Service() {

    private lateinit var locationHandler: LocationHandler
    private lateinit var broadcastReceiver: BroadcastReceiver

    companion object {
        private const val NOTIFICATION_ID = 123
        const val  ACTION_STOP_SERVICE = "action_stop_service"
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i("WalkTrackingService", "Service started")
        setForegroundNotification()
        startWalk()
        setBroadCastReceiver()
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcastReceiver)
    }

    // set a foreground notification
    private fun setForegroundNotification(){
        val notificationHandler = NotificationHandler()
        notificationHandler.createNotificationChannel(this)
        startForeground(NOTIFICATION_ID, notificationHandler.createNotification(this))
    }

    // start tracking the walk
    private fun startWalk(){
        locationHandler = LocationHandler(this)
        val locationCallback = createLocationCallback()
        locationHandler.startLocationUpdates(5000, locationCallback)
    }

    // returns an instance of LocationCallback
    private fun createLocationCallback(): LocationCallback{
        return object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                locationResult.lastLocation?.let { location ->
                    Log.i("WalkTrackingService", "location = ${location.latitude}, ${location.longitude}")
                }
            }
        }
    }

    // sets a broadcast receiver
    private fun setBroadCastReceiver(){
        broadcastReceiver = createBroadcastReceiver()
        val intentFilter = IntentFilter(ACTION_STOP_SERVICE)
        registerReceiver(broadcastReceiver, intentFilter)
    }

    // returns an instance of BroadcastReceiver
    private fun createBroadcastReceiver(): BroadcastReceiver{
        return object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                intent?.action?.let { action ->
                   handleActions(action)
                }
            }
        }
    }

    // performs a task based on the given action
    private fun handleActions(action: String){
        when (action) {
            ACTION_STOP_SERVICE -> stopWalk()
        }
    }

    // stop the location update service and stop the service
    private fun stopWalk(){
        locationHandler.stopLocationUpdates()
        stopSelf()
    }




}