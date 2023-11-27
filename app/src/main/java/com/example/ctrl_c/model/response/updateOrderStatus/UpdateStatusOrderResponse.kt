package com.example.ctrl_c.model.response.updateOrderStatus

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class UpdateStatusOrderResponse(

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("order")
	val order: Order
) : Parcelable