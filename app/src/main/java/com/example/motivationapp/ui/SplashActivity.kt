package com.example.motivationapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.motivationapp.R
import com.example.motivationapp.infra.SecurityPreferences

class SplashActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mSecurityPreferences: SecurityPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        mSecurityPreferences = SecurityPreferences(this)

        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        val buttonSend = findViewById<Button>(R.id.buttonSend)
        // Listening this context
        buttonSend.setOnClickListener(this)

    }

    override fun onClick(view: View) {
        val view_id = view.id
        if (view_id == R.id.buttonSend) {
            handleSend()
        }
    }

    private fun handleSend() {
        val inputedName = findViewById<EditText>(R.id.editTextName).text.toString()

        // Verify input
        if (inputedName.isBlank()) {
            Toast.makeText(this, "This field can't be empty", Toast.LENGTH_SHORT).show()
        } else {
            mSecurityPreferences.storeString("username", inputedName)
            // Register a intent to open the MainActivity
            val intentMainActivity = Intent(this, MainActivity::class.java)
            // Initialize MainActivity
            startActivity(intentMainActivity)
        }
    }
}