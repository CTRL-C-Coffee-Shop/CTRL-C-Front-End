package com.example.ctrl_c.model.response.cart

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class CartItem(

	@field:SerializedName("SugarLvl")
	val sugarLvl: String,

	@field:SerializedName("Size")
	val size: String,

	@field:SerializedName("Amount")
	val amount: Int,

	@field:SerializedName("Warmth")
	val warmth: String,

	@field:SerializedName("Product")
	val product: Product
) : Parcelable