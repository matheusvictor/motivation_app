package com.example.motivationapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.motivationapp.R
import com.example.motivationapp.databinding.ActivityMainBinding
import com.example.motivationapp.extensions.tryLoadImage
import com.example.motivationapp.infra.MotivationAppConstants
import com.example.motivationapp.infra.SecurityPreferences
import com.example.motivationapp.model.Phrase
import com.example.motivationapp.repository.AppDatabase
import kotlin.random.Random

class MainActivity : AppCompatActivity(R.layout.activity_main), View.OnClickListener {

    // Initialize mSecurityPreferences
    private lateinit var mSecurityPreferences: SecurityPreferences
    private var mPhraseFiltered: Int = MotivationAppConstants.PHRASES_FILTER.ALL

    private val bindingMainActivity by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val phrasesDAO by lazy {
        AppDatabase.getInstance(this).phrasesDao()
    }

    private var phraseFounded: Phrase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bindingMainActivity.root)

        // Instance mSecurityPreferences
        mSecurityPreferences = SecurityPreferences(this)

        bindingMainActivity.allCategoriesFilter.setColorFilter(R.color.colorAccent)
        bindingMainActivity.allCategoriesFilter.setColorFilter(resources.getColor(R.color.colorAccent))

        bindingMainActivity.allCategoriesFilter.setOnClickListener(this)
        bindingMainActivity.goodVibesCategoryFilter.setOnClickListener(this)
        bindingMainActivity.badVibesCategoryFilter.setOnClickListener(this)
        bindingMainActivity.buttonSeeNewPhrase.setOnClickListener(this)
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
        val listWithFiltersID = listOf(
            R.id.all_categories_filter,
            R.id.good_vibes_category_filter,
            R.id.bad_vibes_category_filter
        )

        if (view.id == R.id.button_see_new_phrase) {
            generateNewPhrase()
        } else if (view.id in listWithFiltersID) {
            handleFilter(view.id)
        }
    }

    private fun handleFilter(filter_value: Int) {

        bindingMainActivity.allCategoriesFilter.setColorFilter(resources.getColor(R.color.white))
        bindingMainActivity.badVibesCategoryFilter.setColorFilter(resources.getColor(R.color.white))
        bindingMainActivity.goodVibesCategoryFilter.setColorFilter(resources.getColor(R.color.white))

        when (filter_value) {
            R.id.all_categories_filter -> {
                bindingMainActivity.allCategoriesFilter.setColorFilter(resources.getColor(R.color.colorAccent))
                mPhraseFiltered = MotivationAppConstants.PHRASES_FILTER.ALL
            }
            R.id.bad_vibes_category_filter -> {
                bindingMainActivity.badVibesCategoryFilter.setColorFilter(resources.getColor(R.color.colorAccent))
                mPhraseFiltered = MotivationAppConstants.PHRASES_FILTER.BAD_VIBES
            }
            R.id.good_vibes_category_filter -> {
                bindingMainActivity.goodVibesCategoryFilter.setColorFilter(resources.getColor(R.color.colorAccent))
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
        bindingMainActivity.phraseMessage.text = phrase?.text ?: "There's nothing here"
        bindingMainActivity.phraseAuthor.text = phrase?.author ?: "Unknown"
        bindingMainActivity.phraseImage.tryLoadImage(phrase?.urlImage)

        if (phrase?.urlImage.isNullOrBlank()) {
            bindingMainActivity.phraseImage.visibility = View.GONE
        } else {
            bindingMainActivity.phraseImage.visibility = View.VISIBLE
        }
    }
}
