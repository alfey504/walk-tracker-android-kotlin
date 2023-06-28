package com.aab.walktracker.repos

import android.content.Context
import android.location.Location
import android.util.Log
import com.aab.walktracker.database.WalkTrackerDatabase
import com.aab.walktracker.database.daos.WalkPointsDao
import com.aab.walktracker.database.enitites.Walk
import com.aab.walktracker.database.enitites.WalkPoints
import com.google.android.gms.common.api.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class WalkRepo(context: Context) {

    private val walkTrackerDatabase = WalkTrackerDatabase.getDatabase(context)
    private val walkDao = walkTrackerDatabase.walkDao()
    private val walkPointsDao = walkTrackerDatabase.walkPointsDao()

    suspend fun newWalk(): Long{
        return withContext(Dispatchers.IO) {
            val walk = Walk(walkStatus = "Ongoing")
            return@withContext walkDao.insertWalk(walk)
        }
    }

    suspend fun addWalkPoints(walkPoints: List<WalkPoints>) {
        withContext(Dispatchers.IO) {
            walkPointsDao.insertWalkPoint(walkPoints)
        }
    }

    suspend fun updateWalkStatus(walkId: Int, walkStatus: String) {
        withContext(Dispatchers.IO){
            walkDao.updateWalkStatus(walkId, walkStatus)
        }
    }
}