package com.deepseat.ds.api

import com.deepseat.ds.vo.ResponseBody
import com.deepseat.ds.vo.UserVO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserService {

    @POST("/id-check")
    fun checkIDDuplicate(@Query("userID") userID: String): Call<ResponseBody<Boolean>>

    @POST("/nickname-check")
    fun checkNicknameDuplicate(@Query("nickname") nickname: String): Call<ResponseBody<Boolean>>

    @POST("/register")
    fun registerUser(
        @Query("userID") userID: String,
        @Query("userPW") userPW: String,
        @Query("userPWCheck") userPWCheck: String,
        @Query("nickname") nickname: String,
        @Query("email") email: String
    ): Call<ResponseBody<String>>

    @POST("/login")
    fun loginUser(
        @Query("userID") userID: String,
        @Query("userPW") userPW: String
    ): Call<ResponseBody<String>>

    @POST("/user")
    fun getUser(): Call<ResponseBody<UserVO>>

    @POST("/edit-user")
    fun editUser(@Query("nickname") nickname: String): Call<ResponseBody<Boolean>>

}