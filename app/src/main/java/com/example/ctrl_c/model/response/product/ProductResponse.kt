package com.example.ctrl_c.model.response.product

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class ProductResponse(

	@field:SerializedName("product")
	val product: List<ProductItem>,

	@field:SerializedName("message")
	val message: String,

    @field:SerializedName("error")
    val error: Boolean,

) : Parcelable