package com.example.okhttp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "forecast")
data class Forecast (
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var date:String? = null,
    var telop:String? = null,
    @ColumnInfo(name = "detail") var detail:String? = null
)
/** Entity **/
// あとから detail 追加
// Entityの変更を反映させる必要がある
// Migrationパス