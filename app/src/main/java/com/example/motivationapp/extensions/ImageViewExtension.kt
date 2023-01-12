package com.example.motivationapp.extensions

import android.widget.ImageView
import coil.load
import com.example.motivationapp.R

fun ImageView.tryLoadImage(
    url: String? = null,
    fallback: Int = R.drawable.default_image
) {
    load(url) {
        fallback(fallback)
        error(R.drawable.default_image)
        placeholder(R.drawable.default_image)
    }
}