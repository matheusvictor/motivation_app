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
import kotlin.random.Random

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

        val imageAll = findViewById<ImageView>(R.id.iv_all_categories)
        val imageHappy = findViewById<ImageView>(R.id.iv_good_vibes_categories)
        val imageSun = findViewById<ImageView>(R.id.iv_bad_vibes_category)
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
        val listWithFiltersID = listOf(
            R.id.iv_all_categories,
            R.id.iv_good_vibes_categories,
            R.id.iv_bad_vibes_category
        )

        if (id == R.id.buttonNewPhrase) {
            generateNewPhrase()
        } else if (id in listWithFiltersID) {
            handleFilter(id)
        }
    }

    private fun handleFilter(filter_value: Int) {

        val imageAll = findViewById<ImageView>(R.id.iv_all_categories)
        val imageSun = findViewById<ImageView>(R.id.iv_bad_vibes_category)
        val imageHappy = findViewById<ImageView>(R.id.iv_good_vibes_categories)

        imageAll.setColorFilter(resources.getColor(R.color.white))
        imageSun.setColorFilter(resources.getColor(R.color.white))
        imageHappy.setColorFilter(resources.getColor(R.color.white))

        when (filter_value) {
            R.id.iv_all_categories -> {
                imageAll.setColorFilter(resources.getColor(R.color.colorAccent))
                mPhraseFiltered = MotivationAppConstants.PHRASES_FILTER.ALL
            }
            R.id.iv_bad_vibes_category -> {
                imageSun.setColorFilter(resources.getColor(R.color.colorAccent))
                mPhraseFiltered = MotivationAppConstants.PHRASES_FILTER.BAD_VIBES
            }
            R.id.iv_good_vibes_categories -> {
                imageHappy.setColorFilter(resources.getColor(R.color.colorAccent))
                mPhraseFiltered = MotivationAppConstants.PHRASES_FILTER.GOOD_VIBES
            }
        }
    }

    private fun generateNewPhrase() {
        val textPhrase = findViewById<TextView>(R.id.textPhrase)
        phraseFounded = when (mPhraseFiltered) {
            1 -> {
                val randCategory = Random.nextInt(
                    from = MotivationAppConstants.PHRASES_FILTER.GOOD_VIBES,
                    until = MotivationAppConstants.PHRASES_FILTER.BAD_VIBES + 1
                )
                phrasesDAO.findByCategoryId(randCategory)
            }
            else -> {
                phrasesDAO.findByCategoryId(mPhraseFiltered)
            }
        }
        textPhrase.text = phraseFounded?.text ?: "There aren't nothing here"

        val image = findViewById<ImageView>(R.id.iv_phrase_photo)
        if (phraseFounded?.urlImage.isNullOrBlank()) {
            image.visibility = View.GONE
        }
    }

}