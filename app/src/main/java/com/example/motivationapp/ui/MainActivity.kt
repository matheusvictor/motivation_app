package com.example.motivationapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import com.example.motivationapp.R
import com.example.motivationapp.infra.MotivationAppConstants
import com.example.motivationapp.infra.SecurityPreferences

class MainActivity : AppCompatActivity() {

    // Initialize mSecurityPreferences
    private lateinit var mSecurityPreferences: SecurityPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Instance mSecurityPreferences
        mSecurityPreferences = SecurityPreferences(this)

        val textUsername = findViewById<TextView>(R.id.textUsername)

        val username = mSecurityPreferences.getStoredString(MotivationAppConstants.KEY.USER_NAME)

        textUsername.text = "Hello, ${username}"
    }
}