package com.example.motivationapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

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
        TODO()
    }
}