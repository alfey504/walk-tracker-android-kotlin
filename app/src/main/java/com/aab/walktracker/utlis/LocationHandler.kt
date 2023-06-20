package com.aab.walktracker.utlis

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

class LocationHandler(private val context: Context) {



    private val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    // starts location checking with given interval and location callback
    fun startLocationUpdates(interval: Long, locationCallback: LocationCallback){
        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_BALANCED_POWER_ACCURACY,
            interval
        ).setMinUpdateIntervalMillis(10*1000).build()

        if(checkPermission()){
            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper())
            Log.i("LocationHandler", "Location updates started")
        }
    }

    // checking permission for location
    private fun checkPermission(): Boolean {

        val accessFineLocation = ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION)
        val accessCoarseLocation = ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION)


        val hasFineLocationPermission = accessFineLocation == PackageManager.PERMISSION_GRANTED
        val hasCoarseLocationPermission = accessCoarseLocation == PackageManager.PERMISSION_GRANTED


        if(Build.VERSION.SDK_INT == Build.VERSION_CODES.Q){
            val accessBackgroundLocation = ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION)
            val hasBackgroundLocationPermission = accessBackgroundLocation == PackageManager.PERMISSION_GRANTED
            return  hasFineLocationPermission && hasCoarseLocationPermission && hasBackgroundLocationPermission
        }

        return hasFineLocationPermission && hasCoarseLocationPermission
    }
}