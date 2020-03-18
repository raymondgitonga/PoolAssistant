package com.tosh.poolassistant.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tosh.poolassistant.model.database.UserEntity
import com.tosh.poolassistant.model.network.RetrofitClient
import com.tosh.poolassistant.model.repository.MainRepository
import com.tosh.poolassistant.util.addToSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : BaseViewModel(application) {

    private val disposable = CompositeDisposable()
    private val client = RetrofitClient()
    private val repository: MainRepository = MainRepository(application)

    fun userLogin(phone: String, password: String): LiveData<String> {
        val loginResponse = MutableLiveData<String>()

        disposable.add(
            client.userLogin(phone, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        loginResponse.value = it.message
                        if (it.message == "successful") {
                            for (user in it.user) {
                                val newUser = UserEntity(
                                    id = user.id,
                                    name = user.name,
                                    phone = user.phone
                                )

                                insert(newUser)
                                addToSharedPreferences(
                                    getApplication(),
                                    newUser.phone
                                )
                            }
                        } else {
                            loginResponse.value = it.message
                        }

                    },
                    {
                        // add error handling
                    }
                )
        )

        return loginResponse
    }

    fun insert(userEntity: UserEntity) {
        repository.insert(userEntity)
    }

    fun deleteUser() {
        launch {
            repository.deleteUser()
        }
    }

    fun getUserDetails(): LiveData<List<UserEntity>> {
        return repository.getUserDetails()
    }
}