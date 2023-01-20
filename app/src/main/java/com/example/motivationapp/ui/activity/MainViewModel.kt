package com.example.motivationapp.ui.activity

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

    private var _categoryFilter: Int = randomCategory()
    val categoryFilter: Int get() = _categoryFilter

    private var _phraseFounded: Phrase? = null
    val phraseFounded: Phrase? get() = _phraseFounded

    init {
        getNextPhrase(categoryFilter)
    }

    private fun randomCategory(): Int {
        return Random.nextInt(
            from = MotivationAppConstants.PHRASES_FILTER.GOOD_VIBES,
            until = MotivationAppConstants.PHRASES_FILTER.BAD_VIBES + 1
        )
    }

    fun filterByGoodVibesCategory() {
        _categoryFilter = MotivationAppConstants.PHRASES_FILTER.GOOD_VIBES
    }

    fun filterByBadVibesCategory() {
        _categoryFilter = MotivationAppConstants.PHRASES_FILTER.BAD_VIBES
    }

    fun filterByRandomCategory() {
        _categoryFilter = randomCategory()
        Log.d("MainViewModel", "_category $_categoryFilter")
        Log.d("MainViewModel", categoryFilter.toString())
    }

    fun getNextPhrase(filter: Int) {
        _phraseFounded = _phrasesDAO.findByCategoryId(filter)
    }

}