package com.example.ctrl_c.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginResponse(

	@field:SerializedName("error")
	val error: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("token")
    val token: String,
) : Parcelable