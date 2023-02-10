package com.example.motivationapp.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "phrase")
@Parcelize
data class Phrase(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Long = 0L,
    @ColumnInfo(name = "url_image") var urlImage: String? = null,
    @ColumnInfo(name = "phrase_message") var text: String,
    @ColumnInfo(name = "author") var author: String? = null,
    @ColumnInfo(name = "category") var category: Int,
) : Parcelable