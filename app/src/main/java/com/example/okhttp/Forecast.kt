package com.example.okhttp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "forecast")
data class Forecast (
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var date:String? = null,
    var telop:String? = null,
    var detail:String? = null
)
/** Errorログ **/
// カラムは List<String> や ArrayList<String> や Serializable 型ではデータベースに保存できない（Cannot figure out how to save this field into database）
/** Entity **/
// あとから detail 追加
// Entityの変更を反映させる必要がある
// Migrationパス