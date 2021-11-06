package com.example.okhttp

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "API_table")
data class API (
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    var text:String? = null
)