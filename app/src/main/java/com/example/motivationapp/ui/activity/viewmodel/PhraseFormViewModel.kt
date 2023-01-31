package com.example.motivationapp.ui.activity.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.motivationapp.model.Phrase
import com.example.motivationapp.repository.AppDatabase

class PhraseFormViewModel(application: Application) : AndroidViewModel(application) {

    private val _phrasesDAO by lazy {
        AppDatabase.getInstance(application.applicationContext).phrasesDao()
    }

    private var _phraseFounded: Phrase? = null
    val phraseFounded: Phrase? get() = _phraseFounded

    fun findPhraseById(id: Long): Phrase? {
        _phraseFounded = _phrasesDAO.findById(id)
        return _phraseFounded
    }

    fun saveNewPhrase(phrase: Phrase) {
        _phrasesDAO.save(phrase)
    }

    fun tryLoadPhraseDetails(id: Long) {
        _phraseFounded = _phrasesDAO.findById(id)
    }

    fun savePhrase(phrase: Phrase) {
        _phrasesDAO.save(phrase)
    }

}