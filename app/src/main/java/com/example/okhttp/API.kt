package com.example.okhttp

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "api_table")
data class API (
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    var text:String? = null
)