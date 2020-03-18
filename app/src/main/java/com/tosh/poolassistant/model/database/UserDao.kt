package com.tosh.poolassistant.model.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDetails(userEntity: UserEntity)

    @Query("SELECT * FROM user_table")
    fun getUserDetails(): LiveData<List<UserEntity>>

    @Query("DELETE FROM user_table")
    suspend fun deleteUser()


}