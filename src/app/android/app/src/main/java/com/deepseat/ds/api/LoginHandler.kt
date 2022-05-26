package com.deepseat.ds.api

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.deepseat.ds.GlobalData
import com.deepseat.ds.R
import com.deepseat.ds.vo.ResponseBody
import com.deepseat.ds.vo.UserVO
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginHandler(private val context: Context) {

    var onLoginCompleteListener: ((responseCode: Int?) -> Unit)? = null
    var onUserGetCompleteListener: ((user: UserVO?) -> Unit)? = null

    public fun login(userID: String, userPW: String) {
        val call: Call<String> = ServiceFactory.userService.loginUser(userID, userPW)

        call.enqueue(object : Callback<String> {
            override fun onResponse(
                call: Call<String>,
                response: Response<String>
            ) {
                val responseBody = Gson().fromJson(response.body(), ResponseBody::class.java)

                onLoginCompleteListener?.let { it(responseBody.responseCode) }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("LoginHandler", t.toString())
                onLoginCompleteListener?.let { it(null) }
            }

        })
    }

    public fun getUser() {
        val call: Call<String> =
            ServiceFactory.userService.getUser()

        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val responseBody = Gson().fromJson(response.body(), ResponseBody::class.java)

                if (responseBody == null || responseBody.responseCode != 200) {
                    onUserGetCompleteListener?.let { it(null) }

                } else {
                    Log.e("=== Success ===", response.body() ?: "empty content")

                    val data = Gson().toJson(responseBody.data)
                    val user = Gson().fromJson(data, UserVO::class.java)

                    onUserGetCompleteListener?.let { it(user) }
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("=== Fail ===", t.toString())

                onUserGetCompleteListener?.let { it (null) }
            }

        })
    }

}