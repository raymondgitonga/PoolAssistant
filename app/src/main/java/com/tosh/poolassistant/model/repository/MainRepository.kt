package com.tosh.poolassistant.model.repository

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.tosh.poolassistant.model.database.MainDatabase
import com.tosh.poolassistant.model.database.UserDao
import com.tosh.poolassistant.model.database.UserEntity
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class MainRepository(application: Application) {
    private var userDao: UserDao
    private var userDetails: LiveData<List<UserEntity>>

    init {
        val mainDb: MainDatabase = MainDatabase.getInstance(
            application.applicationContext
        )!!

        userDao = mainDb.userDao()
        userDetails = userDao.getUserDetails()
    }

    fun insert(userEntity: UserEntity) {
        class SaveUser : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg params: Void?): Void? {
                userDao.insertDetails(userEntity)
                return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
            }

        }

        SaveUser().execute()
    }

    fun getUserDetails(): LiveData<List<UserEntity>> {
        return userDetails
    }

    suspend fun deleteUser(){
        coroutineScope {
            launch {
                userDao.deleteUser()
            }
        }
    }
}