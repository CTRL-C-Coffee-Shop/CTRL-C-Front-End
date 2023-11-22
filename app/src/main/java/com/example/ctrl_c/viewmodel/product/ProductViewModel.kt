package com.example.ctrl_c.viewmodel.product

import androidx.lifecycle.ViewModel
import com.example.ctrl_c.data.repository.Repository

class ProductViewModel(private val repository: Repository): ViewModel() {
    fun getAllProduct() = repository.getProduct()
    fun getAllStore() = repository.getAllStores()
}