package com.example.deniz_evrendilek_user_interface.ui.fragments

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.MediaStore.ACTION_IMAGE_CAPTURE
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.deniz_evrendilek_user_interface.R
import com.example.deniz_evrendilek_user_interface.data.ProfileData
import com.example.deniz_evrendilek_user_interface.managers.PermissionsManager
import com.example.deniz_evrendilek_user_interface.managers.ToastManager
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


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

    private lateinit var profileData: ProfileData
    private lateinit var permissionsManager: PermissionsManager
    private lateinit var toastManager: ToastManager

    private fun setProfilePic(uri: Uri) {
        profilePicUri = uri
        profilePicImageView.setImageURI(profilePicUri)
    }

    private fun getProfilePic(): Uri? {
        return profilePicUri
    }

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
        profileData = ProfileData(requireContext())
        permissionsManager = PermissionsManager(this)
        toastManager = ToastManager(requireContext())
        setupProfilePage()
        @Suppress("DEPRECATION")
        setHasOptionsMenu(true)
        return view
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        @Suppress("DEPRECATION")
        super.onCreateOptionsMenu(menu, inflater)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_navigate_to_settings -> {
                exitProfile()
                return true
            }
        }
        @Suppress("DEPRECATION")
        return super.onOptionsItemSelected(item)
    }


    private fun setupProfilePage() {
//        maybeRequestPermissions()
        setupViewVariables()
        loadProfile()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (getProfilePic() != null) {
            println("onSaveInstanceState profilePicUri: $profilePicUri")
            outState.putString("profilePicUri", profilePicUri.toString())
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        var maybeUri: Uri? = null
        if (savedInstanceState != null) {
            maybeUri = savedInstanceState.getString("profilePicUri", null)?.toUri()
        }
        if (maybeUri != null) {
            setProfilePic(maybeUri)
        }
        println("onRestoreInstanceState profilePicUri: $profilePicUri")
    }

    private fun loadProfile() {
        val data = profileData.load()

        println(data[ProfileData.KEYS.GENDER])
        println(data[ProfileData.KEYS.GENDER]?.toIntOrNull() ?: -1)

        inputName.setText(data[ProfileData.KEYS.NAME])
        inputEmail.setText(data[ProfileData.KEYS.EMAIL])
        inputPhone.setText(data[ProfileData.KEYS.PHONE])
        radioGroupGender.check(data[ProfileData.KEYS.GENDER]?.toIntOrNull() ?: -1)
        inputClass.setText(data[ProfileData.KEYS.EMAIL])
        inputMajor.setText(data[ProfileData.KEYS.EMAIL])

        val maybeUri = data[ProfileData.KEYS.PROFILE_IMAGE_URI]?.toUri()
        val emptyUri = "".toUri()
        if (maybeUri != null && maybeUri != emptyUri) {
            setProfilePic(maybeUri)
            println("profilePicUri is valid: $profilePicUri")
        }
    }

    private fun saveProfile() {
        // Retrieve values from input fields using getFormValues()
        val formValues = getFormValues()
        val currUri = getProfilePic() ?: ""

        println(formValues["genderRadio"].toString())

        profileData.save(
            formValues["nameInput"].toString(),
            formValues["emailInput"].toString(),
            formValues["phoneInput"].toString(),
            formValues["genderRadio"].toString(),
            formValues["classInput"].toString(),
            formValues["majorInput"].toString(),
            currUri.toString()
        )
    }

    private fun hasCameraPermission(): Boolean {
        return permissionsManager.hasPermission(android.Manifest.permission.CAMERA)
    }

    private fun hasReadStoragePermission(): Boolean {
        return permissionsManager.hasPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    private fun hasWriteStoragePermission(): Boolean {
        return permissionsManager.hasPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    private fun maybeRequestCameraPermission() {
        if (!hasCameraPermission()) {
            permissionsManager.requestPermission(
                android.Manifest.permission.CAMERA, PermissionsManager.PERMISSION_IMAGE_CAPTURE
            )
        }
    }

    @Suppress("unused")
    private fun maybeRequestReadStoragePermission() {
        if (!hasReadStoragePermission()) {
            permissionsManager.requestPermission(
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                PermissionsManager.PERMISSION_READ_STORAGE
            )
        }
    }

    @Suppress("unused")
    private fun maybeRequestWriteStoragePermission() {
        if (!hasWriteStoragePermission()) {
            permissionsManager.requestPermission(
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                PermissionsManager.PERMISSION_WRITE_STORAGE
            )
        }
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
        toastManager.showToast("Saved!")
        exitProfile()
    }

    private fun handleOnCancelSave() {
        println("Cancel Save Info")
        exitProfile()
    }

    private fun handleSelectImageWithCamera() {
        println("Select Image")
        val cameraIntent = Intent(ACTION_IMAGE_CAPTURE)
        @Suppress("DEPRECATION") startActivityForResult(
            cameraIntent, PermissionsManager.PERMISSION_IMAGE_CAPTURE
        )
    }

    private fun handleSelectImageFromGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        @Suppress("DEPRECATION") startActivityForResult(
            galleryIntent, PermissionsManager.PERMISSION_PICK
        )
    }

    private fun handleOnSelectImage() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Select Image")
        val options = arrayOf("Take Picture", "Choose from Gallery")
        builder.setItems(options) { _, which ->
            when (which) {
                0 -> {
                    if (!hasCameraPermission())
                    // Request permission here, callback inside onRequestPermissionsResult
                        maybeRequestCameraPermission()
                    else handleSelectImageWithCamera()
                }

                1 -> handleSelectImageFromGallery()
                else -> throw IllegalAccessError("Cannot find the select image type")
            }
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun saveImageLocally(bm: Bitmap): Uri? {
        val storageDir = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        if (storageDir == null) {
            toastManager.showToast(
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

    /**
     * @source https://developer.android.com/training/camera-deprecated/photobasics
     */
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        @Suppress("DEPRECATION") super.onActivityResult(requestCode, resultCode, data)

        fun onImageCapture(data: Intent?) {
            @Suppress("DEPRECATION") val imageBitmap = data?.extras?.get("data") as Bitmap
            val imageUri = saveImageLocally(imageBitmap)
            if (imageUri != null) {
                setProfilePic(imageUri)
            }
            println("Could not save the profile picture locally")
        }

        fun onPermissionPick() {
            val imageUri = data?.data
            if (imageUri != null) {
                setProfilePic(imageUri)
            }
        }
        permissionsManager.onActivityResult(
            requestCode,
            resultCode,
            data,
            mapOf(PermissionsManager.PERMISSION_IMAGE_CAPTURE to { onImageCapture(it) },
                PermissionsManager.PERMISSION_PICK to { onPermissionPick() })
        )
    }


    @Deprecated(
        "Deprecated in Java", ReplaceWith(
            "super.onRequestPermissionsResult(requestCode, permissions, grantResults)",
            "androidx.fragment.app.Fragment"
        )
    )
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        @Suppress("DEPRECATION") super.onRequestPermissionsResult(
            requestCode, permissions, grantResults
        )
        fun onPermissionGranted(requestCode: Int) {
            when (requestCode) {
                PermissionsManager.PERMISSION_IMAGE_CAPTURE -> handleSelectImageWithCamera()
            }
        }
        permissionsManager.onRequestPermissionsResult(requestCode,
            permissions,
            grantResults,
            { onPermissionGranted(it) },
            { _ -> })
    }

}