package com.deepseat.ds

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewTreeObserver
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import com.deepseat.ds.databinding.ActivityMainBinding
import com.deepseat.ds.fragment.CommunityFragment
import com.deepseat.ds.fragment.MoreFragment
import com.deepseat.ds.fragment.SeatsFragment
import com.google.android.material.navigation.NavigationBarView
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {

    private val seatsFragment = SeatsFragment.newInstance()
    private val communityFragment = CommunityFragment.newInstance()
    private val moreFragment = MoreFragment.newInstance()

    private lateinit var binding: ActivityMainBinding

    private var logInReady: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        // Android Splash Screen (Since 12)
        installSplashScreen()
        initSplashScreen()

        // View Initializations
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initial Fragment
        replaceFragment(seatsFragment)

        // Bottom Nav View
        binding.bnvMain.setOnItemSelectedListener(this)

        CoroutineScope(Dispatchers.Main).launch {
            delay(timeMillis = 1000)
            logInReady = true
        }
    }

    private fun initSplashScreen() {
        // Set up an OnPreDrawListener to the root view.
        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    // Check if the initial data is ready.
                    return if (logInReady) {
                        // The content is ready; start drawing.
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else {
                        // The content is not ready; suspend.
                        false
                    }
                }
            }
        )
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