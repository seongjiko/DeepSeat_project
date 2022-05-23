package com.deepseat.ds.api.service

import retrofit2.Call
import retrofit2.http.*

interface DocumentService {

    @POST("/doc/{roomID}/{seatID}")
    fun writeDocument(@Path("roomID") roomID: Int, @Path("seatID") seatID: Int): Call<String>

    @GET("/doc")
    fun getDocuments(): Call<String>

    @GET("/doc/vo")
    fun getDocumentVOs(): Call<String>

    @GET("/doc/{docID}/vo")
    fun getDocumentVO(@Path("docID") docID: Int): Call<String>

    @GET("/doc/{docID}")
    fun getDocument(
        @Path("roomID") roomID: Int,
        @Path("seatID") seatID: Int,
        @Path("docID") docID: Int
    ): Call<String>

    @GET("/doc/{roomID}/{seatID}/vo")
    fun getDocumentVOs(@Path("roomID") roomID: Int, @Path("seatID") seatID: Int): Call<String>

    @GET("/doc/{roomID}/{seatID}")
    fun getDocuments(@Path("roomID") roomID: Int, @Path("seatID") seatID: Int): Call<String>

    @PUT("/doc/{roomID}/{seatID}/{docID}")
    fun editDocument(
        @Path("roomID") roomID: Int,
        @Path("seatID") seatID: Int,
        @Path("docID") docID: Int,
        @Query("content") content: String
    ): Call<String>

    @DELETE("/doc/{roomID}/{seatID}/{docID}")
    fun deleteDocument(
        @Path("roomID") roomID: Int,
        @Path("seatID") seatID: Int,
        @Path("docID") docID: Int
    ): Call<String>

}