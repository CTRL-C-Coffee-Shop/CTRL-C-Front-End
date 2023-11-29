package com.example.ctrl_c.model.response.voucher

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class VouchersItem(

	@field:SerializedName("number")
	val number: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("discount")
	val discount: Int,

	@field:SerializedName("id")
	val id: Int
) : Parcelable