package com.example.motivationapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.motivationapp.R
import com.example.motivationapp.databinding.ActivityMainBinding
import com.example.motivationapp.infra.MotivationAppConstants
import com.example.motivationapp.infra.SecurityPreferences
import com.example.motivationapp.model.Phrase
import com.example.motivationapp.repository.AppDatabase

class MainActivity : AppCompatActivity(), View.OnClickListener {

    // Initialize mSecurityPreferences
    private lateinit var mSecurityPreferences: SecurityPreferences
    private var mPhraseFiltered: Int = MotivationAppConstants.PHRASES_FILTER.ALL

    private val bindingActivityMain by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val phrasesDAO by lazy {
        AppDatabase.getInstance(this).phrasesDao()
    }

    private var phraseFounded: Phrase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Instance mSecurityPreferences
        mSecurityPreferences = SecurityPreferences(this)

        val textUsername = bindingActivityMain.textUsername
        val username = mSecurityPreferences.getStoredString(MotivationAppConstants.KEY.USER_NAME)
        val tempUsername =
            mSecurityPreferences.getStoredString(MotivationAppConstants.KEY.TEMP_USER_NAME)

        if (username.isNotBlank()) {
            textUsername.text = "Hello, ${username}"
        } else {
            textUsername.text = "Hello, ${tempUsername}"
        }

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_more_options, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_add_new_phrase -> {
                val intent = Intent(this, PhraseFormActivity::class.java)
                startActivity(intent)
            }
            R.id.action_list_all_phrases -> {
                val intent = Intent(this, PhraseListActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
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
        val textPhrase = findViewById<TextView>(R.id.textPhrase)
        phraseFounded = phrasesDAO.findById(mPhraseFiltered.toLong())
        textPhrase.text = phraseFounded?.text ?: "There aren't nothing here"
    }

}