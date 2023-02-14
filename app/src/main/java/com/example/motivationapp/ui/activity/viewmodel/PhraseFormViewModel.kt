package com.example.motivationapp.ui.activity.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.motivationapp.model.Phrase
import com.example.motivationapp.repository.AppDatabase

class PhraseFormViewModel(application: Application) : AndroidViewModel(application) {

    private val _phrasesDAO by lazy {
        AppDatabase.getInstance(application.applicationContext).phrasesDao()
    }

    private var _phraseFounded = MutableLiveData<Phrase?>(null)
    val phraseFounded: LiveData<Phrase?> get() = _phraseFounded

    fun getPhraseById(id: Long): LiveData<Phrase?> {
        _phraseFounded.value = _phrasesDAO.findById(id)
        return _phraseFounded
    }

    fun savePhrase(phrase: Phrase) {
        _phrasesDAO.save(phrase)
    }

    fun updatePhrase(phrase: Phrase) {
        _phrasesDAO.update(phrase)
    }
}
