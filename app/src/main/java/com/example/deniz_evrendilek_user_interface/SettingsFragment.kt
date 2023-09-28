package com.example.deniz_evrendilek_user_interface

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.preference.CheckBoxPreference
import androidx.preference.EditTextPreference
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat

class SettingsFragment : PreferenceFragmentCompat() {
    private lateinit var profilePreference: Preference
    private lateinit var privacyPreference: CheckBoxPreference
    private lateinit var unitPreference: ListPreference
    private lateinit var commentsPreference: EditTextPreference
    private lateinit var webpagePreference: Preference

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        setupAccountPreferences()
        setupAdditionalSettings()
        setupMisc()
    }

    private fun setupAccountPreferences() {
        profilePreference =
            preferenceManager.findPreference("profile")
                ?: throw NoSuchFieldError("profilePreference preference not found")
        privacyPreference =
            preferenceManager.findPreference("privacy")
                ?: throw NoSuchFieldError("privacyPreference preference not found")

        profilePreference.setOnPreferenceClickListener {
            println("profilePreference click")
            true
        }
        privacyPreference.setOnPreferenceChangeListener { _, newValue ->
            println("privacyPreference $newValue")
            true
        }
    }

    private fun setupAdditionalSettings() {
        unitPreference =
            preferenceManager.findPreference("unit_preference")
                ?: throw NoSuchFieldError("Unit preference not found")
        commentsPreference = preferenceManager.findPreference("comments") ?: throw NoSuchFieldError(
            "Comments preference not found"
        )

        unitPreference.setOnPreferenceChangeListener { _, newValue ->
            println("unitPreference: $newValue")
            true
        }
        commentsPreference.setOnPreferenceChangeListener { _, newValue ->
            println(
                "Comments: $newValue"
            )
            true
        }

    }

    private fun setupMisc() {
        webpagePreference =
            preferenceManager.findPreference("webpage")
                ?: throw NoSuchFieldError("Webpage preference not found")

        webpagePreference.setOnPreferenceClickListener {
            println("Intent Webpage")
            val intentOpenWebpage = Intent(Intent.ACTION_VIEW)
            intentOpenWebpage.data = Uri.parse("https://www.sfu.ca/computing.html")
            startActivity(intentOpenWebpage)
            true
        }
    }
}