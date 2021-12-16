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
import com.example.motivationapp.repository.Mock

class MainActivity : AppCompatActivity(), View.OnClickListener {

    // Initialize mSecurityPreferences
    private lateinit var mSecurityPreferences: SecurityPreferences
    private var mPhraseFiltered: Int = MotivationAppConstants.PHRASES_FILTER.ALL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        // Instance mSecurityPreferences
        mSecurityPreferences = SecurityPreferences(this)

        val textUsername = findViewById<TextView>(R.id.textUsername)
        val username = mSecurityPreferences.getStoredString(MotivationAppConstants.KEY.USER_NAME)

        textUsername.text = "Hello, ${username}"

        val imageAll = findViewById<ImageView>(R.id.imageAll)
        val imageHappy = findViewById<ImageView>(R.id.imageHappy)
        val imageSun = findViewById<ImageView>(R.id.imageSun)
        val buttonNewPhrase = findViewById<Button>(R.id.buttonNewPhrase)

        // Preset color too imagemAll
        imageAll.setColorFilter(resources.getColor(R.color.colorAccent))

        imageAll.setOnClickListener(this)
        imageHappy.setOnClickListener(this)
        imageSun.setOnClickListener(this)
        buttonNewPhrase.setOnClickListener(this)

        generateNewPhrase()
    }

    override fun onClick(view: View) {
        val id = view.id
        val listWithFiltersID = listOf(R.id.imageAll, R.id.imageHappy, R.id.imageSun)

        if (id == R.id.buttonNewPhrase) {
            generateNewPhrase()
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
                mPhraseFiltered = MotivationAppConstants.PHRASES_FILTER.ALL
            }
            R.id.imageSun -> {
                imageSun.setColorFilter(resources.getColor(R.color.colorAccent))
                mPhraseFiltered = MotivationAppConstants.PHRASES_FILTER.MORNING
            }
            R.id.imageHappy -> {
                imageHappy.setColorFilter(resources.getColor(R.color.colorAccent))
                mPhraseFiltered = MotivationAppConstants.PHRASES_FILTER.HAPPY
            }
        }
    }

    private fun generateNewPhrase() {
        var textPhrase = findViewById<TextView>(R.id.textPhrase)
        textPhrase.text = Mock().getPhrase(mPhraseFiltered)
    }

}