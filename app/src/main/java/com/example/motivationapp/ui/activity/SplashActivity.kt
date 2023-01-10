package com.example.motivationapp.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import com.example.motivationapp.R
import com.example.motivationapp.infra.MotivationAppConstants
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

        verifySavedUsername()

    }

    private fun verifySavedUsername() {
        val userName = mSecurityPreferences.getStoredString(MotivationAppConstants.KEY.USER_NAME)
        //mSecurityPreferences.storeString(MotivationAppConstants.KEY.TEMP_USER_NAME, "")
        if (!userName.isBlank()) {
            val intentMainActivity = Intent(this, MainActivity::class.java)
            startActivity(intentMainActivity)
            // Close this activity
            finish()
        }
    }

    override fun onClick(view: View) {
        val view_id = view.id
        if (view_id == R.id.buttonSend) {
            handleSend()
        }
    }

    private fun handleSend() {
        val inputedName = findViewById<EditText>(R.id.editTextName).text.toString()
        val checkboxRememberUsername = findViewById<CheckBox>(R.id.checkboxRememberUsername)

        // Verify input
        if (inputedName.isBlank()) {
            Toast.makeText(this, "This field can't be empty", Toast.LENGTH_SHORT).show()
        } else {
            if (checkboxRememberUsername.isChecked) {
                mSecurityPreferences.storeString(MotivationAppConstants.KEY.USER_NAME, inputedName)
                mSecurityPreferences.storeString(MotivationAppConstants.KEY.TEMP_USER_NAME, "")
            } else {
                mSecurityPreferences.storeString(
                    MotivationAppConstants.KEY.TEMP_USER_NAME,
                    inputedName
                )
            }
            // Register a intent to open the MainActivity
            val intentMainActivity = Intent(this, MainActivity::class.java)
            // Initialize MainActivity
            startActivity(intentMainActivity)
            finish()
        }
    }
}