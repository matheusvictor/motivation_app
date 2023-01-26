package com.example.motivationapp.ui.activity.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.motivationapp.infra.MotivationAppConstants
import com.example.motivationapp.model.Phrase
import com.example.motivationapp.repository.AppDatabase
import kotlin.random.Random

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _phrasesDAO by lazy {
        AppDatabase.getInstance(application.applicationContext).phrasesDao()
    }

    private var _categoryFilter: Int
    val categoryFilter: Int get() = _categoryFilter

    private var _isRandom: Boolean = true
    val isRandom: Boolean get() = _isRandom

    private var _phraseFounded: Phrase? = null
    val phraseFounded: Phrase? get() = _phraseFounded

    private var _phrasesList: List<Phrase>

    init {
        _categoryFilter = randomCategory()
        _phrasesList = getPhrasesByCategory()
        getNextPhrase()
    }

    private fun randomCategory(): Int {
        return Random.nextInt(
            from = MotivationAppConstants.PHRASES_FILTER.GOOD_VIBES,
            until = MotivationAppConstants.PHRASES_FILTER.BAD_VIBES + 1
        )
    }

    fun filterByGoodVibesCategory() {
        _isRandom = false
        _categoryFilter = MotivationAppConstants.PHRASES_FILTER.GOOD_VIBES
    }

    fun filterByBadVibesCategory() {
        _isRandom = false
        _categoryFilter = MotivationAppConstants.PHRASES_FILTER.BAD_VIBES
    }

    fun filterByRandomCategory() {
        _isRandom = true
        _categoryFilter = randomCategory()
    }

    private fun getPhrasesByCategory(): List<Phrase> {
        return _phrasesDAO.findAllByCategory(_categoryFilter)
    }

    fun getNextPhrase() {

        _phrasesList = _phrasesDAO.findAllByCategory(_categoryFilter)
        var randomIndex = Random.nextInt(from = 0, until = (_phrasesList.size))
        Log.d("MainViewModel", "Random Index: $randomIndex")

        Log.d("MainViewModel", _phrasesList.toString())
        _phraseFounded = _phrasesList[randomIndex]
        Log.d("MainViewModel", "Founded: $_phraseFounded")
    }
}