package com.example.motivationapp.ui.activity.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.motivationapp.model.Phrase
import com.example.motivationapp.repository.AppDatabase

class PhraseDetailsViewModel(application: Application) : AndroidViewModel(application) {

    private val _phrasesDao by lazy {
        AppDatabase.getInstance(application.applicationContext).phrasesDao()
    }

    private val _phraseId = MutableLiveData<Long>()
    val phraseId: LiveData<Long> get() = _phraseId

    private var _phraseFounded = MutableLiveData<Phrase?>()
    val phraseFounded: LiveData<Phrase?> get() = _phraseFounded

    fun getPhraseById(id: Long) {
        _phraseFounded.value = _phrasesDao.findById(id)
        _phraseId.value = _phraseFounded.value?.id ?: 0L
    }

    fun removePhrase() {
        _phraseFounded.value?.let { _phrasesDao.delete(it) }
    }
}
