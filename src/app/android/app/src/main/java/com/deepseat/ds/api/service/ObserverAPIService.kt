package com.deepseat.ds.api.service

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ObserverAPIService {

    @GET("/api/room")
    fun getRooms(): Call<String>

    @GET("/api/room/{roomID}")
    fun getSeats(@Path("roomID") roomID: Int): Call<String>

    @GET("/api/room/{roomID}/status")
    fun getStatus(@Path("roomID") roomID: Int): Call<String>

}