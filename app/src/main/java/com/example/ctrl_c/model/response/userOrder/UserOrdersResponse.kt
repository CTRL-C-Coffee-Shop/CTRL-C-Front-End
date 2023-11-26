package com.example.ctrl_c.model.response.userOrder

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class UserOrdersResponse(

	@field:SerializedName("orders")
	val orders: List<OrdersItem>,

	@field:SerializedName("message")
	val message: String
) : Parcelable