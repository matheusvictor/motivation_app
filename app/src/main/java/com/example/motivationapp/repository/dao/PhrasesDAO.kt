package com.example.motivationapp.repository.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.motivationapp.model.Phrase

@Dao
interface PhrasesDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(vararg phrase: Phrase)

}
