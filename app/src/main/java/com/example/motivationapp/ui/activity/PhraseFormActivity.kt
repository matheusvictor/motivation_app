package com.example.motivationapp.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.motivationapp.R
import com.example.motivationapp.databinding.ActivityPhraseFormActivityBinding
import com.example.motivationapp.model.Phrase
import com.example.motivationapp.repository.AppDatabase

class PhraseFormActivity : AppCompatActivity(R.layout.activity_phrase_form_activity) {

    private val bindingPhraseFormActivity by lazy {
        ActivityPhraseFormActivityBinding.inflate(layoutInflater)
    }

    private val phrasesDao by lazy {
        AppDatabase.getInstance(this).phrasesDao()
    }

    private var urlImage: String? = null
    private var phraseId: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bindingPhraseFormActivity.root)

        title = "Add New Phrase" // define o título da Activity

        setSaveButton()
    }

    private fun setSaveButton() {
        val saveButton = bindingPhraseFormActivity.btFormSavePhrase

        saveButton.setOnClickListener {
            val newPhrase = createNewPhrase()
            phrasesDao.save(newPhrase)
            Toast.makeText(this, "Created with success", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    private fun createNewPhrase(): Phrase {

        val phraseTextView = bindingPhraseFormActivity.formPhraseText
        val phrase = phraseTextView.text.toString()

        val phraseAuthor = bindingPhraseFormActivity.formPhraseAuthor
        val author = phraseAuthor.text.toString()

        return Phrase(
            id = phraseId,
            urlImage = urlImage,
            text = phrase,
            author = author,
            category = 1
        )
    }

}