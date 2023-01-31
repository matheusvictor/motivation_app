package com.example.motivationapp.ui.activity.view

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.motivationapp.constants.PHRASE_ID
import com.example.motivationapp.databinding.ActivityPhraseFormActivityBinding
import com.example.motivationapp.extensions.tryLoadImage
import com.example.motivationapp.infra.MotivationAppConstants
import com.example.motivationapp.model.Phrase
import com.example.motivationapp.ui.activity.viewmodel.PhraseFormViewModel
import com.example.motivationapp.ui.dialog.ImageFormDialog

class PhraseFormActivity : AppCompatActivity() {

    private val bindingPhraseFormActivity by lazy {
        ActivityPhraseFormActivityBinding.inflate(layoutInflater)
    }
    private lateinit var phraseFormViewModel: PhraseFormViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bindingPhraseFormActivity.root)
        phraseFormViewModel = ViewModelProvider(this)[PhraseFormViewModel::class.java]

        title = "Add New Phrase"
        setSaveButton()

        bindingPhraseFormActivity.formImagePhrase.setOnClickListener {
            ImageFormDialog(this)
                .show(phraseFormViewModel.phraseFounded?.urlImage) {
                    bindingPhraseFormActivity.formImagePhrase.tryLoadImage(
                        phraseFormViewModel.phraseFounded?.urlImage
                    )
                }
        }
    }

    override fun onResume() {
        super.onResume()
        fillFields()
    }

    private fun setSaveButton() {
        bindingPhraseFormActivity.btFormSavePhrase.setOnClickListener {
            val newPhrase = createNewPhrase()
            phraseFormViewModel.savePhrase(newPhrase)
            Toast.makeText(this, "Created with success", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun createNewPhrase(): Phrase {

        val phraseTextView = bindingPhraseFormActivity.formPhraseText
        val phrase = phraseTextView.text.toString()

//        if (phrase.isBlank()) {
//            setErrorTextField(true)
//        }

        val phraseAuthor = bindingPhraseFormActivity.formPhraseAuthor
        val author = phraseAuthor.text.toString()

        var category = 1
        if (bindingPhraseFormActivity.radioButtonBadVibesCategory.isChecked) {
            category = 2
        }

        val newPhrase = Phrase(
            id = 0L,
            urlImage = "",
            text = phrase,
            author = author,
            category = category
        )
        Log.i("PhraseFormActivity", newPhrase.toString())
        return newPhrase
    }

    //    private fun setErrorTextField(error: Boolean) {
//        if (error) {
//            bindingPhraseFormActivity.formPhraseText.error = "AA"
//        }
//    }
    private fun fillFields() {
        val phrase = phraseFormViewModel.findPhraseById(
            intent.getLongExtra(PHRASE_ID, -1L)
        )

        phrase?.let {
            title = "Edit Phrase"
            bindingPhraseFormActivity.formImagePhrase.tryLoadImage(it.urlImage)
            bindingPhraseFormActivity.formPhraseText.setText(it.text)
            bindingPhraseFormActivity.formPhraseAuthor.setText(it.author)
            if (it.category == MotivationAppConstants.PHRASES_FILTER.GOOD_VIBES) {
                bindingPhraseFormActivity.radioButtonGoodVibesCategory.isChecked = true
                bindingPhraseFormActivity.radioButtonBadVibesCategory.isChecked = false
            } else {
                bindingPhraseFormActivity.radioButtonBadVibesCategory.isChecked = true
                bindingPhraseFormActivity.radioButtonGoodVibesCategory.isChecked = false
            }
        }

    }

}