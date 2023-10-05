package com.example.deniz_evrendilek_user_interface.managers

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

class PermissionsManager(private val activity: Activity) {
    companion object {
        const val PERMISSION_REQUEST_CODE = 1001
    }

    fun hasPermission(permission: String): Boolean {
        return ActivityCompat.checkSelfPermission(
            activity,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun requestPermissions(vararg permissions: String) {
        ActivityCompat.requestPermissions(activity, permissions, PERMISSION_REQUEST_CODE)
    }

    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
        onPermissionGranted: () -> Unit,
        onPermissionDenied: () -> Unit
    ) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onPermissionGranted()
            } else {
                onPermissionDenied()
            }
        }
    }
}
