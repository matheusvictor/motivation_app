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

        Log.i("PhraseFormActivity init", phraseFormViewModel.phraseFounded.toString())
        loadData()
        Log.i("PhraseFormActivity fnd", phraseFormViewModel.phraseFounded.toString())

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
        title = if (phraseFormViewModel.phraseFounded == null) {
            "Add New Phrase"
        } else {
            "Edit Phrase"
        }
        loadPhrasesInformation()
        Log.i("PhraseFormActivity", "On resume")
    }

    private fun setSaveButton() {
        bindingPhraseFormActivity.btFormSavePhrase.setOnClickListener {

            if (phraseFormViewModel.phraseFounded == null) {
                val newPhrase = createNewPhrase()
                phraseFormViewModel.savePhrase(newPhrase)
                Log.i("PhraseFormActivity add", newPhrase.toString())
                Toast.makeText(this, "Created with success", Toast.LENGTH_SHORT).show()
            } else {
                setPhrasesInformation()
                phraseFormViewModel.updatePhrase(phraseFormViewModel.phraseFounded!!)
                Toast.makeText(this, "Updated with success", Toast.LENGTH_SHORT).show()
                Log.i("PhraseFormActivity up", phraseFormViewModel.phraseFounded!!.toString())
            }
            finish()
        }
    }

    private fun createNewPhrase(): Phrase {
        val phraseMessage = bindingPhraseFormActivity.formPhraseText.text.toString()
        if (phraseMessage.isBlank()) setErrorTextField(true)

        val author = bindingPhraseFormActivity.formPhraseAuthor.text.toString()

        var category = 1
        if (bindingPhraseFormActivity.radioButtonBadVibesCategory.isChecked) category = 2

        val newPhrase = Phrase(
            text = phraseMessage,
            author = author,
            category = category
        )

        Log.i("PhraseFormActivity", newPhrase.toString())
        return newPhrase
    }

    private fun setErrorTextField(error: Boolean) {
        if (error) {
            bindingPhraseFormActivity.formPhraseText.error = "AA"
        }
    }

    private fun loadPhrasesInformation() {
        phraseFormViewModel.phraseFounded?.let {
            bindingPhraseFormActivity.formImagePhrase.tryLoadImage(it.urlImage)
            bindingPhraseFormActivity.formPhraseText.setText(it.text)
            bindingPhraseFormActivity.formPhraseAuthor.setText(it.author)
            if (it.category == MotivationAppConstants.PHRASES_FILTER.BAD_VIBES) {
                bindingPhraseFormActivity.radioButtonBadVibesCategory.isChecked = true
                bindingPhraseFormActivity.radioButtonGoodVibesCategory.isChecked = false
            } else {
                bindingPhraseFormActivity.radioButtonGoodVibesCategory.isChecked = true
                bindingPhraseFormActivity.radioButtonBadVibesCategory.isChecked = false
            }
        }
    }

    private fun setPhrasesInformation() {
        phraseFormViewModel.phraseFounded?.apply {
            this.text = bindingPhraseFormActivity.formPhraseText.text.toString()
            this.author = bindingPhraseFormActivity.formPhraseAuthor.text.toString()
            if (bindingPhraseFormActivity.radioButtonGoodVibesCategory.isChecked) {
                this.category = 1
            } else {
                this.category = 2
            }
        }
    }

    private fun loadData() {
        val bundle = intent.extras

        bundle?.let {
            phraseFormViewModel.findPhraseById(intent.getLongExtra(PHRASE_ID, -1L))
            Log.i("PhraseFormActivity fnd", phraseFormViewModel.phraseFounded.toString())
        }
    }
}
