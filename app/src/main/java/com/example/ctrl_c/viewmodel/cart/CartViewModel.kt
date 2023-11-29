package com.example.ctrl_c.viewmodel.cart

import androidx.lifecycle.ViewModel
import com.example.ctrl_c.data.repository.Repository

class CartViewModel(private val repository: Repository) : ViewModel() {
    fun postOrderToCart(
        userID: Int,
        productID: Int,
        amount: Int,
        warmth: Int,
        size: Int,
        sugarLvl: Int
    ) = repository.postOrderToCart(userID, productID, amount, warmth, size, sugarLvl)

    fun getOrderInCart(userID: Int) = repository.getOrderInCart(userID)

    fun deleteAllItemsInCart(userID: Int) = repository.deleteAllItemsInCart(userID)

    fun deleteItemInCart(userID: Int, productID: Int) =
        repository.deleteItemInCart(userID, productID)

    fun getAllVouchers() = repository.getAllVouchers()
}