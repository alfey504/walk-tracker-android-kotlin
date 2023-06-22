package com.aab.walktracker.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.aab.walktracker.database.enitites.Walk

@Dao
interface WalkDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWalk(walk: Walk): Long
}