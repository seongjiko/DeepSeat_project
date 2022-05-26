package com.deepseat.ds.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.deepseat.ds.api.ServiceFactory
import com.deepseat.ds.databinding.FragmentSeatsBinding
import com.deepseat.ds.model.Seat
import com.deepseat.ds.vo.Observation
import com.deepseat.ds.vo.ResponseBody
import com.deepseat.ds.vo.UserVO
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SeatsFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = SeatsFragment()
    }

    private lateinit var binding: FragmentSeatsBinding
    private var seats: Array<Seat>? = null
    private var observations: Array<Observation>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSeatsBinding.inflate(layoutInflater, container, false)

        initData()

        return binding.root
    }

    private fun initData() {
        val obsCall: Call<String> = ServiceFactory.apiService.getStatus(1)

        obsCall.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val responseBody = Gson().fromJson(response.body(), ResponseBody::class.java)

                if (responseBody == null || responseBody.responseCode != 200) {
                    Log.e("=== Fail ===", response.body() ?: "empty content")

                    Snackbar.make(binding.root, "서버에 등록된 정보가 없습니다.", Snackbar.LENGTH_LONG).show()
                } else {
                    val data = Gson().toJson(responseBody.data)
                    observations = Gson().fromJson(data, Array<Observation>::class.java)
                    Log.e("=== Success ===", response.body() ?: "empty content")

                    initView()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("=== Fail ===", t.toString())
                Snackbar.make(binding.root, "error", Snackbar.LENGTH_LONG).show()
            }

        })

        val seatCall: Call<String> = ServiceFactory.apiService.getSeats(1)

        seatCall.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val responseBody = Gson().fromJson(response.body(), ResponseBody::class.java)

                if (responseBody == null || responseBody.responseCode != 200) {
                    Log.e("=== Fail ===", response.body() ?: "empty content")

                    Snackbar.make(binding.root, "error", Snackbar.LENGTH_LONG).show()
                } else {
                    val data = Gson().toJson(responseBody.data)
                    seats = Gson().fromJson(data, Array<Seat>::class.java)
                    Log.e("=== Success ===", response.body() ?: "empty content")

                    initView()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("=== Fail ===", t.toString())
                Snackbar.make(binding.root, "error", Snackbar.LENGTH_LONG).show()
            }

        })
    }

    private fun initView() {
        if (seats != null && observations != null) {
            binding.seatViewMain.setData(seats!!, observations!!)
        }
    }
}