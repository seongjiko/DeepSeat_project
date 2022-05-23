package com.deepseat.ds.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.deepseat.ds.R
import com.deepseat.ds.api.ServiceFactory
import com.deepseat.ds.databinding.ActivityWritingBinding
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WritingActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ROOM_ID = "room_id"
        const val EXTRA_SEAT_ID = "seat_id"
    }

    private lateinit var binding: ActivityWritingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWritingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarWriting)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_writing, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> this.finish()
            R.id.action_write -> handleWrite()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun handleWrite() {
        val call: Call<String> = ServiceFactory.docService.writeDocument(
            intent.getIntExtra(EXTRA_ROOM_ID, 1),
            intent.getIntExtra(EXTRA_SEAT_ID, 1),
            binding.edtWriting.text.toString()
        )

        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                this@WritingActivity.finish()
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Snackbar.make(binding.root, "서버 오류가 발생했습니다.", Snackbar.LENGTH_LONG).show()
            }

        })
    }
}