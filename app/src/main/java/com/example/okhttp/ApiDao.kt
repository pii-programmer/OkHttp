package com.example.okhttp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ApiDao {
    @Insert
    fun insert(API: MutableList<API>)

    @Query("SELECT * FROM API_table")
    fun selectAll(): List<API>

    @Query("DELETE FROM API_table")
    fun deleteAll()
}