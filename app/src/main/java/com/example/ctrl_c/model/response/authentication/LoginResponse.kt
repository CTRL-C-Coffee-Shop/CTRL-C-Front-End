package com.example.ctrl_c.model.response.authentication

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginResponse(

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("access_type")
    val accessType: Boolean,

    @field:SerializedName("full_name")
    val fullName: String,

    @field:SerializedName("user_id")
    val userId: Int,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("token")
    val token: String

) : Parcelable
