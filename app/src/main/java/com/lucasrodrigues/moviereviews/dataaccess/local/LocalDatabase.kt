package com.lucasrodrigues.moviereviews.dataaccess.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lucasrodrigues.moviereviews.dataaccess.local.dao.ReviewDao
import com.lucasrodrigues.moviereviews.model.Review
import com.lucasrodrigues.moviereviews.utils.DatabaseConverters

@Database(
    entities = [
        Review::class,
    ],
    version = 1
)
@TypeConverters(DatabaseConverters::class)
abstract class LocalDatabase : RoomDatabase() {

    companion object {
        @Volatile
        private var INSTANCE: LocalDatabase? = null

        fun getInstance(context: Context): LocalDatabase =
            INSTANCE
                ?: synchronized(this) {
                    INSTANCE
                        ?: buildDatabase(
                            context
                        ).also {
                            INSTANCE = it
                        }
                }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                LocalDatabase::class.java, "database.db"
            )
                .fallbackToDestructiveMigration()
                .build()
    }

    abstract fun reviewDao(): ReviewDao
}