package com.example.deniz_evrendilek_user_interface.managers

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

class PermissionsManager(private val activity: Activity) {
    companion object {
        const val PERMISSION_IMAGE_CAPTURE = 1001
        const val PERMISSION_READ_STORAGE = 1002
        const val PERMISSION_WRITE_STORAGE = 1003
        const val PERMISSION_PICK = 1004
    }

    fun hasPermission(permission: String): Boolean {
        return ActivityCompat.checkSelfPermission(
            activity, permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun requestPermission(permission: String, requestCode: Int) {
        ActivityCompat.requestPermissions(activity, arrayOf(permission), requestCode)
    }

    fun requestPermissions(vararg permissions: String, requestCode: Int) {
        ActivityCompat.requestPermissions(activity, permissions, requestCode)
    }

    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
        onPermissionGranted: () -> Unit,
        onPermissionDenied: () -> Unit
    ) {
        if (requestCode == PERMISSION_IMAGE_CAPTURE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onPermissionGranted()
            } else {
                onPermissionDenied()
            }
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
