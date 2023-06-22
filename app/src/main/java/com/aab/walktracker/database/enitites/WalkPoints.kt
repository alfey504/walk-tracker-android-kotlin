package com.aab.walktracker.database.enitites

import android.location.Location
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "walk_points", foreignKeys = [ForeignKey(
    entity = Walk::class,
    parentColumns = arrayOf("walk_id"),
    childColumns = arrayOf("walk_point_walk_id"),
    onDelete = ForeignKey.CASCADE
)])
data class WalkPoints(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "walk_point_id")
    val walkPointId: Int? = null,

    @ColumnInfo(name = "walk_point_walk_id")
    val walkId: Int,

    @ColumnInfo(name = "walk_point")
    val walkPoint: Location,

    @ColumnInfo(name = "timestamp")
    val timeStamp: Date
)
