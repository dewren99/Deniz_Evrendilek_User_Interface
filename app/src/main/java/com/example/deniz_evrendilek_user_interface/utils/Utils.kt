package com.example.deniz_evrendilek_user_interface.utils

import android.content.Context
import androidx.fragment.app.Fragment

@Suppress("unused")
class Utils {
    /**
     * @source: https://weidianhuang.medium.com/android-fragment-not-attached-to-a-context-24d00fac4f3d
     */
    @Suppress("unused")
    fun checkIfFragmentAttached(fragment: Fragment, operation: Context.() -> Unit) {
        if (fragment.isAdded && fragment.context != null) {
            operation(fragment.requireContext())
        }
    }
}