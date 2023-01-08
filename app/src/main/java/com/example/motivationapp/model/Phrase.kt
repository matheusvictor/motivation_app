package com.example.motivationapp.model

data class Phrase(
    val description: String,
    val author: String? = null,
    val category: Int,
)