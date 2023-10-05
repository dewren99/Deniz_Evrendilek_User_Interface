package com.example.deniz_evrendilek_user_interface

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore.ACTION_IMAGE_CAPTURE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.TableLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout.Tab
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


const val REQUEST_IMAGE_CAPTURE = 1
const val REQUEST_READ_STORAGE = 2
const val REQUEST_WRITE_STORAGE = 3

class ProfileFragment : Fragment() {
    // Buttons | Images
    private lateinit var profilePicImageView: ImageView
    private lateinit var selectImageButton: Button
    private lateinit var saveInfoButton: Button
    private lateinit var cancelSaveInfoButton: Button

    // Input Fields
    private lateinit var inputName: EditText
    private lateinit var inputEmail: EditText
    private lateinit var inputPhone: EditText
    private lateinit var radioGroupGender: RadioGroup
    private lateinit var inputClass: EditText
    private lateinit var inputMajor: EditText

    private var profilePicUri: Uri? = null
    private lateinit var view: View

    private fun <T : View?> findViewById(id: Int): T {
        return view.findViewById<T>(id)
    }

    @Suppress("RedundantOverride")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false)
        setupProfilePage()
        return view
    }

    private fun setupProfilePage() {
        maybeRequestPermissions()
        setupViewVariables()
        loadProfile()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (profilePicUri != null) {
            println("onSaveInstanceState profilePicUri: $profilePicUri")
            outState.putString("profilePicUri", profilePicUri.toString())
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            profilePicUri = savedInstanceState.getString("profilePicUri", null)?.toUri()
        }
        if (profilePicUri != null) {
            profilePicImageView.setImageURI(profilePicUri)
        }
        println("onRestoreInstanceState profilePicUri: $profilePicUri")
    }

    private fun loadProfile() {
        val sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val name = sharedPreferences.getString("nameInput", "")
        val email = sharedPreferences.getString("emailInput", "")
        val phone = sharedPreferences.getString("phoneInput", "")
        val gender = sharedPreferences.getInt("genderRadio", -1)
        val personClass = sharedPreferences.getString("classInput", "")
        val major = sharedPreferences.getString("majorInput", "")
        profilePicUri = sharedPreferences.getString("profilePicUri", null)?.toUri()

        println(
            "loading data:$name, $email, $phone, $gender, $personClass, $major, $profilePicUri",
        )

        inputName.setText(name)
        inputEmail.setText(email)
        inputPhone.setText(phone)
        radioGroupGender.check(gender)
        inputClass.setText(personClass)
        inputMajor.setText(major)
        if (profilePicUri != null) {
            println("profilePicUri is valid: $profilePicUri")
            profilePicImageView.setImageURI(profilePicUri)
        }
    }

    @SuppressLint("ApplySharedPref")
    private fun saveProfile() {
        val sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val sharedPrefEditor = sharedPref.edit()

        // Retrieve values from input fields using getFormValues()
        val formValues = getFormValues()
        sharedPrefEditor.putString("nameInput", formValues["nameInput"].toString())
        sharedPrefEditor.putString("emailInput", formValues["emailInput"].toString())
        sharedPrefEditor.putString("phoneInput", formValues["phoneInput"].toString())
        sharedPrefEditor.putInt(
            "genderRadio", Integer.parseInt(formValues["genderRadio"].toString())
        )
        sharedPrefEditor.putString("classInput", formValues["classInput"].toString())
        sharedPrefEditor.putString("majorInput", formValues["majorInput"].toString())
        if (profilePicUri != null) {
            sharedPrefEditor.putString(
                "profilePicUri", profilePicUri.toString()
            )
        }

        sharedPrefEditor.commit()
    }

    private fun displayToastMessage(message: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(requireContext(), message, duration).show()
    }

    private fun hasCameraPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            requireContext(), android.Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun hasReadStoragePermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            requireContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun hasWriteStoragePermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            requireContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun maybeRequestCameraPermission() {
        if (!hasCameraPermission()) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.CAMERA),
                REQUEST_IMAGE_CAPTURE
            )
        }
    }

    private fun maybeRequestReadStoragePermission() {
        if (!hasReadStoragePermission()) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                REQUEST_READ_STORAGE
            )
        }
    }

    private fun maybeRequestWriteStoragePermission() {
        if (!hasWriteStoragePermission()) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                REQUEST_WRITE_STORAGE
            )
        }
    }

    private fun maybeRequestPermissions() {
        maybeRequestCameraPermission()
        maybeRequestReadStoragePermission()
        maybeRequestWriteStoragePermission()
    }

    private fun setupViewVariables() {
        selectImageButton = findViewById(R.id.select_picture_button)
        saveInfoButton = findViewById(R.id.button_save_info)
        cancelSaveInfoButton = findViewById(R.id.button_cancel_save_info)
        profilePicImageView = findViewById(R.id.image_view_profile_picture)
        addViewListeners()

        inputName = findViewById(R.id.input_name)
        inputEmail = findViewById(R.id.input_email)
        inputPhone = findViewById(R.id.input_phone)
        radioGroupGender = findViewById(R.id.radio_group_gender)
        inputClass = findViewById(R.id.input_class)
        inputMajor = findViewById(R.id.input_major)
    }

    private fun exitProfile() {
        findNavController().navigate(R.id.action_profileFragment_to_mainFragment)
    }

    private fun getFormValues(): Map<String, Any> {
        println(
            "${inputName.text}, " + "${inputEmail.text}, " + "${inputPhone.text}, " + "${radioGroupGender.checkedRadioButtonId}, " + "${inputClass.text}, " + "${inputMajor.text}"
        )

        return mapOf(
            "nameInput" to inputName.text,
            "emailInput" to inputEmail.text,
            "phoneInput" to inputPhone.text,
            "genderRadio" to radioGroupGender.checkedRadioButtonId,
            "classInput" to inputClass.text,
            "majorInput" to inputMajor.text
        )
    }

    private fun addViewListeners() {
        selectImageButton.setOnClickListener {
            handleOnSelectImage()

        }
        saveInfoButton.setOnClickListener {
            handleOnSave()
        }
        cancelSaveInfoButton.setOnClickListener {
            handleOnCancelSave()
        }
    }

    private fun handleOnSave() {
        println("Save Info")
        saveProfile()
        displayToastMessage("Saved!")
        exitProfile()
    }

    private fun handleOnCancelSave() {
        println("Cancel Save Info")
        exitProfile()
    }

    private fun handleOnSelectImage() {
        if (!hasCameraPermission()) {
            exitProfile()
            return
        }
        println("Select Image")
        val takePicIntent = Intent(ACTION_IMAGE_CAPTURE)
        @Suppress("DEPRECATION") startActivityForResult(takePicIntent, REQUEST_IMAGE_CAPTURE)
    }

    /**
     * @source https://developer.android.com/training/camera-deprecated/photobasics
     */
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        @Suppress("DEPRECATION") super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK) {
            println("onActivityResult resultCode != RESULT_OK")
            return
        }

        when (requestCode) {
            REQUEST_IMAGE_CAPTURE -> {
                @Suppress("DEPRECATION") val imageBitmap = data?.extras?.get("data") as Bitmap
                profilePicImageView.setImageBitmap(imageBitmap)
                profilePicUri = saveImageLocally(imageBitmap)
            }
        }
    }


    private fun saveImageLocally(bm: Bitmap): Uri? {
        val storageDir = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        if (storageDir == null) {
            displayToastMessage(
                "Cannot save profile image locally, " + "photo directory doesn't exist"
            )
            return null
        }
        val imagePrefix = System.currentTimeMillis()
        val imageFile =
            File(storageDir, "${requireActivity().packageName}_profile_${imagePrefix}_.jpg")

        try {
            val out = FileOutputStream(imageFile)
            bm.compress(Bitmap.CompressFormat.JPEG, 50, out)
            out.close()
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
        return Uri.fromFile(imageFile)
    }
}