package com.example.okhttp

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = arrayOf(Forecast::class), version = 2, exportSchema = false)
abstract class AppDatabase:RoomDatabase() {
    abstract fun ApiDao():ApiDao
}

val MIGRATION_1_2 = object: Migration(1,2){
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE forecast ADD COLUMN detail TEXT")
    }
}