package com.deepseat.ds.api.service

import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface LikeService {

    @POST("/liked/{docID}")
    fun markLiked(@Path("docID") docID: Int): Call<String>

    @POST("/liked/{roomID}/{seatID}/{docID}/{commentID}")
    fun markLiked(
        @Path("roomID") roomID: Int,
        @Path("seatID") seatID: Int,
        @Path("docID") docID: Int,
        @Path("commentID") commentID: Int
    ): Call<String>

    @GET("/liked/{roomID}/{seatID}/{docID}")
    fun getLiked(
        @Path("roomID") roomID: Int,
        @Path("seatID") seatID: Int,
        @Path("docID") docID: Int
    ): Call<String>

    @GET("/liked/{roomID}/{seatID}/{docID}/{commentID}")
    fun getLiked(
        @Path("roomID") roomID: Int,
        @Path("seatID") seatID: Int,
        @Path("docID") docID: Int,
        @Path("commentID") commentID: Int
    ): Call<String>

    @DELETE("/liked/{roomID}/{seatID}/{docID}")
    fun deleteLiked(
        @Path("roomID") roomID: Int,
        @Path("seatID") seatID: Int,
        @Path("docID") docID: Int
    ): Call<String>

    @DELETE("/liked/{roomID}/{seatID}/{docID}/{commentID}/")
    fun deleteLiked(
        @Path("roomID") roomID: Int,
        @Path("seatID") seatID: Int,
        @Path("docID") docID: Int,
        @Path("commentID") commentID: Int
    ): Call<String>

}