package com.deepseat.ds.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import com.deepseat.ds.R
import com.deepseat.ds.adapter.RoomAdapter
import com.deepseat.ds.api.ServiceFactory
import com.deepseat.ds.databinding.FragmentSeatsBinding
import com.deepseat.ds.model.Room
import com.deepseat.ds.model.Seat
import com.deepseat.ds.vo.Observation
import com.deepseat.ds.vo.ResponseBody
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SeatsFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = SeatsFragment()
    }

    private lateinit var binding: FragmentSeatsBinding
    private lateinit var adapter: RoomAdapter
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<CardView>

    private var roomID: Int = 1
    private var seats: Array<Seat>? = null
    private var observations: Array<Observation>? = null
    private var update: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSeatsBinding.inflate(layoutInflater, container, false)

        initView()
        initData()
        initRoomData()

        initCommunityRecyclerView()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        update = true
        updateLoop()
    }

    override fun onPause() {
        super.onPause()
        update = false
    }

    private fun updateLoop() {
        CoroutineScope(Dispatchers.IO).async {
            while (update) {
                Log.e("SeatsFragment", "update")
                seats = null
                observations = null
                initData()
                delay(10000)
            }
        }
    }

    private fun initView() {
        bottomSheetBehavior =
            BottomSheetBehavior.from(binding.layoutSeatsBottomSheet.root)

        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

        binding.btnSeatsSelect.setOnClickListener {
            if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_HIDDEN) {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            } else {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            }
        }
    }

    private fun initData() {
        updateData()

        val seatCall: Call<String> = ServiceFactory.apiService.getSeats(roomID)

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

                    updateView()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("=== Fail ===", t.toString())
                Snackbar.make(binding.root, "error", Snackbar.LENGTH_LONG).show()
            }

        })
    }

    private fun updateData() {
        val obsCall: Call<String> = ServiceFactory.apiService.getStatus(roomID)

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

                    updateView()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("=== Fail ===", t.toString())
                Snackbar.make(binding.root, "error", Snackbar.LENGTH_LONG).show()
            }

        })
    }

    private fun initRoomData() {
        val call: Call<String> = ServiceFactory.apiService.getRooms()

        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.e("=== Response ===", response.body() ?: "empty content")

                val responseBody = Gson().fromJson(response.body(), ResponseBody::class.java)
                if (responseBody != null && responseBody.responseCode == 200) {
                    val data = Gson().toJson(responseBody.data)
                    val result = Gson().fromJson(data, Array<Room>::class.java)
                    val rooms = arrayListOf<Room>()
                    rooms.addAll(result)

                    adapter.data = rooms

                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                snackbarMessage("서버 오류가 발생했습니다.")
            }

        })
    }

    private fun updateView() {
        if (seats != null && observations != null) {
            binding.seatViewMain.drawSeats(seats!!, observations!!)
        }
    }

    private fun initCommunityRecyclerView() {
        adapter = RoomAdapter(requireContext())
        adapter.onItemClickListener = { room, position ->
            roomID = room.roomID
            initData()

            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }

        binding.layoutSeatsBottomSheet.rvSeatsSelect.adapter = adapter
        binding.layoutSeatsBottomSheet.rvSeatsSelect.layoutManager =
            LinearLayoutManager(requireContext())
    }

    private fun snackbarMessage(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.confirm) { }
            .show()
    }
}