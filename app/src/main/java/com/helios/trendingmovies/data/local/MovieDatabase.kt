package com.helios.trendingmovies.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.helios.trendingmovies.data.local.model.RoomMovie

@Database(entities = [RoomMovie::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class, ListStringConverter::class)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
}