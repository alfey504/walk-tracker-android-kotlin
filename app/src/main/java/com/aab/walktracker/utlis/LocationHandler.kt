package com.aab.walktracker.utlis

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Looper
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

class LocationHandler(private val context: Context) {

    private val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    fun startLocationUpdates(interval: Long, locationCallback: LocationCallback){
        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            interval
        ).build()

        if(checkPermission()){
            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper())
        }
    }

    private fun checkPermission(): Boolean{
        val accessFineLocation = ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION)
        val accessCoarseLocation = ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION)

        val hasFineLocationPermission = accessFineLocation != PackageManager.PERMISSION_GRANTED
        val hasCoarseLocationPermission = accessCoarseLocation != PackageManager.PERMISSION_GRANTED

        return hasFineLocationPermission && hasCoarseLocationPermission
    }
}