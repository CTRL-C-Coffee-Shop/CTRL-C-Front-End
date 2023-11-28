package com.example.ctrl_c.model.response.cart

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class CartResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("cart")
    val cart: List<CartItem>,

    @field:SerializedName("message")
    val message: String
) : Parcelable