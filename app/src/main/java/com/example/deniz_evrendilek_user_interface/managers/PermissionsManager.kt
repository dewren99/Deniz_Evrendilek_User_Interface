package com.example.deniz_evrendilek_user_interface.managers

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment

class PermissionsManager(private val fragment: Fragment) {
    companion object {
        const val PERMISSION_IMAGE_CAPTURE = 1001
        const val PERMISSION_READ_STORAGE = 1002
        const val PERMISSION_WRITE_STORAGE = 1003
        const val PERMISSION_PICK = 1004
    }

    fun hasPermission(permission: String): Boolean {
        return ActivityCompat.checkSelfPermission(
            fragment.requireActivity(), permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun requestPermission(permission: String, requestCode: Int) {
        fragment.requestPermissions(arrayOf(permission), requestCode)
    }

    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
        onPermissionGranted: () -> Unit,
        onPermissionDenied: () -> Unit
    ) {
        println("onRequestPermissionsResult requestCode")
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            println(
                "grantResults.isNotEmpty() && grantResults[0] == PackageManager" + ".PERMISSION_GRANTED)"
            )
            onPermissionGranted()
        } else {
            onPermissionDenied()
        }
    }

    fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
        actionMap: Map<Int, () -> Unit>,
    ) {
        if (resultCode != Activity.RESULT_OK) {
            println("onActivityResult $resultCode != RESULT_OK")
            return
        }

        actionMap[requestCode]?.invoke()
    }
}
