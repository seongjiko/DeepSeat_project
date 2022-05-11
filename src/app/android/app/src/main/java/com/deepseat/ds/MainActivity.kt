package com.deepseat.ds

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.deepseat.ds.databinding.ActivityMainBinding
import com.deepseat.ds.fragment.CommunityFragment
import com.deepseat.ds.fragment.MoreFragment
import com.deepseat.ds.fragment.SeatsFragment
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {

    private val seatsFragment = SeatsFragment.newInstance()
    private val communityFragment = CommunityFragment.newInstance()
    private val moreFragment = MoreFragment.newInstance()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initial Fragment
        replaceFragment(seatsFragment)

        // Bottom Nav View
        binding.bnvMain.setOnItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        replaceFragment(
            when (item.itemId) {
                R.id.nav_seats -> seatsFragment
                R.id.nav_community -> communityFragment
                R.id.nav_more -> moreFragment
                else -> seatsFragment
            }
        )

        return true
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = this.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fl_main, fragment)
        transaction.commit()
    }

}