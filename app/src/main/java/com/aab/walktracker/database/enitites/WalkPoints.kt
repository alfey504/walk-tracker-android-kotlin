package com.aab.walktracker.database.enitites

import android.location.Location
import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "walk_points")
data class WalkPoints(
    @ColumnInfo(name = "walk_point_id") val walkPointId: Int,
    @ColumnInfo(name = "walk_id") val walkId: Int,
    @ColumnInfo(name = "walk_point") val walkPoint: Location
)
