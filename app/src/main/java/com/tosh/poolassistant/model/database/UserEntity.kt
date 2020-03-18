package com.tosh.poolassistant.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey
    val phone: String,
    val name: String,
    val id: Int

)