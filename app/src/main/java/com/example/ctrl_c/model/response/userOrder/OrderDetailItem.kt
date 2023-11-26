package com.example.ctrl_c.model.response.userOrder

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class OrderDetailItem(

	@field:SerializedName("id_order")
	val idOrder: Int,

	@field:SerializedName("amount")
	val amount: Int,

	@field:SerializedName("id_product")
	val idProduct: Int,

	@field:SerializedName("Product")
	val product: Product
) : Parcelable