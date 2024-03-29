package com.example.motivationapp.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.motivationapp.model.Phrase
import com.example.motivationapp.repository.dao.PhrasesDAO

@Database(entities = [Phrase::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {

    abstract fun phrasesDao(): PhrasesDAO

    companion object {
        private lateinit var INSTANCE: AppDatabase

        fun getInstance(context: Context): AppDatabase {
            if (!::INSTANCE.isInitialized) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        "phrases.db"
                    ).allowMainThreadQueries()
                        .createFromAsset("database/phrases.db")
                        .build()
                }
            }
            return INSTANCE
        }
    }

}