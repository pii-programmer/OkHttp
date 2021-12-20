package com.example.okhttp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = arrayOf(Forecast::class), version = 2, exportSchema = false)
abstract class AppDatabase:RoomDatabase() {

    abstract fun ApiDao():ApiDao

    // Database のインスタンスはアプリ内で一つだけにする
    companion object{
        fun getDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "forecast")
                .addMigrations(MIGRATION_1_2)
                .build()
        }
    }
}

val MIGRATION_1_2 = object: Migration(1,2){
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE forecast ADD COLUMN detail TEXT")
    }
}
/** 以下メモ **/
// アプリ内にDBを１つ設ける
// そのDBにテーブルを追加してく（ex.  api_table + ApiDao  forecast_table + ForecastDao）
// DBのversion1の時は api_table だけ存在してる
// DBのversion2の時は api_table と forecast_table が存在してる