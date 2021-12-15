package com.example.motivationapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText

class SplashActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

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
        val inputedName = findViewById<EditText>(R.id.editTextName)

        // Verify input
        if (inputedName.text.isBlank()) {
            inputedName.error = "This field can't be empty"
        } else {
            // Register a intent to open the MainActivity
            val intentMainActivity = Intent(this, MainActivity::class.java)
            // Initialize MainActivity
            startActivity(intentMainActivity)
        }
    }
}