package com.example.ctrl_c.model.response.stores

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class StoresItem(

	@field:SerializedName("address")
	val address: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int

) : Parcelable