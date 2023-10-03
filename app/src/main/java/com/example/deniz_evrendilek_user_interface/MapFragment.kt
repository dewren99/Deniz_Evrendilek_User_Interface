package com.example.deniz_evrendilek_user_interface

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


const val MAP_HEADER = "Map"

class MapFragment : Fragment() {
    private lateinit var view: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_map, container, false)
        setToolbarHeader()
        return view
    }

    private fun setToolbarHeader() {
        requireActivity().findViewById<TextView>(R.id.toolbar_header).text = MAP_HEADER
    }
}