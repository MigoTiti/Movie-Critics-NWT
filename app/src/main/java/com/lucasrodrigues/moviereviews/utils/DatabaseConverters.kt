package com.lucasrodrigues.moviereviews.utils

import androidx.room.TypeConverter
import java.util.*

object DatabaseConverters {

    @JvmStatic
    @TypeConverter
    fun toDate(dateLong: Long?): Date? {
        return dateLong?.let { Date(it) }
    }

    @JvmStatic
    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }
}