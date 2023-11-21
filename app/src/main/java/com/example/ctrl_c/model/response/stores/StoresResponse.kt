package com.example.ctrl_c.model.response.stores

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class StoresResponse(

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("stores")
	val stores: List<StoresItem>,

	@field:SerializedName("message")
	val message: String
) : Parcelable