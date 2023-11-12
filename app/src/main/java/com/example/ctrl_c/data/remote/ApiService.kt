package com.example.ctrl_c.data.remote

import com.example.ctrl_c.model.response.GeneralResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("/register")
    suspend fun register(
        @Field("FullName") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("AccessType") accessType: Int,
    ): GeneralResponse
}