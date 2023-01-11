package com.example.motivationapp.repository.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.motivationapp.model.Phrase

@Dao
interface PhrasesDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(vararg phrase: Phrase)

    @Query("SELECT * FROM Phrase")
    fun findAll(): List<Phrase>

    @Query("SELECT * FROM Phrase WHERE id = :id")
    fun findById(id: Long): Phrase?

    @Query("SELECT * FROM Phrase WHERE phrase_category = :categoryId")
    fun findByCategoryId(categoryId: Int): Phrase?

}
