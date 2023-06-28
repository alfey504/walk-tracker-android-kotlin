package com.aab.walktracker.database.daos

import androidx.room.*
import com.aab.walktracker.database.enitites.Walk
import com.google.android.gms.common.api.Status

@Dao
interface WalkDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWalk(walk: Walk): Long

    @Query("UPDATE walk SET walk_status = :walkStatus WHERE walk_id = :walkId")
    suspend fun updateWalkStatus(walkId: Int, walkStatus: String)
}