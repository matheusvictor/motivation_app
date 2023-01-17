package com.example.motivationapp.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.motivationapp.databinding.PhraseItemBinding
import com.example.motivationapp.extensions.tryLoadImage
import com.example.motivationapp.model.Phrase

class PhraseListAdapter(
    private val context: Context,
    phrases: List<Phrase> = emptyList(),
    var whenClickOnItem: (phrase: Phrase) -> Unit = {},
    var whenLongClickOnItem: (phrase: Phrase) -> Unit = {}
) :
    RecyclerView.Adapter<PhraseListAdapter.ViewHolder>() {

    private val dataSet = phrases.toMutableList()

    inner class ViewHolder(private val binding: PhraseItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var phrase: Phrase

        init {
            itemView.setOnClickListener {
                if (::phrase.isInitialized) {
                    whenClickOnItem(phrase)
                }
            }
            itemView.setOnLongClickListener {
                whenLongClickOnItem.invoke(dataSet[adapterPosition])
                return@setOnLongClickListener true
            }
        }

        fun linkPhraseToView(phrase: Phrase) {
            this.phrase = phrase

            val phraseMessage = binding.tvDescription
            phraseMessage.text = phrase.text

            val author = binding.tvAuthor
            author.text = phrase.author

            val category = binding.tvCategory
            when (phrase.category) {
                2 -> {
                    category.text = "Good Vibes"

                }
                3 -> {
                    category.text = "Bad Vibes"
                }
            }

            if (phrase.urlImage.isNullOrBlank()) {
                binding.ivPhraseCategory.visibility = View.GONE
            } else {
                binding.ivPhraseCategory.visibility = View.VISIBLE
                binding.ivPhraseCategory.tryLoadImage(phrase.urlImage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = PhraseItemBinding.inflate(
            LayoutInflater.from(context),
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

    fun update(phrases: List<Phrase>) {
        this.dataSet.clear()
        this.dataSet.addAll(phrases)
        notifyDataSetChanged()
    }

}
