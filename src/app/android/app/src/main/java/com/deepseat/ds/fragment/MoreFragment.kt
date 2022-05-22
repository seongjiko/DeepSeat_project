package com.deepseat.ds.fragment

import android.os.Bundle
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
import com.deepseat.ds.adapter.MenuAdapter
import com.deepseat.ds.databinding.FragmentMoreBinding
import com.deepseat.ds.datasource.MenuDataSource

class MoreFragment : Fragment(), View.OnClickListener {

    companion object {
        @JvmStatic
        fun newInstance() = MoreFragment()
    }

    private lateinit var binding: FragmentMoreBinding
    private lateinit var adapter: MenuAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMoreBinding.inflate(layoutInflater, container, false)

        (requireActivity() as MainActivity).setSupportActionBar(binding.toolbarMenu)

        initView()

        return binding.root
    }

    private fun initView() {
        initRecyclerView()

        if (GlobalData.user != null) {
            // Account View
            binding.txtMenuNickname.text = GlobalData.user.nickname
            binding.txtMenuUserId.text = GlobalData.user.userId
            binding.cardMenuEdit.setOnClickListener(this)
        }
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
                // TODO: Editing user nickname
                GlobalData.user?.nickname = editText.text.toString()
                this.binding.txtMenuNickname.text = GlobalData.user?.nickname
                dialog.dismiss()
            }
            .setNegativeButton(R.string.cancel) { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun handleLogoutButton(v: View) {

    }
}

