package com.example.ctrl_c.model.response.order

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AdminGetOrderResponse(

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("orders")
    val orders: List<OrdersItem>,

    @field:SerializedName("message")
    val message: String
) : Parcelable