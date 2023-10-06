package com.example.deniz_evrendilek_user_interface.data

import android.content.Context
import com.example.deniz_evrendilek_user_interface.managers.SharedPreferencesManager

class ProfileData(context: Context) {
    companion object {
        private const val prefix: String = "PROFILE_DATA_"
        val KEYS = KEY
    }

    object KEY {
        const val NAME = prefix + "NAME"
        const val EMAIL = prefix + "EMAIL"
        const val PHONE = prefix + "PHONE"
        const val GENDER = prefix + "GENDER"
        const val CLASS = prefix + "CLASS"
        const val MAJOR = prefix + "MAJOR"
        const val PROFILE_IMAGE_URI = prefix + "PROFILE_IMAGE_URI"
    }

    private var sharedPreferencesManager = SharedPreferencesManager(
        SharedPreferencesManager.PROFILE_PREFERENCES, context
    )
    private var name = sharedPreferencesManager.getValue(KEYS.NAME)
    private var email = sharedPreferencesManager.getValue(KEYS.EMAIL)
    private var phone = sharedPreferencesManager.getValue(KEYS.PHONE)
    private var gender = sharedPreferencesManager.getValue(KEYS.GENDER)
    private var personClass = sharedPreferencesManager.getValue(KEYS.CLASS)
    private var major = sharedPreferencesManager.getValue(KEYS.MAJOR)
    private var maybeProfileImageUri =
        sharedPreferencesManager.getValue(KEYS.PROFILE_IMAGE_URI)

    fun load(): Map<String, String> {
        return mapOf(
            KEYS.NAME to name,
            KEYS.EMAIL to email,
            KEYS.PHONE to phone,
            KEYS.GENDER to gender,
            KEYS.CLASS to personClass,
            KEYS.MAJOR to major,
            KEYS.PROFILE_IMAGE_URI to maybeProfileImageUri
        )
    }

    fun save(
        name: String,
        email: String,
        phone: String,
        gender: String,
        personClass: String,
        major: String,
        maybeProfileImageUri: String
    ) {
        sharedPreferencesManager.saveValue(KEYS.NAME, name)
        sharedPreferencesManager.saveValue(KEYS.EMAIL, email)
        sharedPreferencesManager.saveValue(KEYS.PHONE, phone)
        sharedPreferencesManager.saveValue(KEYS.GENDER, gender)
        sharedPreferencesManager.saveValue(KEYS.CLASS, personClass)
        sharedPreferencesManager.saveValue(KEYS.MAJOR, major)
        sharedPreferencesManager.saveValue(KEYS.PROFILE_IMAGE_URI, maybeProfileImageUri)
    }
}