package com.example.deniz_evrendilek_user_interface

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class StartFragment : Fragment() {
    private lateinit var spinnerInputType: Spinner
    private lateinit var spinnerActivityType: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_start, container, false)
        if (view != null) {
            setupSpinners(view)
            val buttonSave: Button = view.findViewById(R.id.save)
            buttonSave.setOnClickListener {
                findNavController().navigate(R.id.action_mainFragment_to_blankFragment)
            }
        }
        return view
    }

    private fun setupSpinners(view: View) {
        spinnerInputType = view.findViewById(R.id.spinner_input_type)
        spinnerActivityType = view.findViewById(R.id.spinner_activity_type)

        val inputTypeOptions = resources.getStringArray(R.array.InputType)
        val activityTypeOptions = resources.getStringArray(R.array.ActivityType)

        // Setup Adapters
        spinnerInputType.adapter = ArrayAdapter(
            view.context, android.R.layout.simple_spinner_dropdown_item, inputTypeOptions
        )
        spinnerActivityType.adapter = ArrayAdapter(
            view.context, android.R.layout.simple_spinner_dropdown_item, activityTypeOptions
        )
    }
}