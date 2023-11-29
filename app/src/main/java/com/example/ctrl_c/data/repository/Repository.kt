package com.example.ctrl_c.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.ctrl_c.data.local.UserPreference
import com.example.ctrl_c.data.remote.ApiService
import com.example.ctrl_c.model.response.GeneralResponse
import com.example.ctrl_c.model.response.authentication.LoginResponse
import com.example.ctrl_c.model.response.cart.CartItem
import com.example.ctrl_c.model.response.cart.CartResponse
import com.example.ctrl_c.model.response.order.AdminGetOrderResponse
import com.example.ctrl_c.model.response.product.ProductResponse
import com.example.ctrl_c.model.response.stores.StoresResponse
import com.example.ctrl_c.model.response.updateOrderStatus.UpdateStatusOrderResponse
import com.example.ctrl_c.model.response.userOrder.UserOrdersResponse
import com.example.ctrl_c.model.result.Result
import com.example.ctrl_c.model.result.Result.Error
import com.example.ctrl_c.model.result.Result.Loading
import com.example.ctrl_c.model.result.Result.Success


class Repository(private val pref: UserPreference, private val apiService: ApiService) {

    fun register(
        fullname: String, email: String, password: String, accessType: Int
    ): LiveData<Result<GeneralResponse>> = liveData {
        emit(Loading)
        try {
            val response = apiService.register(
                fullname, email, password, accessType
            )
            if (response.error) {
                emit(Error(response.message))
            } else {
                emit(Success(response))
            }
        } catch (e: Exception) {
            emit(Error(e.message.toString()))
        }
    }

    fun login(email: String, password: String): LiveData<Result<LoginResponse>> = liveData {
        emit(Loading)
        try {
            val response = apiService.login(email, password)
            if (response.error) {
                emit(Error(response.message))
            } else {
                emit(Success(response))
            }
        } catch (e: Exception) {
            emit(Error(e.message.toString()))
        }
    }

    fun getProduct(): LiveData<Result<ProductResponse>> = liveData {
        emit(Loading)
        val token = pref.getToken()
        try {
            val response = apiService.getAllProduct("Bearer $token")
            if (response.error) {
                emit(Error(response.message))
            } else {
                emit(Success(response))
            }
        } catch (e: Exception) {
            emit(Error(e.message.toString()))
        }
    }

    fun getAllStores(): LiveData<Result<StoresResponse>> = liveData {
        emit(Loading)
        val token = pref.getToken()
        try {
            val response = apiService.getAllStore("Bearer $token")
            if (response.error) {
                emit(Error(response.message))
            } else {
                emit(Success(response))
            }
        } catch (e: Exception) {
            emit(Error(e.message.toString()))
        }
    }

    fun getAllOrderAdmin(): LiveData<Result<AdminGetOrderResponse>> = liveData {
        emit(Loading)
        val token = pref.getToken()
        try {
            val response = apiService.getAllOrderAdmin("Bearer $token")
            if (response.error) {
                emit(Error(response.message))
            } else {
                emit(Success(response))
            }
        } catch (e: Exception) {
            emit(Error(e.message.toString()))
        }
    }

    fun updateStatusOrderAdmin(
        status: String, orderId: Int
    ): LiveData<Result<UpdateStatusOrderResponse>> = liveData {
        emit(Loading)
        val token = pref.getToken()
        try {
            val response = apiService.updateOrderStatus("Bearer $token", status, orderId)
            if (response.error) {
                emit(Error(response.message))
            } else {
                emit(Success(response))
            }
        } catch (e: Exception) {
            emit(Error(e.message.toString()))
        }
    }

    fun getAllTransaction(id: Int): LiveData<Result<UserOrdersResponse>> = liveData {
        emit(Loading)
        val token = pref.getToken()
        try {
            val response = apiService.getAllOrder("Bearer $token", id)
            if (response.error) {
                emit(Error(response.message))
            } else {
                emit(Success(response))
            }
        } catch (e: Exception) {
            emit(Error(e.message.toString()))
        }
    }

    fun postOrderToCart(
        userID: Int,
        productID: Int,
        amount: Int,
        warmth: Int,
        size: Int,
        sugarLvl: Int
    ): LiveData<Result<GeneralResponse>> = liveData {
        emit(Loading)
        val token = pref.getToken()
        try {
            val response = apiService.postCart(
                "Bearer $token",
                userID,
                productID,
                amount,
                warmth,
                size,
                sugarLvl
            )
            if (response.error) {
                emit(Error(response.message))
            } else {
                emit(Success(response))
            }
        } catch (e: Exception) {
            emit(Error(e.message.toString()))
        }
    }

    fun getOrderInCart(userID: Int): LiveData<Result<CartResponse>> = liveData {
        emit(Loading)
        val token = pref.getToken()
        try {
            val response = apiService.getCart("Bearer $token", userID)
            if (response.error) {
                emit(Error(response.message))
            } else {
                emit(Success(response))
            }
        } catch (e: Exception) {
            emit(Error(e.message.toString()))
        }
    }

    fun deleteAllItemsInCart(userID: Int): LiveData<Result<GeneralResponse>> = liveData {
        emit(Loading)
        val token = pref.getToken()
        try {
            val response = apiService.deleteAllCart("Bearer $token", userID)
            if (response.error) {
                emit(Error(response.message))
            } else {
                emit(Success(response))
            }
        } catch (e: Exception) {
            emit(Error(e.message.toString()))
        }
    }

    fun deleteItemInCart(userID: Int, productID: Int): LiveData<Result<GeneralResponse>> =
        liveData {
            emit(Loading)
            val token = pref.getToken()
            try {
                val response = apiService.deleteCart("Bearer $token", userID, productID)
                if (response.error) {
                    emit(Error(response.message))
                } else {
                    emit(Success(response))
                }
            } catch (e: Exception) {
                emit(Error(e.message.toString()))
            }
        }

    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            preferences: UserPreference, apiService: ApiService
        ): Repository = instance ?: synchronized(this) {
            instance ?: Repository(preferences, apiService)
        }.also { instance = it }
    }
}
