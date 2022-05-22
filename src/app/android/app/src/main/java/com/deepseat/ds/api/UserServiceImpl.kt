package com.deepseat.ds.api

import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.net.CookieManager

object UserServiceImpl {
    val BASE_URL = "http://soc06212.iptime.org:8080"

    private val okHttpClient: OkHttpClient
        get() {
            return OkHttpClient.Builder()
                .cookieJar(JavaNetCookieJar(CookieManager()))
                .build()
        }

    private val retrofit: Retrofit
        get() {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

    val service = retrofit.create(UserService::class.java)
}