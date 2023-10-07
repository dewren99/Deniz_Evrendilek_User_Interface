package com.example.deniz_evrendilek_user_interface.ui.fragments.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.fragment.app.DialogFragment

class AlertDialogFragment(
    private val title: String,
    private val inputType: Int,
    private val placeHolder: String?,
    private val positiveButtonText: String,
    private val negativeButtonText: String,
    private val positiveButtonCallback: (DialogInterface, Int) -> Unit,
    private val negativeButtonCallback: (DialogInterface, Int) -> Unit
) : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("DEPRECATION")
        retainInstance = true
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return createAlertDialog()
    }

    private fun createAlertDialog(): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val input = EditText(requireContext())

        builder.setTitle(title)
        input.inputType = inputType
        if (inputType == InputType.TYPE_TEXT_FLAG_MULTI_LINE) {
            input.isSingleLine = false
            input.minLines = 3
        }
        if (placeHolder != null) {
            input.hint = placeHolder
        }
        builder.setView(input)
        builder.setPositiveButton(positiveButtonText) { dialog, i ->
            positiveButtonCallback(dialog, i)
            println(input.text.toString())
        }
        builder.setNegativeButton(negativeButtonText) { dialog, i ->
            negativeButtonCallback(dialog, i)
            println(input.text.toString())
        }

        return builder.create()
    }
}