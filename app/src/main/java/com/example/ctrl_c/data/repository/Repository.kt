package com.example.ctrl_c.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.ctrl_c.data.local.UserPreference
import com.example.ctrl_c.data.remote.ApiService
import com.example.ctrl_c.model.response.GeneralResponse
import com.example.ctrl_c.model.result.Result
import com.example.ctrl_c.model.result.Result.Error
import com.example.ctrl_c.model.result.Result.Loading
import com.example.ctrl_c.model.result.Result.Success


class Repository(private val pref: UserPreference, private val apiService: ApiService) {

    fun register(
        fullname: String, email: String, password: String, accessType: Int
    ): LiveData<Result<GeneralResponse>> = liveData {
        emit(Loading)
        try {
            val response = apiService.register(
                fullname, email, password, accessType
            )
            if (response.error) {
                emit(Error(response.message))
            } else {
                emit(Success(response))
            }
        } catch (e: Exception) {
            emit(Error(e.message.toString()))
        }
    }

    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            preferences: UserPreference, apiService: ApiService
        ): Repository = instance ?: synchronized(this) {
            instance ?: Repository(preferences, apiService)
        }.also { instance = it }
    }
}
