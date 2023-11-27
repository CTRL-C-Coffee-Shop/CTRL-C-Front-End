package com.example.ctrl_c.viewmodel.order

import androidx.lifecycle.ViewModel
import com.example.ctrl_c.data.repository.Repository

class AdminOrderViewModel(private val repository: Repository) : ViewModel() {
    fun getALlOrderAdmin() = repository.getAllOrderAdmin()
    fun updateStatusOrders(status: String, orderId: Int) =
        repository.updateStatusOrderAdmin(status, orderId)
}