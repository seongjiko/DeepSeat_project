package com.deepseat.ds.api.service

import retrofit2.Call
import retrofit2.http.*

interface CommentService {

    @POST("/comment/{docID}")
    fun writeComment(
        @Path("docID") docID: Int,
        @Query("content") content: String
    ): Call<String>

    @GET("/comment/{docID}")
    fun getComments(@Path("docID") docID: Int): Call<String>

    @DELETE("/comment/{commentID}")
    fun deleteComment(@Path("commentID") commentID: Int): Call<String>

    @PUT("/comment/{docID}/{commentID}")
    fun editComment(
        @Path("docID") docID: Int,
        @Path("commentID") commentID: Int,
        content: String
    ): Call<String>

}