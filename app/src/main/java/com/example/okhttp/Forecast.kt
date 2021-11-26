package com.example.okhttp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "forecast")
data class Forecast (
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    var date:String? = null,
    var telop:String? = null
)
//detail