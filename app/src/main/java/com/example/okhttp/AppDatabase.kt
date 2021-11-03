package com.example.okhttp

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(API::class), version = 1, exportSchema = false)
abstract class AppDatabase:RoomDatabase() {
    abstract fun ApiDao():ApiDao
}