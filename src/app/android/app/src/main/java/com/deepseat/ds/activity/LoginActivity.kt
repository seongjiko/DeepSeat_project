package com.deepseat.ds.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.deepseat.ds.GlobalData
import com.deepseat.ds.R
import com.deepseat.ds.api.ServiceFactory
import com.deepseat.ds.databinding.ActivityLoginBinding
import com.deepseat.ds.vo.ResponseBody
import com.google.android.material.snackbar.Snackbar
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

            val call: Call<String> = ServiceFactory.service.loginUser(userID, userPW)

            call.enqueue(object : Callback<String> {
                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    val responseBody = Gson().fromJson(response.body(), ResponseBody::class.java)

                    if (responseBody == null || responseBody.responseCode != 200 || responseBody.data as? String == null) {
                        when (responseBody.responseCode) {
                            4001 -> Snackbar.make(
                                binding.root,
                                R.string.login_error_account,
                                Snackbar.LENGTH_LONG
                            ).show()
                            4002 -> Snackbar.make(
                                binding.root,
                                R.string.login_error_account,
                                Snackbar.LENGTH_LONG
                            ).show()
                            4005 -> Snackbar.make(
                                binding.root,
                                R.string.login_error_not_exist,
                                Snackbar.LENGTH_LONG
                            ).show()
                            else -> Snackbar.make(
                                binding.root,
                                R.string.login_error,
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                    } else {
                        GlobalData.sessionId = responseBody.data as? String
                        finish()
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Snackbar.make(binding.root, R.string.login_id, Snackbar.LENGTH_LONG).show()
                }

            })
        }
    }

}