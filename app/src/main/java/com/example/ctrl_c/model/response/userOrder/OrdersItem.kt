package com.example.ctrl_c.model.response.userOrder

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class OrdersItem(

	@field:SerializedName("date")
	val date: String,

	@field:SerializedName("kedai")
	val kedai: Kedai,

	@field:SerializedName("id_order")
	val idOrder: Int,

	@field:SerializedName("id_kedai")
	val idKedai: Int,

	@field:SerializedName("user_id")
	val userId: Int,

	@field:SerializedName("price")
	val price: Int,

	@field:SerializedName("voucher")
	val voucher: Voucher,

	@field:SerializedName("voucher_id")
	val voucherId: Int,

	@field:SerializedName("order_detail")
	val orderDetail: List<OrderDetailItem>,

	@field:SerializedName("status")
	val status: String
) : Parcelable