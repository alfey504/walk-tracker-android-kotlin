package com.aab.walktracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aab.walktracker.databinding.ActivityMainBinding
import com.aab.walktracker.services.WalkTrackingService

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setOnClickListeners()
    }

    private fun setOnClickListeners(){
        binding.mainActivityStartWalkButton.setOnClickListener {
            val intent = Intent(this, WalkTrackingService::class.java)
            startService(intent)
        }

        binding.mainActivityStopWalkButton.setOnClickListener {
            stopService()
        }
    }

    private fun stopService(){
        val intent = Intent(WalkTrackingService.ACTION_STOP_SERVICE)
        intent.setPackage(packageName)
        sendBroadcast(intent)
    }
}