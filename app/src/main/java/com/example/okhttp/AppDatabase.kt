package com.example.okhttp

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

// アプリ内にDBを１つ設ける
// そのDBにテーブルを追加してく（ex.  api_table + ApiDao  forecast_table + ForecastDao）
// DBのversion1の時は api_table だけ存在してる
// DBのversion2の時は api_table と forecast_table が存在してる
@Database(entities = arrayOf(Forecast::class), version = 2, exportSchema = false)
abstract class AppDatabase:RoomDatabase() {
    abstract fun ApiDao():ApiDao
}

val MIGRATION_1_2 = object: Migration(1,2){
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE forecast ADD COLUMN detail TEXT")
    }
}