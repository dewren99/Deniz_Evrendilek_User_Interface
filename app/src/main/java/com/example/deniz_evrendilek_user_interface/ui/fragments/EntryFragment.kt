package com.example.deniz_evrendilek_user_interface.ui.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.deniz_evrendilek_user_interface.R
import com.example.deniz_evrendilek_user_interface.data.ExerciseDataState
import com.example.deniz_evrendilek_user_interface.ui.fragments.dialogs.AlertDialogFragment
import com.example.deniz_evrendilek_user_interface.ui.fragments.dialogs.DateListener
import com.example.deniz_evrendilek_user_interface.ui.fragments.dialogs.DatePickerDialogFragment
import com.example.deniz_evrendilek_user_interface.ui.fragments.dialogs.TimeListener
import com.example.deniz_evrendilek_user_interface.ui.fragments.dialogs.TimePickerDialogFragment

val ENTRY_OPTIONS = arrayOf(
    "Date", "Time", "Duration", "Distance", "Calories", "Heart Rate", "Comment"
)

private const val NEGATIVE_BUTTON_TEXT = "CANCEL"
private const val POSITIVE_BUTTON_TEXT = "OK"

class EntryFragment : Fragment(), DateListener, TimeListener {
    private lateinit var view: View
    private lateinit var listView: ListView
    private lateinit var buttonSave: Button
    private lateinit var buttonCancel: Button

    private var exerciseDataState = ExerciseDataState()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        println("onCreateView")
        view = inflater.inflate(R.layout.fragment_entry, container, false)
        setupListView()
        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        println("onSaveInstanceState")
        super.onSaveInstanceState(outState)

        exerciseDataState.saveInstanceState({ key: String, value: String? ->
            outState.putString(key, value)
        }, { key: String, value: Int ->
            outState.putInt(key, value)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        println("onViewCreated")
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            return
        }

        val restoredData =
            exerciseDataState.restoreSavedInstanceState({ key: String, defaultValue: String ->
                savedInstanceState.getString(key, defaultValue)
            }) { key: String, defaultValue: Int ->
                savedInstanceState.getInt(key, defaultValue)
            }
        println(restoredData)
        exerciseDataState = restoredData
    }

    private fun handleOnItemClickListener(selected: String) {
        when (selected) {
            ENTRY_OPTIONS[0] -> createAndShowDatePicker()
            ENTRY_OPTIONS[1] -> createAndShowTimePicker()
            ENTRY_OPTIONS[2] -> createAndShowDurationDialog()
            ENTRY_OPTIONS[3] -> createAndShowDistanceDialog()
            ENTRY_OPTIONS[4] -> createAndShowCaloriesDialog()
            ENTRY_OPTIONS[5] -> createAndShowHeartRateDialog()
            ENTRY_OPTIONS[6] -> createAndShowCommentDialog()
            else -> throw IllegalStateException("Unexpected listView selection: $selected")
        }
    }

    private fun setupListView() {
        listView = view.findViewById(R.id.list_view)
        listView.adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, ENTRY_OPTIONS)

        listView.setOnItemClickListener { parent, _, position, _ ->
            val selected = parent.getItemAtPosition(position) as String
            println(selected)

            handleOnItemClickListener(selected)
        }

        buttonSave = view.findViewById(R.id.manual_entry_save_button)
        buttonCancel = view.findViewById(R.id.manual_entry_cancel_button)
        buttonSave.setOnClickListener {
            findNavController().navigate(R.id.action_entryFragment_to_mainFragment)
        }
        buttonCancel.setOnClickListener {
            findNavController().navigate(R.id.action_entryFragment_to_mainFragment)
            Toast.makeText(requireContext(), "Entry discarded", Toast.LENGTH_SHORT).show()
        }
    }

    private fun createAndShowDatePicker() {
        val (day, month, year) = exerciseDataState
        val datePickerDialog = DatePickerDialogFragment(
            year, month, day
        )
        @Suppress("DEPRECATION") datePickerDialog.setTargetFragment(this, 0)
        datePickerDialog.show(parentFragmentManager, "datePicker")
    }

    private fun createAndShowTimePicker() {
        val timePickerDialogFragment =
            TimePickerDialogFragment(exerciseDataState.hour, exerciseDataState.minute)
        @Suppress("DEPRECATION") timePickerDialogFragment.setTargetFragment(this, 0)
        timePickerDialogFragment.show(parentFragmentManager, "timePicker")
    }

    @Suppress("SameParameterValue")
    private fun createAndShowAlertDialog(
        title: String,
        inputType: Int,
        placeHolder: String?,
        positiveButtonText: String,
        negativeButtonText: String,
        positiveButtonCallback: (DialogInterface, Int) -> Unit,
        negativeButtonCallback: (DialogInterface, Int) -> Unit
    ) {
        val alertDialogFragment = AlertDialogFragment(
            title,
            inputType,
            placeHolder,
            positiveButtonText,
            negativeButtonText,
            positiveButtonCallback,
            negativeButtonCallback
        )
        alertDialogFragment.show(parentFragmentManager, title)
    }

    private fun createAndShowDurationDialog() {
        createAndShowAlertDialog(ENTRY_OPTIONS[2],
            InputType.TYPE_CLASS_NUMBER,
            null,
            POSITIVE_BUTTON_TEXT,
            NEGATIVE_BUTTON_TEXT,
            { _, _ -> println("OK") },
            { _, _ -> println("CANCEL") })
    }

    private fun createAndShowDistanceDialog() {
        createAndShowAlertDialog(ENTRY_OPTIONS[3],
            InputType.TYPE_CLASS_NUMBER,
            null,
            POSITIVE_BUTTON_TEXT,
            NEGATIVE_BUTTON_TEXT,
            { _, _ -> println("OK") },
            { _, _ -> println("CANCEL") })
    }

    private fun createAndShowCaloriesDialog() {
        createAndShowAlertDialog(ENTRY_OPTIONS[4],
            InputType.TYPE_CLASS_NUMBER,
            null,
            POSITIVE_BUTTON_TEXT,
            NEGATIVE_BUTTON_TEXT,
            { _, _ -> println("OK") },
            { _, _ -> println("CANCEL") })
    }

    private fun createAndShowHeartRateDialog() {
        createAndShowAlertDialog(ENTRY_OPTIONS[5],
            InputType.TYPE_CLASS_NUMBER,
            null,
            POSITIVE_BUTTON_TEXT,
            NEGATIVE_BUTTON_TEXT,
            { _, _ -> println("OK") },
            { _, _ -> println("CANCEL") })
    }

    private fun createAndShowCommentDialog() {
        val hint = "How did it go? Notes here."
        createAndShowAlertDialog(ENTRY_OPTIONS[6],
            InputType.TYPE_TEXT_FLAG_MULTI_LINE,
            hint,
            POSITIVE_BUTTON_TEXT,
            NEGATIVE_BUTTON_TEXT,
            { _, _ -> println("OK") },
            { _, _ -> println("CANCEL") })
    }

    override fun onDateSelected(year: Int, month: Int, dayOfMonth: Int) {
        exerciseDataState.year = year
        exerciseDataState.month = month
        exerciseDataState.day = dayOfMonth
    }

    override fun onTimeSelected(hourOfDay: Int, minute: Int) {
        exerciseDataState.hour = hourOfDay
        exerciseDataState.minute = minute
        println("$hourOfDay:$minute")
    }

}