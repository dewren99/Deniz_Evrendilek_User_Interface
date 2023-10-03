package com.example.deniz_evrendilek_user_interface.user_exercise_entry

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.deniz_evrendilek_user_interface.R


const val MAP_HEADER = "Map"

class MapFragment : Fragment() {
    private lateinit var view: View
    private lateinit var buttonCancel: Button
    private lateinit var buttonSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_map, container, false)
        setToolbarHeader()
        setupButtons()
        return view
    }

    private fun setupButtons() {
        buttonCancel = view.findViewById(R.id.map_cancel_button)
        buttonSave = view.findViewById(R.id.map_save_button)

        buttonCancel.setOnClickListener {
            findNavController().navigate(R.id.action_mapFragment_to_mainFragment)
        }
        buttonSave.setOnClickListener {
            findNavController().navigate(R.id.action_mapFragment_to_mainFragment)
        }
    }

    private fun setToolbarHeader() {
        requireActivity().findViewById<TextView>(R.id.toolbar_header).text = MAP_HEADER
    }
}