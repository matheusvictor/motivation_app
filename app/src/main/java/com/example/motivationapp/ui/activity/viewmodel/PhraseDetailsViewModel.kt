package com.example.motivationapp.ui.activity.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.motivationapp.model.Phrase
import com.example.motivationapp.repository.AppDatabase

class PhraseDetailsViewModel(application: Application) : AndroidViewModel(application) {

    private val _phrasesDao by lazy {
        AppDatabase.getInstance(application.applicationContext).phrasesDao()
    }

    private var _phraseId: Long = 0L
    val phraseId get() = _phraseId

    private var _phraseFounded: Phrase? = null
    val phraseFounded: Phrase? get() = _phraseFounded

    fun getPhraseById(id: Long) {
        _phraseFounded = _phrasesDao.findById(id)
        _phraseId = _phraseFounded?.id ?: 0L
    }

    fun removePhrase() {
        _phraseFounded?.let { _phrasesDao.delete(it) }
    }
}
