package com.example.motivationapp.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.motivationapp.R
import com.example.motivationapp.databinding.ViewPagerLayoutBinding

class ViewPagerAdapter(
    private val context: Context,
    private var mDotsQuant: Int = 0
) :
    RecyclerView.Adapter<ViewPagerAdapter.ViewPagerViewHolder>() {


    inner class ViewPagerViewHolder(private val binding: ViewPagerLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var dot: ImageView

        fun linkToView(dot: ImageView) {
            dot.setColorFilter(R.color.colorAccent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        val view = ViewPagerLayoutBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false
        )
        return ViewPagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return this.mDotsQuant
    }

}