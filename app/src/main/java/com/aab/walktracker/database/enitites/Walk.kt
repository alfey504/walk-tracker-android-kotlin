package com.aab.walktracker.database.enitites

import android.location.Location
import android.text.Spannable.Factory
import androidx.room.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "walk")
data class Walk(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("walk_id")
    val walkId: Int? = null,

    @ColumnInfo("walk_status")
    val walkStatus: String = "Ongoing"
)

class LocationTypeConverter{

    @TypeConverter
    fun locationToString(location: Location?): String{
        if(location == null) { return  "null" }
        return "${location.latitude}%${location.longitude}"
    }

    @TypeConverter
    fun stringToLocation(stringLocation: String): Location?{
        if(stringLocation == "null") { return null }
        val splitString = stringLocation.split("%")
        val location = Location("LocationTypeConverter")
        location.latitude = splitString[0].toDouble()
        location.longitude = splitString[1].toDouble()
        return location
    }

    @TypeConverter
    fun dateToString(date: Date): String{
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss z", Locale.getDefault())
        // Convert Date to String
        val currentDate = Date()
        return dateFormat.format(currentDate)
    }

    @TypeConverter
    fun stringToDate(dateString: String): Date{
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss z", Locale.getDefault())
        val date = try{
            dateFormat.parse(dateString)
        }catch (exception: ParseException){
            Date()
        }
        return date
    }
}
