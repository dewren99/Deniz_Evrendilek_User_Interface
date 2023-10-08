package com.example.deniz_evrendilek_user_interface.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.deniz_evrendilek_user_interface.R

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.title = resources.getString(R.string.myruns2)
        setSupportActionBar(toolbar)
    }


}