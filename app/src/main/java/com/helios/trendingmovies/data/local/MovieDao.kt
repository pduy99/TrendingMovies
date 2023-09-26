package com.helios.trendingmovies.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.helios.trendingmovies.data.local.model.RoomMovie
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface MovieDao {
    @Update
    suspend fun update(roomMovie: RoomMovie)

    @Delete
    suspend fun delete(roomMovie: RoomMovie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(roomMovie: RoomMovie)

    @Query("SELECT * FROM movies WHERE id = :id")
    fun getMovieById(id: Int): Flow<RoomMovie?>

    @Query("SELECT * FROM movies WHERE createdAt < :date ORDER BY createdAt ASC")
    fun getAllMovies(date: Date = Date()): Flow<List<RoomMovie>>
}