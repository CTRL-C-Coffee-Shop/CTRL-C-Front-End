package com.example.ctrl_c.viewmodel.authetntication.register

import androidx.lifecycle.ViewModel
import com.example.ctrl_c.data.repository.Repository

class RegisterViewModel(private val repository: Repository) : ViewModel() {
    fun register(fullname: String, email: String, password: String, accessType: Int) =
        repository.register(fullname, email, password, accessType)
}