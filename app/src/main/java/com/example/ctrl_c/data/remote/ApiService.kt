package com.example.ctrl_c.data.remote

import com.example.ctrl_c.model.response.GeneralResponse
import com.example.ctrl_c.model.response.authentication.LoginResponse
import com.example.ctrl_c.model.response.cart.CartResponse
import com.example.ctrl_c.model.response.order.AdminGetOrderResponse
import com.example.ctrl_c.model.response.product.ProductResponse
import com.example.ctrl_c.model.response.stores.StoresResponse
import com.example.ctrl_c.model.response.updateOrderStatus.UpdateStatusOrderResponse
import com.example.ctrl_c.model.response.userOrder.UserOrdersResponse
import com.example.ctrl_c.model.response.voucher.VouchersResponse
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("/register")
    suspend fun register(
        @Field("FullName") name: String,
        @Field("Email") email: String,
        @Field("Password") password: String,
        @Field("AccessType") accessType: Int,
    ): GeneralResponse

    @FormUrlEncoded
    @POST("/userlogin")
    suspend fun login(
        @Field("Email") email: String,
        @Field("Password") password: String,
    ): LoginResponse

    @GET("/product")
    suspend fun getAllProduct(
        @Header("Authorization") token: String
    ): ProductResponse

    @GET("/getkedai")
    suspend fun getAllStore(
        @Header("Authorization") token: String
    ): StoresResponse

    @GET("/getorderadmin")
    suspend fun getAllOrderAdmin(
        @Header("Authorization") token: String
    ): AdminGetOrderResponse

    @FormUrlEncoded
    @POST("/updateorderstatus")
    suspend fun updateOrderStatus(
        @Header("Authorization") token: String,
        @Field("Status") status: String,
        @Field("Order_Id") orderId: Int
    ): UpdateStatusOrderResponse

    @FormUrlEncoded
    @POST("/getorder")
    suspend fun getAllOrder(
        @Header("Authorization") token: String,
        @Field("id") id: Int
    ): UserOrdersResponse

    @FormUrlEncoded
    @POST("/postcart")
    suspend fun postCart(
        @Header("Authorization") token: String,
        @Field("UserID") userID: Int,
        @Field("ProdID") productID: Int,
        @Field("Amount") amount: Int,
        @Field("Warmnth") warmth: Int,
        @Field("Size") size: Int,
        @Field("SugarLvl") sugarLvl: Int,
    ): GeneralResponse

    @FormUrlEncoded
    @POST("/getcart")
    suspend fun getCart(
        @Header("Authorization") token: String,
        @Field("UserID") userID: Int
    ): CartResponse

    @FormUrlEncoded
    @POST("/deletecart")
    suspend fun deleteCart(
        @Header("Authorization") token: String,
        @Field("UserID") userID: Int,
        @Field("ProdID") prodID: Int
    ): GeneralResponse

    @FormUrlEncoded
    @POST("/deleteallcart")
    suspend fun deleteAllCart(
        @Header("Authorization") token: String,
        @Field("UserID") userID: Int
    ): GeneralResponse

    @FormUrlEncoded
    @POST("/createorder")
    suspend fun createOrder(
        @Header("Authorization") token: String,
        @Field("id") userID: Int,
        @Field("id_kedai") storeID: Int,
        @Field("id_voucher") voucherID: Int,
        @Field("total") totalPrice: Int,
        @Field("productID[]") productID: List<Int>,
        @Field("amount[]") productAmount: List<Int>,
        @Field("warmth[]") productWarmth: List<Int>,
        @Field("size[]") productSize: List<Int>,
        @Field("sugarLvl[]") productSugarLvl: List<Int>
    ): GeneralResponse


    @GET("/getvoucher")
    suspend fun getAllVouchers(
        @Header("Authorization") token: String
    ): VouchersResponse
}