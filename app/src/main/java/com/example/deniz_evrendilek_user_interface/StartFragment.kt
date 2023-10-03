package com.example.deniz_evrendilek_user_interface

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController


class StartFragment : Fragment() {
    private lateinit var spinnerInputType: Spinner
    private lateinit var spinnerActivityType: Spinner
    private lateinit var buttonStart: Button

    private var selectedInputType: String? = null

    @Suppress("RedundantOverride")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_start, container, false)
        setupViews(view)
        return view
    }

    private fun setupViews(view: View) {
        spinnerInputType = view.findViewById(R.id.spinner_input_type)
        spinnerActivityType = view.findViewById(R.id.spinner_activity_type)
        buttonStart = view.findViewById(R.id.start)

        val inputTypeOptions = resources.getStringArray(R.array.InputType)
        val activityTypeOptions = resources.getStringArray(R.array.ActivityType)

        // Setup Adapters
        spinnerInputType.adapter = ArrayAdapter(
            view.context, android.R.layout.simple_spinner_dropdown_item, inputTypeOptions
        )
        spinnerActivityType.adapter = ArrayAdapter(
            view.context, android.R.layout.simple_spinner_dropdown_item, activityTypeOptions
        )

        spinnerInputType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                if (parent == null) {
                    throw IllegalAccessError("No parent found")
                }
                selectedInputType = parent.getItemAtPosition(position).toString()
                println(selectedInputType)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        buttonStart.setOnClickListener {
            when (selectedInputType) {
                inputTypeOptions[0] -> navigateToEntryCreation()
                inputTypeOptions[1] -> navigateToMap()
                inputTypeOptions[2] -> navigateToMap()
                else -> throw IllegalStateException("Unexpected input type navigation")
            }
        }
    }

    private fun navigateToMap() {
        findNavController().navigate(R.id.action_mainFragment_to_mapFragment)
    }

    private fun navigateToEntryCreation() {
        findNavController().navigate(R.id.action_mainFragment_to_entryFragment)
    }
}