package com.example.ctrl_c.data.remote

import com.example.ctrl_c.model.response.GeneralResponse
import com.example.ctrl_c.model.response.authentication.LoginResponse
import com.example.ctrl_c.model.response.order.AdminGetOrderResponse
import com.example.ctrl_c.model.response.product.ProductResponse
import com.example.ctrl_c.model.response.stores.StoresResponse
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
        @Header("Authorizaton") token: String
    ): AdminGetOrderResponse
}