package com.example.ctrl_c.ui.voucher

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ctrl_c.R
import com.example.ctrl_c.databinding.ActivityVoucherDetailBinding

class VoucherDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVoucherDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVoucherDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}