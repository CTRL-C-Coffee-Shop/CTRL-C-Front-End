package com.example.ctrl_c.ui.order.detailed

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ctrl_c.databinding.ActivityDetailMenuBinding
import com.example.ctrl_c.helper.LoadingHandler

class DetailMenuActivity : AppCompatActivity(), LoadingHandler {

    private lateinit var binding: ActivityDetailMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
    }

    override fun loadingHandler(isLoading: Boolean) {
        if (isLoading) {
            binding.loadingAnimation.visibility = View.VISIBLE
        } else {
            binding.loadingAnimation.visibility = View.GONE
        }
    }
}