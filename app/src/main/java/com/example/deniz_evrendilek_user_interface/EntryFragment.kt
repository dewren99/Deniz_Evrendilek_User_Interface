package com.example.deniz_evrendilek_user_interface

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.ListView
import android.widget.TimePicker
import androidx.fragment.app.Fragment

val ENTRY_OPTIONS = arrayOf(
    "Date", "Time", "Duration", "Distance", "Calories", "Heart Rate", "Comment"
)

class EntryFragment : Fragment(),
        DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener {
    private lateinit var view: View
    private lateinit var listView: ListView

    private var day: Int? = null
    private var month: Int? = null
    private var year: Int? = null
    private var hour: Int? = null
    private var minute: Int? = null


    @Suppress("RedundantOverride")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_entry, container, false)
        setupListView()
        return view
    }

    private fun setupListView() {
        listView = view.findViewById(R.id.list_view)
        listView.adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, ENTRY_OPTIONS)

        listView.setOnItemClickListener { parent, _, position, _ ->
            val selected = parent.getItemAtPosition(position) as String
            println("$selected")

            when (selected) {
                ENTRY_OPTIONS[0] -> createAndShowDatePicker()
                ENTRY_OPTIONS[1] -> null
                ENTRY_OPTIONS[2] -> null
                ENTRY_OPTIONS[3] -> null
                ENTRY_OPTIONS[4] -> null
                ENTRY_OPTIONS[5] -> null
                ENTRY_OPTIONS[7] -> null
                else -> throw IllegalStateException("Unexpected listView selection: $selected")
            }
        }
    }

    private fun createAndShowDatePicker() {
        val calendar: Calendar = Calendar.getInstance()
        if (day == null || month == null || year == null) {
            day = calendar.get(Calendar.DAY_OF_MONTH)
            month = calendar.get(Calendar.MONTH)
            year = calendar.get(Calendar.YEAR)
        }
        val datePickerDialog = DatePickerDialog(requireActivity(), this, year!!, month!!, day!!)
        datePickerDialog.show()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        this.year = year
        this.month = month
        this.day = dayOfMonth

        val calendar: Calendar = Calendar.getInstance()
        this.hour = calendar.get(Calendar.HOUR)
        this.minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            requireActivity(), this, hour!!, minute!!, DateFormat.is24HourFormat(requireContext())
        )
        timePickerDialog.show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        this.hour = hourOfDay
        this.minute = minute
        println("$hourOfDay:$minute $day/$month/$year")
    }
}