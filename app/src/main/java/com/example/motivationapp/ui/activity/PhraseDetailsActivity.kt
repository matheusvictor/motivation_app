package com.example.motivationapp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.motivationapp.databinding.ActivityPhraseDetailsBinding
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
        title = "Details"
        setContentView(bindingPhraseDetails.root)
    }

    override fun onResume() {
        super.onResume()
        phrase = phrasesDao.findById(phraseId)
        phrase?.let {
            fillFields(it)
        } ?: finish()
    }

    private fun setPhraseById() {
        phraseId = intent.getLongExtra("PHRASE_ID", 0L)
    }

    private fun fillFields(phrase: Phrase) {
        with(phrase) {

        }
    }

}