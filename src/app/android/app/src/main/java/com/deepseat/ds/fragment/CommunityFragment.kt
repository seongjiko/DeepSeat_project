package com.deepseat.ds.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.deepseat.ds.R
import com.deepseat.ds.activity.CommunityDetailActivity
import com.deepseat.ds.adapter.CommunityAdapter
import com.deepseat.ds.adapter.CommunityListAdapter
import com.deepseat.ds.api.ServiceFactory
import com.deepseat.ds.databinding.FragmentCommunityBinding
import com.deepseat.ds.model.Room
import com.deepseat.ds.model.Seat
import com.deepseat.ds.viewholder.CommunityListViewHolder
import com.deepseat.ds.viewholder.CommunityListViewHolder.Companion.ClickedItem.*
import com.deepseat.ds.vo.CommunityListVO
import com.deepseat.ds.vo.DocumentVO
import com.deepseat.ds.vo.ResponseBody
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommunityFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = CommunityFragment()
    }

    private lateinit var binding: FragmentCommunityBinding

    private lateinit var docAdapter: CommunityListAdapter
    private lateinit var communityAdapter: CommunityAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCommunityBinding.inflate(layoutInflater, container, false)

        initView()
        initDocumentRecyclerView()
        initCommunityRecyclerView()

        initCommunityData()
        initCommunityListData()

        return binding.root
    }

    private fun initView() {
        val bottomSheetBehavior =
            BottomSheetBehavior.from(binding.layoutCommunityBottomSheet.root)

        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

        binding.btnCommunitySelect.setOnClickListener {
            if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_HIDDEN) {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            } else {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            }
        }
    }

    private fun initDocumentRecyclerView() {
        docAdapter = CommunityListAdapter(requireContext())
        docAdapter.onItemClickListener = { type, docID ->
            // TODO
            when (type) {
                CARD -> {
                    startActivity(Intent(requireContext(), CommunityDetailActivity::class.java))
                }
                LIKED -> {

                }
                COMMENTS -> {

                }
                MORE -> {

                }
                ROOM -> {

                }
                SEAT -> {

                }
            }
        }

        binding.rvCommunityList.adapter = docAdapter
        binding.rvCommunityList.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun initCommunityRecyclerView() {
        communityAdapter = CommunityAdapter(requireContext())
        communityAdapter.onCommunitySelectListener = { roomID, seatID ->
            // TODO
        }

        binding.layoutCommunityBottomSheet.rvCommunitySelect.adapter = communityAdapter
        binding.layoutCommunityBottomSheet.rvCommunitySelect.layoutManager =
            LinearLayoutManager(requireContext())
    }

    private fun initCommunityData() {
        val call: Call<String> = ServiceFactory.apiService.getRooms()

        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.e("=== Response ===", response.body() ?: "empty content")

                val responseBody = Gson().fromJson(response.body(), ResponseBody::class.java)
                if (responseBody != null && responseBody.responseCode == 200) {
                    val data = Gson().toJson(responseBody.data)
                    val result = Gson().fromJson(data, Array<Room>::class.java)
                    communityAdapter.setData(result)

                    communityAdapter.clearSeats()

                    for (room in result) {
                        initCommunitySubData(room.roomID)
                    }

                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                snackbarMessage("서버 오류가 발생했습니다.")
            }

        })
    }

    private fun initCommunitySubData(roomID: Int) {
        val call: Call<String> = ServiceFactory.apiService.getSeats(roomID)

        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.e("=== Response ===", response.body() ?: "empty content")

                val responseBody = Gson().fromJson(response.body(), ResponseBody::class.java)
                if (responseBody != null && responseBody.responseCode == 200) {
                    val data = Gson().toJson(responseBody.data)
                    val result = Gson().fromJson(data, Array<Seat>::class.java)
                    val resultList = ArrayList<Seat>()
                    resultList.addAll(result)
                    communityAdapter.addData(roomID, resultList)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                snackbarMessage("서버 오류가 발생했습니다.")
            }

        })
    }

    private fun initCommunityListData() {
        val call: Call<String> = ServiceFactory.docService.getDocuments()

        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.e("=== Response ===", response.body() ?: "empty content")

                val responseBody = Gson().fromJson(response.body(), ResponseBody::class.java)
                if (responseBody != null && responseBody.responseCode == 200) {
                    val data = Gson().toJson(responseBody.data)
                    val result = Gson().fromJson(data, Array<CommunityListVO>::class.java)
                    val resultList = ArrayList<CommunityListVO>()
                    resultList.addAll(result)

                    docAdapter.data = resultList
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