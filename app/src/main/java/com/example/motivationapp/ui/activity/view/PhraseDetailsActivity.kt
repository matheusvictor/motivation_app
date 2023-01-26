package com.example.motivationapp.ui.activity.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.motivationapp.R
import com.example.motivationapp.constants.PHRASE_ID
import com.example.motivationapp.databinding.ActivityPhraseDetailsBinding
import com.example.motivationapp.extensions.tryLoadImage
import com.example.motivationapp.ui.activity.viewmodel.PhraseDetailsViewModel

class PhraseDetailsActivity : AppCompatActivity() {

    private lateinit var viewModel: PhraseDetailsViewModel

    private val bindingPhraseDetails by lazy { ActivityPhraseDetailsBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bindingPhraseDetails.root)
        viewModel = ViewModelProvider(this)[PhraseDetailsViewModel::class.java]

        title = "Phrase Details"
    }

    override fun onResume() {
        super.onResume()
        fillFields()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_phrase_list_options, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_edit_item -> {
                Intent(this, PhraseFormActivity::class.java).apply {
                    putExtra(PHRASE_ID, viewModel.phraseId)
                    startActivity(this)
                }
            }
            R.id.menu_delete_item -> {
                viewModel.removePhrase()
                finish()
            }
        }
        return super.onContextItemSelected(item)
    }

    private fun fillFields() {
        viewModel.getPhraseById(intent.getLongExtra(PHRASE_ID, 0L))
        with(bindingPhraseDetails) {
            phraseDetailsImage.tryLoadImage(viewModel.phraseFounded?.urlImage)
            phraseDetailsAuthor.text = viewModel.phraseFounded?.author
            phraseTextDetails.text = viewModel.phraseFounded?.text
        }
    }
}
