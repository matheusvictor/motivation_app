package com.example.motivationapp.ui.activity.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.motivationapp.R
import com.example.motivationapp.databinding.ActivityMainBinding
import com.example.motivationapp.extensions.tryLoadImage
import com.example.motivationapp.infra.MotivationAppConstants.KEY.USER_NAME
import com.example.motivationapp.infra.SecurityPreferences
import com.example.motivationapp.ui.activity.viewmodel.MainViewModel

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var viewModel: MainViewModel
    private val bindingMainActivity by lazy { ActivityMainBinding.inflate(layoutInflater) }

    // Initialize mSecurityPreferences
    private lateinit var mSecurityPreferences: SecurityPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bindingMainActivity.root)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        // Instance mSecurityPreferences
        mSecurityPreferences = SecurityPreferences(this)
        bindingMainActivity.username.text = mSecurityPreferences.getStoredString(USER_NAME)

        bindingMainActivity.allCategoriesFilter.setColorFilter(resources.getColor(R.color.colorAccent))

        bindingMainActivity.allCategoriesFilter.setOnClickListener(this)
        bindingMainActivity.goodVibesCategoryFilter.setOnClickListener(this)
        bindingMainActivity.badVibesCategoryFilter.setOnClickListener(this)
        bindingMainActivity.buttonSeeNewPhrase.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        showNewPhrase()
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
        val categoryFilters = listOf(
            R.id.all_categories_filter,
            R.id.good_vibes_category_filter,
            R.id.bad_vibes_category_filter
        )

        if (view.id in categoryFilters) {
            applyCategoryFilter(view.id)
        } else if (view.id == R.id.button_see_new_phrase) {
            if (viewModel.isRandom) {
                viewModel.filterByRandomCategory()
            }
            showNewPhrase()
        }
        Log.d("MainActivity", "list: ${viewModel.phraseList}")
        Log.d("MainActivity", "categoryFilter: ${viewModel.categoryFilter}")
    }

    private fun applyCategoryFilter(filter_value: Int) {

        bindingMainActivity.allCategoriesFilter.setColorFilter(resources.getColor(R.color.white))
        bindingMainActivity.badVibesCategoryFilter.setColorFilter(resources.getColor(R.color.white))
        bindingMainActivity.goodVibesCategoryFilter.setColorFilter(resources.getColor(R.color.white))

        when (filter_value) {
            R.id.all_categories_filter -> {
                bindingMainActivity.allCategoriesFilter.setColorFilter(resources.getColor(R.color.colorAccent))
                viewModel.filterByRandomCategory()
            }
            R.id.bad_vibes_category_filter -> {
                bindingMainActivity.badVibesCategoryFilter.setColorFilter(resources.getColor(R.color.colorAccent))
                viewModel.filterByBadVibesCategory()
            }
            R.id.good_vibes_category_filter -> {
                bindingMainActivity.goodVibesCategoryFilter.setColorFilter(resources.getColor(R.color.colorAccent))
                viewModel.filterByGoodVibesCategory()
            }
        }

        Log.d("MainActivity", "apply filter: ${viewModel.categoryFilter}")
        showNewPhrase()
    }

    private fun showNewPhrase() {
        viewModel.getNextPhrase()
        Log.d("MainActivity", "founded: ${viewModel.phraseFounded}")
        if (viewModel.phraseFounded == null || viewModel.phraseList.isEmpty()) {
            hidePhraseFields()
        } else {
            bindingMainActivity.phraseContent.visibility = View.VISIBLE
            updatePhraseDetails()
        }
    }

    private fun hidePhraseFields() {
        bindingMainActivity.phraseContent.visibility = View.GONE
    }

    private fun updatePhraseDetails() {
        viewModel.phraseFounded?.let {
            with(bindingMainActivity) {
                this.phraseMessage.text = it.text
                this.phraseAuthor.text = it.author ?: "Unknown"
                if (it.urlImage.isNullOrBlank()) {
                    this.phraseImage.visibility = View.GONE
                } else {
                    this.phraseImage.visibility = View.VISIBLE
                    this.phraseImage.tryLoadImage(it.urlImage)
                }
            }
        }
    }
}
