package com.deepseat.ds.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.deepseat.ds.activity.CommunityDetailActivity
import com.deepseat.ds.adapter.CommunityAdapter
import com.deepseat.ds.adapter.CommunityListAdapter
import com.deepseat.ds.databinding.FragmentCommunityBinding
import com.deepseat.ds.model.Room
import com.deepseat.ds.model.Seat
import com.deepseat.ds.viewholder.CommunityListViewHolder
import com.deepseat.ds.viewholder.CommunityListViewHolder.Companion.ClickedItem.*
import com.deepseat.ds.vo.CommunityListVO
import com.deepseat.ds.vo.DocumentVO
import com.google.android.material.bottomsheet.BottomSheetBehavior

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

        docAdapter.data = arrayListOf(
            CommunityListVO(
                1,
                "soc06212@gmail.com",
                "Codog",
                "Software Village",
                "Seat #1",
                "천지는 없는 뼈 것이다. 아름답고 바이며, 낙원을 아름다우냐? 이는 기쁘며, 실현에 놀이 날카로우나 그들을 보라. 위하여 인생에 그들을 길을 날카로우나 운다. 그들은 있는 가는 소금이라 위하여, 대중을 인간이 청춘의 소담스러운 부패뿐이다. 얼마나 우리는 싶이 그들을 들어 피부가 있는 만천하의 때문이다. 예가 목숨을 그들의 이상은 관현악이며, 구하지 없으면, 가슴에 것이다. 우리는 용감하고 하는 있는 봄날의 찬미를 가는 것이다. 유소년에게서 앞이 장식하는 풍부하게 것은 사막이다. 보내는 방황하였으며, 눈이 끝에 황금시대다. 가치를 없으면, 이것을 할지니, 따뜻한 따뜻한 이것이다.,",
                "2022-05-23",
                false,
                4,
                5,
                true

            ),

            CommunityListVO(
                1,
                "soc06202@kakao.com",
                "SJKoh",
                "Software Village",
                "Seat #12",
                "이상 사람은 사랑의 같으며, 이것이야말로 무엇을 봄바람이다. 수 싶이 청춘에서만 청춘의 희망의 않는 인간은 심장은 뿐이다. 뼈 작고 위하여, 구하기 이것은 이것을 끓는다. 인간의 이상 못할 웅대한 황금시대의 부패뿐이다. 청춘의 같이 수 오직 것이 낙원을 고동을 위하여서. 뼈 있는 원질이 투명하되 거친 끓는다. 이것이야말로 따뜻한 이상을 그들의 내려온 되는 쓸쓸한 무엇을 없으면 이것이다. 내는 위하여서, 되는 가장 장식하는 아니더면, 거친 청춘에서만 봄날의 봄바람이다. 우리 생명을 물방아 위하여서. 미인을 뭇 청춘의 끝에 동력은 돋고, 뼈 사막이다.",
                "2022-05-22",
                false,
                2,
                1,
                false

            ),

            CommunityListVO(
                1,
                "soc06212@gmail.com",
                "Hello",
                "Book Cafe",
                "Seat #7",
                "그들의 생의 대고, 끝까지 충분히 그들을 청춘에서만 얼마나 것이다. 새가 아니더면, 인간은 두기 풀밭에 부패를 피고, 있을 있는 운다. 그들에게 않는 우리 긴지라 이상, 청춘 온갖 얼마나 약동하다. 그와 창공에 이성은 황금시대다. 착목한는 그들의 노년에게서 많이 있으랴? 얼마나 생명을 구하지 설산에서 싶이 이것이다. 열락의 것은 청춘의 끓는다. 청춘에서만 행복스럽고 긴지라 때에, 대고, 전인 철환하였는가? 피고, 무엇을 구하지 위하여 기쁘며, 내는 청춘의 사막이다. 트고, 품었기 얼음에 바이며, 것이다. 이것을 그들에게 있음으로써 크고 열락의 없으면 노년에게서 청춘에서만 이상의 사막이다.",
                "2022-05-19",
                false,
                4,
                3,
                false

            ),

            CommunityListVO(
                1,
                "soc06212@gmail.com",
                "World",
                "Software Village",
                "Seat #6",
                "가장 그것을 인생에 작고 되려니와, 영원히 쓸쓸하랴? 곳으로 석가는 위하여, 가치를 뿐이다. 가치를 가슴이 얼마나 낙원을 새 붙잡아 아니다. 황금시대의 바이며, 사랑의 천자만홍이 생명을 무엇을 피고, 물방아 설레는 있는가? 인생의 이상의 위하여, 산야에 튼튼하며, 보내는 같이, 피가 대중을 그리하였는가? 이는 크고 인간에 이상을 스며들어 피가 구하지 그리하였는가? 거선의 같이 황금시대의 길지 끓는다. 위하여, 눈이 그들은 구하지 있는가? 시들어 같으며, 자신과 장식하는 붙잡아 쓸쓸한 이것은 부패뿐이다. 충분히 청춘의 그들의 같이, 사라지지 얼음에 얼마나 운다.",
                "2022-05-17",
                false,
                0,
                4,
                true

            )
        )

        binding.rvCommunityList.adapter = docAdapter
        binding.rvCommunityList.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun initCommunityRecyclerView() {
        communityAdapter = CommunityAdapter(requireContext())
        communityAdapter.onCommunitySelectListener = { roomID, seatID ->
            // TODO
        }

        communityAdapter.setData(
            arrayListOf(
                Room(1, "Software Village"),
                Room(2, "Book Cafe"),
                Room(3, "Book Cafe")
            ),
            hashMapOf(
                Pair(
                    1, arrayListOf(
                        Seat(1, 1, "Seat #1"),
                        Seat(2, 1, "Seat #2"),
                        Seat(3, 1, "Seat #3"),
                        Seat(4, 1, "Seat #4"),
                        Seat(5, 1, "Seat #5")

                    )
                ),
                Pair(
                    2, arrayListOf(
                        Seat(1, 2, "Seat #1"),
                        Seat(2, 2, "Seat #2"),
                        Seat(3, 2, "Seat #3")

                    )
                ),
                Pair(
                    3, arrayListOf(
                        Seat(1, 3, "Seat #1"),
                        Seat(2, 3, "Seat #2"),
                        Seat(3, 3, "Seat #3"),
                        Seat(4, 3, "Seat #4")

                    )
                )
            )
        )

        binding.layoutCommunityBottomSheet.rvCommunitySelect.adapter = communityAdapter
        binding.layoutCommunityBottomSheet.rvCommunitySelect.layoutManager =
            LinearLayoutManager(requireContext())
    }
}