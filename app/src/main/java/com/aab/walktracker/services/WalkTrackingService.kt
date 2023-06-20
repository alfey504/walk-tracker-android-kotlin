package com.aab.walktracker.services

import android.app.Service
import android.content.Intent
import android.nfc.Tag
import android.os.IBinder
import android.util.Log
import com.aab.walktracker.utlis.LocationHandler
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult

class WalkTrackingService : Service() {

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i("WalkTrackingService", "Service started")
        startWalk()
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
}