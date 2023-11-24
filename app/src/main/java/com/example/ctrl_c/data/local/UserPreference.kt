package com.example.ctrl_c.data.local

import android.content.Context

class UserPreference(context: Context) {
    private val preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    fun getToken(): String? {
        return preferences.getString(TOKEN, null)
    }

    fun saveToken(token: String) {
        val edit = preferences.edit()
        edit.putString(TOKEN, token)
        edit.apply()
    }

    fun saveName(name: String) {
        val edit = preferences.edit()
        edit.putString(NAME, name)
        edit.apply()
    }

    fun saveUserId(id: Int) {
        val edit = preferences.edit()
        edit.putInt(USER_ID, id)
        edit.apply()
    }

    fun saveUserEmail(email: String) {
        val edit = preferences.edit()
        edit.putString(EMAIL, email)
        edit.apply()
    }

    fun getUserId(): Int {
        return preferences.getInt(USER_ID, 0)
    }

    fun getUserEmail(): String? {
        return preferences.getString(EMAIL, null)
    }

    fun getUserFullName(): String? {
        return preferences.getString(NAME, null)
    }

    fun clearPreferences() {
        preferences.edit().clear().apply()
    }


    companion object {
        private const val TOKEN = "token"
        private const val NAME = "name"
        private const val USER_ID = "user_id"
        private const val EMAIL = "email"
    }
}