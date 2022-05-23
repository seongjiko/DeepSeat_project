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
                    alert.setTitle("ID 중복 확인")
                    if (result) {
                        idCheck = true
                        alert.setMessage("사용 가능합니다.")
                    } else {
                        alert.setMessage("중복된 ID 입니다.")
                    }
                    alert.setPositiveButton(R.string.confirm) { d, i ->
                        d.dismiss()
                    }
                    alert.show()

                } else {
                    snackbarMessage("서버 오류가 발생했습니다.")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                snackbarMessage("서버 오류가 발생했습니다.")
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
                    alert.setTitle("닉네임 중복 확인")
                    if (result) {
                        nicknameCheck = true
                        alert.setMessage("사용 가능합니다.")
                    } else {
                        alert.setMessage("중복된 닉네임 입니다.")
                    }
                    alert.setPositiveButton(R.string.confirm) { d, i ->
                        d.dismiss()
                    }
                    alert.show()

                } else {
                    snackbarMessage("서버 오류가 발생했습니다.")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                snackbarMessage("서버 오류가 발생했습니다.")
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
            snackbarMessage("모두 입력해주십시오.")
            return
        }

        if (pw != pwCheck) {
            binding.txtRegisterPwCheckError.visibility = View.VISIBLE
            return
        }

        if (!idCheck || !nicknameCheck) {
            snackbarMessage("중복 확인을 해 주십시오.")
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
                    alert.setTitle("회원 가입")
                    alert.setMessage("회원 가입이 완료되었습니다.")
                    alert.setPositiveButton(R.string.confirm) { d, i ->
                        this@RegisterActivity.finish()
                        d.dismiss()
                    }
                    alert.show()

                } else {
                    snackbarMessage("서버 오류가 발생했습니다.")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                snackbarMessage("서버 오류가 발생했습니다.")
            }

        })
    }

    private fun snackbarMessage(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.confirm) { }
            .show()
    }
}