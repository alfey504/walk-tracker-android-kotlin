package com.aab.walktracker.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aab.walktracker.database.daos.WalkDao
import com.aab.walktracker.database.daos.WalkPointsDao
import com.aab.walktracker.database.enitites.LocationTypeConverter
import com.aab.walktracker.database.enitites.Walk
import com.aab.walktracker.database.enitites.WalkPoints

@Database(entities = [Walk::class, WalkPoints::class], version = 1)
@TypeConverters(LocationTypeConverter::class)
abstract class WalkTrackerDatabase: RoomDatabase() {
    abstract fun walkDao() : WalkDao
    abstract fun walkPointsDao(): WalkPointsDao

    companion object {
        private var INSTANCE: WalkTrackerDatabase? = null

        fun getDatabase(context: Context): WalkTrackerDatabase {
            val tempInstance: WalkTrackerDatabase? = INSTANCE
            tempInstance?.let { return it }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context = context.applicationContext,
                    klass = WalkTrackerDatabase::class.java,
                    name = "walk_tracker_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}