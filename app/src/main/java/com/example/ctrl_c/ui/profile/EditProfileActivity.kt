package com.example.ctrl_c.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ctrl_c.R
import com.example.ctrl_c.databinding.ActivityEditProfileBinding

class EditProfileActivity : AppCompatActivity() {
//    Check
private lateinit var binding: ActivityEditProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}