package com.example.ctrl_c.viewmodel.transaction

import androidx.lifecycle.ViewModel
import com.example.ctrl_c.data.repository.Repository

class TransactionViewModel(private val repository: Repository) : ViewModel() {
    fun getAllTransaction(id: Int) = repository.getAllTransaction(id)
}