package com.deepseat.ds.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.deepseat.ds.GlobalData
import com.deepseat.ds.MainActivity
import com.deepseat.ds.R
import com.deepseat.ds.activity.LoginActivity
import com.deepseat.ds.adapter.MenuAdapter
import com.deepseat.ds.api.LoginHandler
import com.deepseat.ds.api.ServiceFactory
import com.deepseat.ds.databinding.FragmentMoreBinding
import com.deepseat.ds.datasource.MenuDataSource
import com.deepseat.ds.vo.ResponseBody
import com.deepseat.ds.vo.UserVO
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoreFragment : Fragment(), View.OnClickListener {

    companion object {
        @JvmStatic
        fun newInstance() = MoreFragment()
    }

    private lateinit var binding: FragmentMoreBinding
    private lateinit var adapter: MenuAdapter

    private var user: UserVO? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMoreBinding.inflate(layoutInflater, container, false)

        (requireActivity() as MainActivity).setSupportActionBar(binding.toolbarMenu)

        initData()

        return binding.root
    }

    private fun initView() {
        initRecyclerView()

        if (user != null) {
            // Account View
            binding.txtMenuNickname.text = this.user!!.nickname
            binding.txtMenuUserId.text = this.user!!.userID
            binding.cardMenuEdit.visibility = View.VISIBLE

            // Log in/ out Button
            binding.btnMenuLogout.text = getText(R.string.menu_logout)
        } else {
            // Account View
            binding.txtMenuNickname.text = getText(R.string.menu_login_null)
            binding.txtMenuUserId.text = ""
            binding.cardMenuEdit.visibility = View.GONE

            // Log in/ out Button
            binding.btnMenuLogout.text = getText(R.string.menu_login)
        }

        binding.cardMenuEdit.setOnClickListener(this)
        binding.btnMenuLogout.setOnClickListener(this)
    }

    private fun initData() {
        if (GlobalData.userID == null || GlobalData.userPW == null) return

        val loginHandler = LoginHandler(requireContext())

        loginHandler.onUserGetCompleteListener = {
            if (it == null) {
                Snackbar.make(binding.root, "error", Snackbar.LENGTH_LONG).show()
            } else {
                user = it
            }
            initView()
        }

        loginHandler.getUser()
    }

    private fun initRecyclerView() {
        adapter = MenuAdapter(requireContext())
        adapter.onItemClickListener = this.onItemClick
        adapter.data = MenuDataSource(requireContext()).getData()

        binding.rvMenu.adapter = adapter
        binding.rvMenu.layoutManager = LinearLayoutManager(requireContext())
    }

    private val onItemClick: ((String) -> Unit) = { id ->
        when (id) {
            "licenses" -> {
                // TODO: do something
            }

            "policy" -> {
                // TODO: do something
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.card_menu_edit -> handleEditButton(v)
            R.id.btn_menu_logout -> handleLogoutButton(v)
        }
    }

    private fun handleEditButton(v: View) {
        val editText = EditText(requireContext())
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(R.string.menu_edit_title)
            .setMessage(R.string.menu_edit_message)
            .setView(editText)
            .setPositiveButton(R.string.confirm) { dialog, _ ->
                val call: Call<String> = ServiceFactory.userService.editUser(
                    editText.text.toString()
                )
                call.enqueue(object : Callback<String> {
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        Log.e("=== Success ===", response.body() ?: "empty content")

                        val responseBody =
                            Gson().fromJson(response.body(), ResponseBody::class.java)

                        if (responseBody != null && responseBody.responseCode == 200) {
                            binding.txtMenuNickname.text = editText.text.toString()
                        }
                    }

                    override fun onFailure(call: Call<String>, t: Throwable) {
                        Log.e("=== Fail ===", t.toString())
                        Snackbar.make(binding.root, "error", Snackbar.LENGTH_LONG).show()
                    }

                })

                dialog.dismiss()
            }
            .setNegativeButton(R.string.cancel) { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun handleLogoutButton(v: View) {
        if (GlobalData.userID != null && GlobalData.userPW != null) {
            val call: Call<String> = ServiceFactory.userService.logoutUser()
            call.enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    Log.e("=== Success ===", response.body() ?: "empty content")
                    Snackbar.make(binding.root, R.string.menu_logout, Snackbar.LENGTH_LONG).show()

                    GlobalData.userID = null
                    GlobalData.userPW = null

                    this@MoreFragment.user = null

                    initData()
                    initView()
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.e("=== Fail ===", t.toString())
                    Snackbar.make(binding.root, "error", Snackbar.LENGTH_LONG).show()

                    GlobalData.userID = null
                    GlobalData.userPW = null

                    this@MoreFragment.user = null

                    initData()
                    initView()
                }
            })
        } else {
            startActivity(Intent(requireContext(), LoginActivity::class.java))
        }
    }

}

