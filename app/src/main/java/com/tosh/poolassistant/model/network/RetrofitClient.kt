package com.tosh.poolassistant.model.network

import com.google.gson.GsonBuilder
import com.tosh.poolassistant.model.LoginResponse
import com.tosh.poolassistant.model.RegisterResponse
import com.tosh.poolassistant.util.Constants.AUTH_BASE_URL
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    private val gson = GsonBuilder()
        .setLenient()
        .create()

    private var httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
        .callTimeout(2L, java.util.concurrent.TimeUnit.MINUTES)
        .connectTimeout(2L, java.util.concurrent.TimeUnit.SECONDS)
        .readTimeout(3L, java.util.concurrent.TimeUnit.SECONDS)
        .writeTimeout(3L, java.util.concurrent.TimeUnit.SECONDS)

    private fun httpInterceptor(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.apply { interceptor.level = HttpLoggingInterceptor.Level.BODY }
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    private val poolerAuth = Retrofit.Builder()
        .baseUrl(AUTH_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(httpInterceptor())
        .client(httpClient.build())
        .build()
        .create(RetrofitApi::class.java)

    fun userLogin(phone: String, password: String): Single<LoginResponse> {
        return  poolerAuth.userLogin(phone, password)
    }

    fun userRegister(name: String, phone: String, password: String, confirmPassword: String): Single<RegisterResponse> {
        return poolerAuth.userRegister(name, phone, password, confirmPassword)
    }
}