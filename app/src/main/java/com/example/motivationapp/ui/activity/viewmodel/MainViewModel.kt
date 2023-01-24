package com.example.motivationapp.ui.activity.viewmodel

import android.app.Application
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

    init {
        _categoryFilter = randomCategory()
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

    fun getNextPhrase() {
        _phraseFounded = _phrasesDAO.findByCategoryId(_categoryFilter)
    }
}