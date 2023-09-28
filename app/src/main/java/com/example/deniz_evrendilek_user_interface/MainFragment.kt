package com.example.deniz_evrendilek_user_interface

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class MainFragment : Fragment() {
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        viewPager2 = view.findViewById(R.id.view_pager_2)
        tabLayout = view.findViewById(R.id.tab_layout)


        setupViewPager2()

        return view
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