package com.example.motivationapp.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.motivationapp.databinding.ActivityPhraseListBinding
import com.example.motivationapp.repository.Mock
import com.example.motivationapp.repository.PhrasesDAO
import com.example.motivationapp.ui.recyclerview.adapter.PhraseListAdapter

class PhraseListActivity : AppCompatActivity() {

    private val bindingPhraseListActivity by lazy { ActivityPhraseListBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bindingPhraseListActivity.root)
        setRecyclerView()
    }

    private fun setRecyclerView() {
        val recyclerView = bindingPhraseListActivity.rvPhraseList
        recyclerView.adapter = PhraseListAdapter(
            Mock().getPhrasesList()
        )
    }


}