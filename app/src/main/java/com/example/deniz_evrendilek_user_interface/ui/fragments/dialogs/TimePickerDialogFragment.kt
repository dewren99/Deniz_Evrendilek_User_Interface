package com.example.deniz_evrendilek_user_interface.ui.fragments.dialogs

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment

interface TimeListener {
    fun onTimeSelected(hourOfDay: Int, minute: Int)
}

class TimePickerDialogFragment(private val _hour: Int, private val _minute: Int) : DialogFragment(),
        TimePickerDialog.OnTimeSetListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("DEPRECATION")
        retainInstance = true
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return TimePickerDialog(
            requireContext(), this, _hour, _minute, DateFormat.is24HourFormat(requireContext())
        )
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        @Suppress("DEPRECATION") (targetFragment as? TimeListener)?.onTimeSelected(
            hourOfDay, minute
        )
    }
}