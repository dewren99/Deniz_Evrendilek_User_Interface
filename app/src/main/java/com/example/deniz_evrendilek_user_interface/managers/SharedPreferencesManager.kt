package com.example.deniz_evrendilek_user_interface.managers

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesManager(private val activity: Activity) {
    private val sharedPreferences: SharedPreferences = activity.getPreferences(Context.MODE_PRIVATE)
}