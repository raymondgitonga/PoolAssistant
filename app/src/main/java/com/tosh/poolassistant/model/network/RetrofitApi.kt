package com.tosh.poolassistant.model.network

import com.tosh.poolassistant.model.LoginResponse
import com.tosh.poolassistant.model.RegisterResponse
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RetrofitApi {
    @FormUrlEncoded
    @POST("pooler/login")
    fun userLogin(
        @Field("phone") phone: String,
        @Field("password") password: String
    ): Single<LoginResponse>

    @FormUrlEncoded
    @POST("pooler/register")
    fun userRegister(
        @Field("name") name: String,
        @Field("phone") phone: String,
        @Field("password") password: String,
        @Field("confirmPassword") confirmPassword: String
    ): Single<RegisterResponse>
}