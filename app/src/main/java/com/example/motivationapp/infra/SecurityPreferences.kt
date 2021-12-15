package com.example.motivationapp.infra

import android.content.Context

class SecurityPreferences(context: Context) {

    private val mSharedPreferences =
        context.getSharedPreferences("motivation", Context.MODE_PRIVATE)

    fun storeString(key: String, value: String) {
        // Edit the val, put a String and then, apply the changes
        mSharedPreferences.edit().putString(key, value).apply()
    }

    fun getStoredString(key: String): String {
        // If 'key' don't have a value, return a "" String. And, if this return is null, return a "" String
        return mSharedPreferences.getString(key, "") ?: ""
    }
}