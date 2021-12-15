package com.example.motivationapp.infra

import android.content.Context

class SecurityPreferences(context: Context) {

    private val mSharedPreferences =
        context.getSharedPreferences("motivation", Context.MODE_PRIVATE)

    fun storeString(key: String, value: String) {
        // Edit the val, put a String and then, apply the changes
        mSharedPreferences.edit().putString(key, value).apply()
    }

    fun getStoredString(key: String) {

    }
}