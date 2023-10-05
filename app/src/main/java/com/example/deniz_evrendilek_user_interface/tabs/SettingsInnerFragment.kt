package com.example.deniz_evrendilek_user_interface.tabs

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.preference.CheckBoxPreference
import androidx.preference.EditTextPreference
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.deniz_evrendilek_user_interface.R

class SettingsInnerFragment : PreferenceFragmentCompat() {
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
            navigateToProfile()
        }
        privacyPreference.setOnPreferenceChangeListener { _, newValue ->
            println("privacyPreference $newValue")
            true
        }
    }

    private fun navigateToProfile(): Boolean {
        println("profilePreference click")
        val navHostFragment = requireActivity().supportFragmentManager.findFragmentById(
            R.id.nav_host_fragment
        ) as NavHostFragment
        navHostFragment.navController.navigate(R.id.profileFragment)
        return true
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