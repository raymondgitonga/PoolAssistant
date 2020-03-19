package com.tosh.poolassistant.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tosh.poolassistant.model.CartItem
import com.tosh.poolassistant.model.Order
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
    val loadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()
    val orders: MutableLiveData<List<Order>> = MutableLiveData()

    fun refresh() {
        getOrders()
    }

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

    private fun getOrders(): LiveData<List<Order>> {
        loading.value = true
        disposable.add(
            client.getOrders()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        orders.value = it
                        loading.value = false
                    },
                    {
                        loadError.value = true
                        loading.value = false
                    }
                )
        )

        return orders

    }

    fun getSingleOrders(orderNumber: Long): MutableLiveData<List<CartItem>>{
        val orderItem: MutableLiveData<List<CartItem>> = MutableLiveData()
        loading.value = true
        disposable.add(
            client.getSingleOrder(orderNumber)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        it.forEach{order->
                            orderItem.value = order.cartItems
                        }
                        loading.value = false
                    },
                    {
                        loadError.value = true
                        loading.value = false
                    }
                )
        )

        return orderItem

    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}