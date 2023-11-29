package com.example.ctrl_c.viewmodel.voucher

import androidx.lifecycle.ViewModel
import com.example.ctrl_c.data.repository.Repository

class VoucherViewModel(private val repository: Repository) : ViewModel() {
    fun getAllVouchers() = repository.getAllVouchers()
}