package com.example.ctrl_c.model.response.userOrder

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrdersItem(

    @field:SerializedName("date")
    val date: String,

    @field:SerializedName("date_only")
    val dateOnly: String,

    @field:SerializedName("time_only")
    val timeOnly: String,

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