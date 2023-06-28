package com.aab.walktracker.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.aab.walktracker.database.enitites.WalkPoints


@Dao
interface WalkPointsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWalkPoint(walkPoints: List<WalkPoints>): List<Long>
}