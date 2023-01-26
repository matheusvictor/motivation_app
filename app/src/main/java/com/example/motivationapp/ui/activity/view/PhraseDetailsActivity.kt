package com.example.motivationapp.ui.activity.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.motivationapp.R
import com.example.motivationapp.constants.PHRASE_ID
import com.example.motivationapp.databinding.ActivityPhraseDetailsBinding
import com.example.motivationapp.extensions.tryLoadImage
import com.example.motivationapp.model.Phrase
import com.example.motivationapp.repository.AppDatabase

class PhraseDetailsActivity : AppCompatActivity() {

    private val bindingPhraseDetails by lazy {
        ActivityPhraseDetailsBinding.inflate(layoutInflater)
    }
    private val phrasesDao by lazy {
        AppDatabase.getInstance(this).phrasesDao()
    }
    private var phraseId: Long = 0L
    private var phrase: Phrase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bindingPhraseDetails.root)
        title = "Phrase Details"
        loadPhraseById()
    }

    override fun onResume() {
        super.onResume()
        phrase = phrasesDao.findById(phraseId)
        phrase?.let {
            fillFields(it)
        } ?: finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_phrase_list_options, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_edit_item -> {
                Intent(this, PhraseFormActivity::class.java).apply {
                    putExtra(PHRASE_ID, phraseId)
                    startActivity(this)
                }
            }
            R.id.menu_delete_item -> {
                phrase?.let {
                    phrasesDao.delete(it)
                }
                finish()
            }
        }
        return super.onContextItemSelected(item)
    }

    private fun loadPhraseById() {
        phraseId = intent.getLongExtra(PHRASE_ID, 0L)
    }

    private fun fillFields(phrase: Phrase) {
        with(bindingPhraseDetails) {
            phraseDetailsImage.tryLoadImage(phrase.urlImage)
            phraseDetailsAuthor.text = phrase.author
            phraseTextDetails.text = phrase.text
        }
    }

}