package com.example.ctrl_c.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ctrl_c.data.repository.Repository
import com.example.ctrl_c.di.Injection
import com.example.ctrl_c.viewmodel.authetntication.login.LoginViewModel
import com.example.ctrl_c.viewmodel.authetntication.register.RegisterViewModel
import com.example.ctrl_c.viewmodel.cart.CartViewModel
import com.example.ctrl_c.viewmodel.order.AdminOrderViewModel
import com.example.ctrl_c.viewmodel.product.ProductViewModel
import com.example.ctrl_c.viewmodel.transaction.TransactionViewModel
import com.example.ctrl_c.viewmodel.voucher.VoucherViewModel

class ViewModelFactory(private val repository: Repository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(repository) as T
            }

            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }

            modelClass.isAssignableFrom(ProductViewModel::class.java) -> {
                ProductViewModel(repository) as T
            }

            modelClass.isAssignableFrom(AdminOrderViewModel::class.java) -> {
                AdminOrderViewModel(repository) as T
            }

            modelClass.isAssignableFrom(TransactionViewModel::class.java) -> {
                TransactionViewModel(repository) as T
            }

            modelClass.isAssignableFrom(CartViewModel::class.java) -> {
                CartViewModel(repository) as T
            }

            modelClass.isAssignableFrom(VoucherViewModel::class.java) -> {
                VoucherViewModel(repository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory {
            return instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
        }
    }

}