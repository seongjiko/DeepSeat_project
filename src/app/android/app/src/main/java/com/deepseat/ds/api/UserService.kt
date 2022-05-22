package com.deepseat.ds.api

import com.deepseat.ds.vo.ResponseBody
import com.deepseat.ds.vo.UserVO
import retrofit2.Call
import retrofit2.http.*

interface UserService {

    @POST("/id-check")
    fun checkIDDuplicate(@Query("userID") userID: String): Call<String>

    @POST("/nickname-check")
    fun checkNicknameDuplicate(@Query("nickname") nickname: String): Call<String>

    @POST("/register")
    fun registerUser(
        @Query("userID") userID: String,
        @Query("userPW") userPW: String,
        @Query("userPWCheck") userPWCheck: String,
        @Query("nickname") nickname: String,
        @Query("email") email: String
    ): Call<String>

    @POST("/login")
    fun loginUser(
        @Query("userID") userID: String,
        @Query("userPW") userPW: String
    ): Call<String>

    @POST("/logout")
    fun logoutUser(): Call<String>

    @POST("/user")
    fun getUser(): Call<String>

    @POST("/edit-user")
    fun editUser(@Query("nickname") nickname: String): Call<String>

}