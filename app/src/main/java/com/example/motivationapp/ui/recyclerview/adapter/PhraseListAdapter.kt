package com.example.motivationapp.ui.recyclerview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.motivationapp.databinding.PhraseItemBinding
import com.example.motivationapp.model.Phrase

class PhraseListAdapter(phrases: List<Phrase> = emptyList()) :
    RecyclerView.Adapter<PhraseListAdapter.ViewHolder>() {

    private val dataSet = phrases.toMutableList() // copy of the list received by parameter

    inner class ViewHolder(private val binding: PhraseItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun linkPhraseToView(phrase: Phrase) {

            val description = binding.tvDescription
            description.text = phrase.text

            val author = binding.tvAuthor
            author.text = phrase.author
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = PhraseItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val phrase = dataSet[position]
        holder.linkPhraseToView(phrase)
    }

    override fun getItemCount(): Int = this.dataSet.size

}
