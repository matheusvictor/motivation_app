package com.example.motivationapp.ui.activity.view

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.motivationapp.R
import com.example.motivationapp.constants.PHRASE_ID
import com.example.motivationapp.databinding.ActivityPhraseListBinding
import com.example.motivationapp.databinding.RemoveItemDialogBinding
import com.example.motivationapp.model.Phrase
import com.example.motivationapp.repository.AppDatabase
import com.example.motivationapp.ui.recyclerview.adapter.PhraseListAdapter

class PhraseListActivity : AppCompatActivity(R.layout.activity_phrase_details),
    PhraseListAdapter.LongClickedItem {

    private val bindingPhraseListActivity by lazy { ActivityPhraseListBinding.inflate(layoutInflater) }
    private val adapter = PhraseListAdapter(context = this, whenLongClickOnItem = this)

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
            val intent = Intent(
                this, PhraseDetailsActivity::class.java
            ).apply {
                putExtra(PHRASE_ID, it.id)
            }
            startActivity(intent)
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

    override fun whenLongClickOnItem(phrase: Phrase) {
        RemoveItemDialogBinding.inflate(LayoutInflater.from(this)).apply {
            AlertDialog.Builder(bindingPhraseListActivity.root.context)
                .setView(root)
                .setNegativeButton("Cancel") { _, _ -> }
                .setPositiveButton("Confirm") { _, _ ->
                    phrasesDAO.delete(phrase)
                    adapter.update(phrasesDAO.findAll())
                    Toast.makeText(applicationContext, "Phrase was remove!", Toast.LENGTH_SHORT)
                        .show()
                }
                .show()
        }
    }
}