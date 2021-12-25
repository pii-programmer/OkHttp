package com.example.okhttp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "api_table")
data class API (
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    var text:String? = null
)