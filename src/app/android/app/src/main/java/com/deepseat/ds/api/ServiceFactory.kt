package com.deepseat.ds.api

import com.deepseat.ds.api.service.*
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.net.CookieManager

object ServiceFactory {
    val BASE_URL = "http://soc06212.iptime.org:8080"

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .cookieJar(JavaNetCookieJar(CookieManager()))
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val userService = retrofit.create(UserService::class.java)
    val docService = retrofit.create(DocumentService::class.java)
    val commentService = retrofit.create(CommentService::class.java)
    val likeService = retrofit.create(LikeService::class.java)
    val apiService = retrofit.create(ObserverAPIService::class.java)
}