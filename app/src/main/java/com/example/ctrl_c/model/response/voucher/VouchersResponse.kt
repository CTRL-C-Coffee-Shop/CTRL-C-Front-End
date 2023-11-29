package com.example.ctrl_c.model.response.voucher

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class VouchersResponse(

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("vouchers")
	val vouchers: List<VouchersItem>
) : Parcelable