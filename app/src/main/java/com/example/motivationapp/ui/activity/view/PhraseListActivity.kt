package com.example.motivationapp.ui.activity.view

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
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

    private lateinit var indicatorLayout: LinearLayout
    private lateinit var prevPhrase: ImageView
    private lateinit var nextPhrase: ImageView

    private var currentPage = 0

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
        setDotsViewPager()
    }

    private fun setRecyclerView() {
        val recyclerView = bindingPhraseListActivity.rvPhraseList
        recyclerView.adapter = adapter

        // Configurando o LinearLayoutManager com orientação horizontal
        val layoutManager =
            NonScrollableLinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager

        adapter.whenClickOnItem = {
            val intent = Intent(
                this, PhraseDetailsActivity::class.java
            ).apply {
                putExtra(PHRASE_ID, it.id)
            }
            startActivity(intent)
        }
    }

    private inner class NonScrollableLinearLayoutManager(
        context: Context,
        orientation: Int,
        reverseLayout: Boolean
    ) :
        LinearLayoutManager(context, orientation, reverseLayout) {
        override fun canScrollHorizontally(): Boolean {
            return false
        }
    }

    private fun setDotsViewPager() {
        setupIndicators(adapter.itemCount)
    }

    private fun setupIndicators(count: Int) {

        with(bindingPhraseListActivity) {
            indicatorLayout = this.linearLayout
            prevPhrase = this.ivPrevPhrase
            nextPhrase = this.ivNextPhrase
        }

        prevPhrase.setOnClickListener {
            if (currentPage > 0) {
                currentPage--
                bindingPhraseListActivity.rvPhraseList.smoothScrollToPosition(currentPage)
                updateIndicators(currentPage)
            }
        }

        nextPhrase.setOnClickListener {
            if (currentPage < adapter.itemCount - 1) {
                currentPage++
                bindingPhraseListActivity.rvPhraseList.smoothScrollToPosition(currentPage)
                updateIndicators(currentPage)
            }
        }

        val indicators = arrayOfNulls<ImageView>(count)
        indicatorLayout.removeAllViews()

        for (i in indicators.indices) {
            indicators[i] = ImageView(this)
            indicators[i]?.setImageResource(R.drawable.default_dot)
            indicators[i]?.setPadding(8, 8, 8, 8)
            indicatorLayout.addView(indicators[i])
        }

        updateIndicators(currentPage)
    }

    private fun updateIndicators(position: Int) {
        val count = indicatorLayout.childCount

        val circleSize = resources.getDimensionPixelSize(R.dimen.circle_indicator_size)
        val layoutParams = LinearLayout.LayoutParams(circleSize, circleSize)
        layoutParams.setMargins(3, 0, 3, 0)

        for (i in 0 until count) {
            val indicator = indicatorLayout.getChildAt(i) as ImageView
            indicator.layoutParams = layoutParams
            if (i == position) {
                indicator.setImageResource(R.drawable.selected_dot)
            } else {
                indicator.setImageResource(R.drawable.default_dot)
            }
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