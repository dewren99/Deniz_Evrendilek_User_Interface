package com.example.deniz_evrendilek_user_interface.tabs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.deniz_evrendilek_user_interface.R

class SettingsOuterFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        requireActivity().supportFragmentManager.beginTransaction().replace(
            R.id.fragment_settings_placeholder, SettingsInnerFragment()
        ).commit()
        return inflater.inflate(R.layout.fragment_outer_settings, container, false)
    }

}