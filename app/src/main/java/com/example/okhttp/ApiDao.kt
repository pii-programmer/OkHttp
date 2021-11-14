package com.example.okhttp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ApiDao {
    @Insert
    fun insert(API: MutableList<API>)

    @Query("SELECT * FROM api_table")
    fun selectAll(): List<API>

    @Query("DELETE FROM api_table")
    fun deleteAll()
}