package com.deepseat.ds.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.deepseat.ds.R
import com.deepseat.ds.adapter.CommentAdapter
import com.deepseat.ds.api.ServiceFactory
import com.deepseat.ds.databinding.ActivityCommunityDetailBinding
import com.deepseat.ds.vo.CommentVO
import com.deepseat.ds.vo.CommunityListVO
import com.deepseat.ds.vo.DocumentVO
import com.deepseat.ds.vo.ResponseBody
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommunityDetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_DOC_ID = "docID"
    }

    private lateinit var binding: ActivityCommunityDetailBinding
    private lateinit var adapter: CommentAdapter
    private lateinit var document: DocumentVO
    private lateinit var comments: CommentVO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommunityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Toolbar Setting
        setSupportActionBar(binding.toolbarCmDetail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initView()

        // Initialize Views
        initRecyclerView()

        // Initialize Data
        initData()
    }

    private fun initView() {
        binding.cardCmDetailCommentWriteBtn.setOnClickListener {
            if (binding.edtxtCmDetailComment.text.toString().isEmpty()) {
                snackbarMessage("내용을 입력하세요")
                return@setOnClickListener
            }

            val call: Call<String> = ServiceFactory.commentService.writeComment(
                intent.getIntExtra(EXTRA_DOC_ID, 1),
                binding.edtxtCmDetailComment.text.toString()
            )

            call.enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    Log.e("=== Response ===", response.body() ?: "empty content")

                    val responseBody = Gson().fromJson(response.body(), ResponseBody::class.java)

                    if (responseBody == null || responseBody.responseCode != 200) {
                        snackbarMessage("서버 오류가 발생했습니다.")
                        return
                    }

                    initData()
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    snackbarMessage("서버 오류가 발생했습니다.")
                }

            })
        }

        binding.srlCmDetail.setOnRefreshListener {
            initData()
        }
    }

    private fun initRecyclerView() {
        adapter = CommentAdapter(this)
        adapter.onItemClickListener = { position ->

        }

        binding.rvCmDetailComments.adapter = adapter

        val layoutManager = LinearLayoutManager(this)
        val divider = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvCmDetailComments.layoutManager = layoutManager
        binding.rvCmDetailComments.addItemDecoration(divider)
    }

    private fun initData() {
        binding.srlCmDetail.isRefreshing = true

        val call: Call<String> = ServiceFactory.docService.getDocumentVO(
            docID = intent.getIntExtra(
                EXTRA_DOC_ID, 1
            )
        )

        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.e("=== Response ===", response.body() ?: "empty content")

                val responseBody = Gson().fromJson(response.body(), ResponseBody::class.java)

                if (responseBody == null || responseBody.responseCode != 200) {
                    snackbarMessage("서버 오류가 발생했습니다.")
                    return
                }

                val data = Gson().toJson(responseBody.data)
                val result = Gson().fromJson(data, CommunityListVO::class.java)

                binding.txtCmDetailDate.text = result.wrote
                binding.txtCmDetailWriter.text = result.nickname
                binding.txtCmDetailLiked.text = result.liked.toString()
                binding.txtCmDetailComments.text = result.comments.toString()
                binding.txtCmDetailContent.text = result.content

                binding.srlCmDetail.isRefreshing = false
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                snackbarMessage("서버 오류가 발생했습니다.")
            }

        })

        val cmCall: Call<String> = ServiceFactory.commentService.getComments(
            intent.getIntExtra(
                EXTRA_DOC_ID, 1
            )
        )

        cmCall.enqueue(object : Callback<String> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.e("=== Response ===", response.body() ?: "empty content")

                val responseBody = Gson().fromJson(response.body(), ResponseBody::class.java)

                if (responseBody == null || responseBody.responseCode != 200) {
                    snackbarMessage("서버 오류가 발생했습니다.")
                    return
                }

                val data = Gson().toJson(responseBody.data)
                val result = Gson().fromJson(data, Array<CommentVO>::class.java)

                adapter.data.clear()
                adapter.data.addAll(result)
                adapter.notifyDataSetChanged()

                binding.srlCmDetail.isRefreshing = false
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                snackbarMessage("서버 오류가 발생했습니다.")
            }

        })
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_comment_like -> {
                // TODO
                Toast.makeText(this, "Like", Toast.LENGTH_LONG).show()
            }

            R.id.action_comment_edit -> {
                // TODO
            }

            R.id.action_comment_delete -> {
                // TODO
            }
        }
        return super.onContextItemSelected(item)
    }

    private fun snackbarMessage(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.confirm) { }
            .show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            this.finish()
        }
        return super.onOptionsItemSelected(item)
    }
}