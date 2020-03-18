package com.tosh.poolassistant.model.network

import com.tosh.poolassistant.model.LoginResponse
import com.tosh.poolassistant.model.Order
import io.reactivex.Single
import retrofit2.http.*

interface RetrofitApi {
    @FormUrlEncoded
    @POST("pooler/login")
    fun userLogin(
        @Field("phone") phone: String,
        @Field("password") password: String
    ): Single<LoginResponse>

    @GET("/api/v1/cart/all")
    fun getOrders(): Single<List<Order>>
}