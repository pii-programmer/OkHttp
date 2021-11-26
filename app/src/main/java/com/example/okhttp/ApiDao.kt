package com.example.okhttp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ApiDao {
    @Insert
    fun insertAll(forecasts: MutableList<Forecast>)

    @Query("SELECT * FROM forecast")
    fun selectAll():MutableList<Forecast>

    @Query("DELETE FROM forecast")
    fun deleteAll()
}
//fun insert(API: MutableList<Forecast>)