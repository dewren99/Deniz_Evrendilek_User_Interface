package com.example.deniz_evrendilek_user_interface

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val preferenceWebPage = preferenceManager.findPreference<Preference>("webpage")
            ?: throw NoSuchFieldError("Webpage preference not found")

        preferenceWebPage.setOnPreferenceClickListener {
            println("Intent Webpage")
            val intentOpenWebpage = Intent(Intent.ACTION_VIEW)
            intentOpenWebpage.data = Uri.parse("https://www.sfu.ca/computing.html")
            startActivity(intentOpenWebpage)
            true
        }
    }
}