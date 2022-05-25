package com.deepseat.ds.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.deepseat.ds.GlobalData
import com.deepseat.ds.R
import com.deepseat.ds.activity.CommunityDetailActivity
import com.deepseat.ds.activity.WritingActivity
import com.deepseat.ds.adapter.CommunityAdapter
import com.deepseat.ds.adapter.CommunityListAdapter
import com.deepseat.ds.api.ServiceFactory
import com.deepseat.ds.databinding.FragmentCommunityBinding
import com.deepseat.ds.model.Room
import com.deepseat.ds.model.Seat
import com.deepseat.ds.viewholder.CommunityListViewHolder.Companion.ClickedItem.*
import com.deepseat.ds.vo.CommunityListVO
import com.deepseat.ds.vo.ResponseBody
import com.deepseat.ds.vo.UserVO
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
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

    private var user: UserVO? = null
    private var roomID: Int = -1
    private var seatID: Int = -1

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

    override fun onResume() {
        super.onResume()
        initUser()
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

        binding.fabCommunity.setOnClickListener {
            if (user != null) {
                if (roomID == -1 || seatID == -1) {
                    snackbarMessage("글을 쓸 카테고리를 선택해 주세요.")
                } else {
                    val intent = Intent(requireContext(), WritingActivity::class.java)
                    intent.putExtra(WritingActivity.EXTRA_ROOM_ID, roomID)
                    intent.putExtra(WritingActivity.EXTRA_SEAT_ID, seatID)
                    startActivity(intent)
                }
            } else {
                snackbarMessage("커뮤니티를 이용하려면 로그인해야 합니다.")
            }
        }
    }

    private fun initDocumentRecyclerView() {
        docAdapter = CommunityListAdapter(requireContext())
        docAdapter.onItemClickListener = { type, doc ->
            when (type) {
                CARD -> {
                    val intent = Intent(requireContext(), CommunityDetailActivity::class.java)
                    intent.putExtra(CommunityDetailActivity.EXTRA_DOC_ID, doc.docId)
                    startActivity(intent)
                }
                LIKED -> {
                    handleLiked(doc.docId)
                }
                COMMENTS -> {
                    val intent = Intent(requireContext(), CommunityDetailActivity::class.java)
                    intent.putExtra(CommunityDetailActivity.EXTRA_DOC_ID, doc.docId)
                    startActivity(intent)
                }
                MORE -> {
//                    val menu = arrayListOf<String>("")
//                    val alert = AlertDialog.Builder(requireContext())
                }
                ROOM -> {

                }
                SEAT -> {
                    var roomID: Int? = null
                    for (r in communityAdapter.rooms) {
                        if (r.roomName == doc.roomName) {
                            roomID = r.roomID
                        }
                    }

                    var seatID: Int? = null
                    for (s in communityAdapter.seats[roomID!!]!!) {
                        if (s.seatID.toString() == doc.seatName) {
                            seatID = s.seatID
                        }
                    }
                    
                    initCommunityListData(roomID, seatID!!)
                }
            }
        }

        binding.rvCommunityList.adapter = docAdapter
        binding.rvCommunityList.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun initCommunityRecyclerView() {
        communityAdapter = CommunityAdapter(requireContext())
        communityAdapter.onCommunitySelectListener = { roomID, seatID ->
            this.roomID = roomID
            this.seatID = seatID
            initCommunityListData(roomID, seatID)
        }

        binding.layoutCommunityBottomSheet.rvCommunitySelect.adapter = communityAdapter
        binding.layoutCommunityBottomSheet.rvCommunitySelect.layoutManager =
            LinearLayoutManager(requireContext())
    }

    private fun initUser() {
        if (GlobalData.sessionId == null) return

        val call: Call<String> =
            ServiceFactory.userService.getUser()

        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val responseBody = Gson().fromJson(response.body(), ResponseBody::class.java)

                if (responseBody == null || responseBody.responseCode != 200) {
                    Log.e("=== Fail ===", response.body() ?: "empty content")

                    Snackbar.make(binding.root, "error", Snackbar.LENGTH_LONG).show()
                } else {
                    val data = Gson().toJson(responseBody.data)
                    user = Gson().fromJson(data, UserVO::class.java)
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

    private fun initCommunityListData(roomID: Int = -1, seatID: Int = -1) {
        val call: Call<String> =
            if (roomID != -1 && seatID != -1) ServiceFactory.docService.getDocumentVOs(
                roomID,
                seatID
            )
            else ServiceFactory.docService.getDocumentVOs()

        call.enqueue(object : Callback<String> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.e("=== Response ===", response.body() ?: "empty content")

                val responseBody = Gson().fromJson(response.body(), ResponseBody::class.java)
                if (responseBody != null && responseBody.responseCode == 200) {
                    val data = Gson().toJson(responseBody.data)
                    val result = Gson().fromJson(data, Array<CommunityListVO>::class.java)
                    val resultList = ArrayList<CommunityListVO>()
                    resultList.addAll(result)

                    docAdapter.data = resultList
                    docAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                snackbarMessage("서버 오류가 발생했습니다.")
            }

        })
    }

    private fun handleLiked(docID: Int) {
        val call: Call<String> = ServiceFactory.likeService.markLiked(docID)
        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.e("=== Response ===", response.body() ?: "empty content")
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