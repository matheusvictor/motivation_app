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
import com.example.motivationapp.extensions.tryLoadImage
import com.example.motivationapp.infra.MotivationAppConstants
import com.example.motivationapp.infra.SecurityPreferences
import com.example.motivationapp.model.Phrase
import com.example.motivationapp.repository.AppDatabase
import kotlin.random.Random

class MainActivity : AppCompatActivity(), View.OnClickListener {

    // Initialize mSecurityPreferences
    private lateinit var mSecurityPreferences: SecurityPreferences
    private var mPhraseFiltered: Int = MotivationAppConstants.PHRASES_FILTER.ALL

    private val phrasesDAO by lazy {
        AppDatabase.getInstance(this).phrasesDao()
    }

    private var phraseFounded: Phrase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Instance mSecurityPreferences
        mSecurityPreferences = SecurityPreferences(this)

        val imageAll = findViewById<ImageView>(R.id.all_categories_filter)
        val imageHappy = findViewById<ImageView>(R.id.good_vibes_category_filter)
        val imageSun = findViewById<ImageView>(R.id.bad_vibes_category_filter)
        val buttonNewPhrase = findViewById<Button>(R.id.button_see_new_phrase)

        // Preset color too imagemAll
        imageAll.setColorFilter(resources.getColor(R.color.colorAccent))

        imageAll.setOnClickListener(this)
        imageHappy.setOnClickListener(this)
        imageSun.setOnClickListener(this)
        buttonNewPhrase.setOnClickListener(this)

    }

    override fun onResume() {
        super.onResume()
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
            R.id.all_categories_filter,
            R.id.good_vibes_category_filter,
            R.id.bad_vibes_category_filter
        )

        if (id == R.id.button_see_new_phrase) {
            generateNewPhrase()
        } else if (id in listWithFiltersID) {
            handleFilter(id)
        }
    }

    private fun handleFilter(filter_value: Int) {

        val imageAll = findViewById<ImageView>(R.id.all_categories_filter)
        val imageSun = findViewById<ImageView>(R.id.bad_vibes_category_filter)
        val imageHappy = findViewById<ImageView>(R.id.good_vibes_category_filter)

        imageAll.setColorFilter(resources.getColor(R.color.white))
        imageSun.setColorFilter(resources.getColor(R.color.white))
        imageHappy.setColorFilter(resources.getColor(R.color.white))

        when (filter_value) {
            R.id.all_categories_filter -> {
                imageAll.setColorFilter(resources.getColor(R.color.colorAccent))
                mPhraseFiltered = MotivationAppConstants.PHRASES_FILTER.ALL
            }
            R.id.bad_vibes_category_filter -> {
                imageSun.setColorFilter(resources.getColor(R.color.colorAccent))
                mPhraseFiltered = MotivationAppConstants.PHRASES_FILTER.BAD_VIBES
            }
            R.id.good_vibes_category_filter -> {
                imageHappy.setColorFilter(resources.getColor(R.color.colorAccent))
                mPhraseFiltered = MotivationAppConstants.PHRASES_FILTER.GOOD_VIBES
            }
        }
    }

    private fun generateNewPhrase() {

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

        fillPhraseFields(phraseFounded)
    }

    private fun fillPhraseFields(phrase: Phrase?) {
        val message = findViewById<TextView>(R.id.phrase_message)
        val author = findViewById<TextView>(R.id.phrase_author)
        val image = findViewById<ImageView>(R.id.phrase_image)

        message.text = phrase?.text ?: "There's nothing here"
        author.text = phrase?.author ?: "Unknown"
        image.tryLoadImage(phrase?.urlImage)

        if (phrase?.urlImage.isNullOrBlank()) {
            image.visibility = View.GONE
        } else {
            image.visibility = View.VISIBLE
        }
    }

}