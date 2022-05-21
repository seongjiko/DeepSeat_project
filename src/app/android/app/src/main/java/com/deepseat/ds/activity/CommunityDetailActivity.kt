package com.deepseat.ds.activity

import android.content.Intent
import android.os.Bundle
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
import com.deepseat.ds.databinding.ActivityCommunityDetailBinding
import com.deepseat.ds.vo.CommentVO
import com.deepseat.ds.vo.DocumentVO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

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

        // Initialize Views
        initRecyclerView()

        // Initialize Data
        initData()
    }

    private fun initRecyclerView() {
        adapter = CommentAdapter(this)
        adapter.onItemClickListener = { position ->

        }

        adapter.data = arrayListOf(
            CommentVO(
                1, 1, "soc06212@gmail.com", "mgdgc",
                "Test", "1999/02/01 21:30:55", false, 5
            ),
            CommentVO(
                1, 1, "soc06202@gmail.com", "SJKoh",
                "Test2", "2022/05/23 21:30:55", false, 0
            )
        )

        binding.rvCmDetailComments.adapter = adapter

        val layoutManager = LinearLayoutManager(this)
        val divider = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvCmDetailComments.layoutManager = layoutManager
        binding.rvCmDetailComments.addItemDecoration(divider)
    }

    private fun initData() {
        binding.srlCmDetail.isRefreshing = true

        CoroutineScope(Dispatchers.IO).async {
            // TODO: Get Document
            // TODO: Get Comments

            CoroutineScope(Dispatchers.Main).launch {
                binding.srlCmDetail.isRefreshing = false
                // TODO: Set adapter data
            }
        }
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
}