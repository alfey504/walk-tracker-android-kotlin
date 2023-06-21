package com.aab.walktracker.database.enitites

import android.location.Location
import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class Walk(
    @ColumnInfo("walk_id") val walkId: Int,
    @ColumnInfo("start_location") val startLocation: Location,
    @ColumnInfo("end_location") val endLocation: Location,
    @ColumnInfo("walk_status") val walkStatus: String
)
