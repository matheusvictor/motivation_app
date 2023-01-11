package com.example.motivationapp.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.motivationapp.R
import com.example.motivationapp.databinding.ActivityPhraseListBinding
import com.example.motivationapp.repository.AppDatabase
import com.example.motivationapp.ui.recyclerview.adapter.PhraseListAdapter

class PhraseListActivity : AppCompatActivity(R.layout.activity_phrase_details) {

    private val bindingPhraseListActivity by lazy { ActivityPhraseListBinding.inflate(layoutInflater) }
    private val adapter = PhraseListAdapter(context = this)

    private val phrasesDAO by lazy {
        AppDatabase.getInstance(this).phrasesDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bindingPhraseListActivity.root)
        setRecyclerView()
        setAddNewPhraseButton()
        title = "My Phrases"
    }

    override fun onResume() {
        super.onResume()
        adapter.update(phrasesDAO.findAll())
    }

    private fun setRecyclerView() {
        val recyclerView = bindingPhraseListActivity.rvPhraseList
        recyclerView.adapter = adapter
        adapter.whenClickOnItem = {
            // TODO
        }
    }

    private fun setAddNewPhraseButton() {
        val button = bindingPhraseListActivity.extendedFab
        button.setOnClickListener {
            goToNewPhraseForm()
        }
    }

    private fun goToNewPhraseForm() {
        val intent = Intent(this, PhraseFormActivity::class.java)
        startActivity(intent)
    }
}