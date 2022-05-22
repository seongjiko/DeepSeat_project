package com.deepseat.ds.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.deepseat.ds.R
import com.deepseat.ds.api.UserServiceImpl
import com.deepseat.ds.databinding.ActivityLoginBinding
import com.deepseat.ds.vo.ResponseBody
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        binding.btnLogin.setOnClickListener {
            val userID = binding.edtLoginId.text.toString().trim()
            val userPW = binding.edtLoginPw.text.toString().trim()

            val call: Call<ResponseBody<String>> = UserServiceImpl.service.loginUser(userID, userPW)

            Log.e("test json", Gson().toJson(ResponseBody<String>(200, "hello")))

            call.enqueue(object : Callback<ResponseBody<String>> {
                override fun onResponse(
                    call: Call<ResponseBody<String>>,
                    response: Response<ResponseBody<String>>
                ) {
                    Log.e("===== Retrofit =====", response.body()?.data ?: "no content")
                }

                override fun onFailure(call: Call<ResponseBody<String>>, t: Throwable) {
                    Log.e("===== Fail =====", t.toString())
                }

            })
        }
    }

}