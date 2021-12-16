package com.example.motivationapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.motivationapp.R
import com.example.motivationapp.infra.MotivationAppConstants
import com.example.motivationapp.infra.SecurityPreferences

class MainActivity : AppCompatActivity(), View.OnClickListener {

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

        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        val buttonNewPhrase = findViewById<Button>(R.id.buttonNewPhrase).setOnClickListener(this)
        val imageAll = findViewById<ImageView>(R.id.imageAll).setOnClickListener(this)
        val imageSun = findViewById<ImageView>(R.id.imageSun).setOnClickListener(this)
        val imageHappy = findViewById<ImageView>(R.id.imageHappy).setOnClickListener(this)
    }

    override fun onClick(view: View) {
        val id = view.id
        val listWithFiltersID = listOf(R.id.imageAll, R.id.imageHappy, R.id.imageSun)

        if (id == R.id.buttonNewPhrase) {
            //TODO
        } else if (id in listWithFiltersID) {
            handleFilter(id)
        }
    }

    private fun handleFilter(filter_value: Int) {

        val imageAll = findViewById<ImageView>(R.id.imageAll)
        val imageSun = findViewById<ImageView>(R.id.imageSun)
        val imageHappy = findViewById<ImageView>(R.id.imageHappy)

        imageAll.setColorFilter(resources.getColor(R.color.white))
        imageSun.setColorFilter(resources.getColor(R.color.white))
        imageHappy.setColorFilter(resources.getColor(R.color.white))

        when (filter_value) {
            R.id.imageAll -> {
                imageAll.setColorFilter(resources.getColor(R.color.colorAccent))
            }
            R.id.imageSun -> {
                imageSun.setColorFilter(resources.getColor(R.color.colorAccent))
            }
            R.id.imageHappy -> {
                imageHappy.setColorFilter(resources.getColor(R.color.colorAccent))
            }
        }
    }
}