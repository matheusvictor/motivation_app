package com.example.motivationapp.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Phrase(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    @ColumnInfo(name = "url_image") val urlImage: String? = null,
    @ColumnInfo(name = "text") val text: String,
    @ColumnInfo(name = "author") val author: String? = null,
    @ColumnInfo(name = "category") val category: Int,
) : Parcelable