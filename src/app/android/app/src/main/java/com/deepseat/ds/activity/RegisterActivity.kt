package com.deepseat.ds.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.deepseat.ds.R
import com.deepseat.ds.api.ServiceFactory
import com.deepseat.ds.databinding.ActivityRegisterBinding
import com.deepseat.ds.vo.ResponseBody
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityRegisterBinding

    private var idCheck = false
    private var nicknameCheck = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegisterIdCheck.setOnClickListener(this)
        binding.btnRegisterNicknameCheck.setOnClickListener(this)
        binding.btnRegister.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0) {
            binding.btnRegisterIdCheck -> handleIdCheck()
            binding.btnRegisterNicknameCheck -> handleNicknameCheck()
            binding.btnRegister -> handleRegister()
        }
    }

    private fun handleIdCheck() {
        val call: Call<String> =
            ServiceFactory.userService.checkIDDuplicate(binding.edtRegisterId.text.toString())

        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.e("=== Response ===", response.body() ?: "empty content")

                val responseBody = Gson().fromJson(response.body(), ResponseBody::class.java)
                if (responseBody != null && responseBody.responseCode == 200) {
                    val data = Gson().toJson(responseBody.data)
                    val result = Gson().fromJson(data, Boolean::class.java)

                    val alert = AlertDialog.Builder(this@RegisterActivity)
                    alert.setTitle("ID ?????? ??????")
                    if (result) {
                        idCheck = true
                        alert.setMessage("?????? ???????????????.")
                    } else {
                        alert.setMessage("????????? ID ?????????.")
                    }
                    alert.setPositiveButton(R.string.confirm) { d, i ->
                        d.dismiss()
                    }
                    alert.show()

                } else {
                    snackbarMessage("?????? ????????? ??????????????????.")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                snackbarMessage("?????? ????????? ??????????????????.")
            }

        })
    }

    private fun handleNicknameCheck() {
        val call: Call<String> =
            ServiceFactory.userService.checkNicknameDuplicate(binding.edtRegisterNickname.text.toString())

        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.e("=== Response ===", response.body() ?: "empty content")

                val responseBody = Gson().fromJson(response.body(), ResponseBody::class.java)
                if (responseBody != null && responseBody.responseCode == 200) {
                    val data = Gson().toJson(responseBody.data)
                    val result = Gson().fromJson(data, Boolean::class.java)

                    val alert = AlertDialog.Builder(this@RegisterActivity)
                    alert.setTitle("????????? ?????? ??????")
                    if (result) {
                        nicknameCheck = true
                        alert.setMessage("?????? ???????????????.")
                    } else {
                        alert.setMessage("????????? ????????? ?????????.")
                    }
                    alert.setPositiveButton(R.string.confirm) { d, i ->
                        d.dismiss()
                    }
                    alert.show()

                } else {
                    snackbarMessage("?????? ????????? ??????????????????.")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                snackbarMessage("?????? ????????? ??????????????????.")
            }

        })
    }

    private fun handleRegister() {
        val id = binding.edtRegisterId.text.toString().trim()
        val email = binding.edtRegisterId.text.toString().trim()
        val pw = binding.edtRegisterPw.text.toString().trim()
        val pwCheck = binding.edtRegisterPwCheck.text.toString().trim()
        val nickname = binding.edtRegisterNickname.text.toString().trim()

        if (id.isEmpty() || pw.isEmpty() || pwCheck.isEmpty() || nickname.isEmpty()) {
            snackbarMessage("?????? ?????????????????????.")
            return
        }

        if (pw != pwCheck) {
            binding.txtRegisterPwCheckError.visibility = View.VISIBLE
            return
        }

        if (!idCheck || !nicknameCheck) {
            snackbarMessage("?????? ????????? ??? ????????????.")
            return
        }

        val call: Call<String> =
            ServiceFactory.userService.registerUser(id, pw, pwCheck, nickname, email)

        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.e("=== Response ===", response.body() ?: "empty content")

                val responseBody = Gson().fromJson(response.body(), ResponseBody::class.java)
                if (responseBody != null && responseBody.responseCode == 200) {
                    val alert = AlertDialog.Builder(this@RegisterActivity)
                    alert.setTitle("?????? ??????")
                    alert.setMessage("?????? ????????? ?????????????????????.")
                    alert.setPositiveButton(R.string.confirm) { d, i ->
                        this@RegisterActivity.finish()
                        d.dismiss()
                    }
                    alert.show()

                } else {
                    snackbarMessage("?????? ????????? ??????????????????.")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                snackbarMessage("?????? ????????? ??????????????????.")
            }

        })
    }

    private fun snackbarMessage(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.confirm) { }
            .show()
    }
}