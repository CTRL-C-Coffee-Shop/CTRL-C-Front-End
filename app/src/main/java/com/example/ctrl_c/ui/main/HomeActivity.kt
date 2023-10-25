package com.example.ctrl_c.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ctrl_c.databinding.ActivityHomeBinding
import com.example.ctrl_c.ui.order.pickup.SelfPickUpOrderActivity

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setupAction()
    }


    private fun setupAction() {
        binding.pickUp.setOnClickListener {
            navigateToSelfPickUpOrderActivity()
        }
    }

    private fun navigateToSelfPickUpOrderActivity() {
        val intent = Intent(this@HomeActivity, SelfPickUpOrderActivity::class.java)
        startActivity(intent)
    }
}