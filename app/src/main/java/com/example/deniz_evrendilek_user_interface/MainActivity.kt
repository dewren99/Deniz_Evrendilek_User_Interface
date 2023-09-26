package com.example.deniz_evrendilek_user_interface

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager2 = findViewById(R.id.view_pager_2)
        tabLayout = findViewById(R.id.tab_layout)

        setupViewPager2()
    }

    private fun setupViewPager2() {
        viewPager2.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = 3

            override fun createFragment(position: Int): Fragment {
                return when (position) {
                    0 -> StartFragment()
                    1 -> HistoryFragment()
                    2 -> SettingsFragment()
                    else -> throw IllegalStateException("Unexpected tab position: $position")
                }
            }
        }

        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.text = when (position) {
                0 -> "Start"
                1 -> "History"
                2 -> "Settings"
                else -> null
            }
        }.attach()
    }
}