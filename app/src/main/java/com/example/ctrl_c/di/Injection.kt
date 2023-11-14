package com.example.ctrl_c.di

import android.content.Context
import com.example.ctrl_c.data.local.UserPreference
import com.example.ctrl_c.data.remote.ApiConfig
import com.example.ctrl_c.data.repository.Repository

object Injection {
    fun provideRepository(context: Context): Repository {
        val preferences = UserPreference(context)
        val apiService = ApiConfig.getApiService()
        return Repository.getInstance(preferences, apiService)
    }
}