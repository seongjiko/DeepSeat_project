package com.deepseat.ds.activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.deepseat.ds.GlobalData
import com.deepseat.ds.R
import com.deepseat.ds.api.LoginHandler
import com.deepseat.ds.api.ServiceFactory
import com.deepseat.ds.databinding.ActivityLoginBinding
import com.deepseat.ds.vo.ResponseBody
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var pref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pref = getSharedPreferences("user", 0)

        initView()
    }

    private fun initView() {
        binding.btnLogin.setOnClickListener {
            val userID = binding.edtLoginId.text.toString().trim()
            val userPW = binding.edtLoginPw.text.toString().trim()

            login(userID, userPW)
        }

        binding.btnLoginRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun login(userID: String, userPW: String) {
        val loginHandler = LoginHandler(this)

        loginHandler.onLoginCompleteListener = {
            when (it) {
                200 -> {
                    val alert = AlertDialog.Builder(this@LoginActivity)
                    alert.setTitle("계정 저장")
                        .setMessage("다음에 로그인하실 때 이 ID와 비밀번호를 사용할 수 있도록 저장하시겠어요?")
                        .setPositiveButton("저장") { dialog, _ ->
                            dialog.dismiss()

                            val edit = pref.edit()
                            edit.putString("userID", binding.edtLoginId.text.toString().trim())
                            edit.putString("userPW", binding.edtLoginPw.text.toString().trim())
                            edit.apply()

                            GlobalData.userID = binding.edtLoginId.text.toString().trim()
                            GlobalData.userPW = binding.edtLoginPw.text.toString().trim()

                            finish()
                        }
                        .setNegativeButton("저장 안함") { dialog, _ ->
                            dialog.dismiss()

                            GlobalData.userID = binding.edtLoginId.text.toString().trim()
                            GlobalData.userPW = binding.edtLoginPw.text.toString().trim()

                            finish()
                        }
                        .setCancelable(false)
                        .show()
                }
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
        }

        loginHandler.login(userID, userPW)
    }
}