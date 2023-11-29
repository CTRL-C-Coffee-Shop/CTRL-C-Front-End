package com.example.ctrl_c.ui.voucher

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ctrl_c.databinding.ActivityVoucherDetailBinding
import com.example.ctrl_c.model.response.voucher.VouchersItem
import com.example.ctrl_c.ui.order.checkout.CheckoutActivity


class VoucherDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVoucherDetailBinding
    private var idVoucher = 0
    private var discountPercentage = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVoucherDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setupDetailedVoucher()
        setupAction()
    }

    private fun setupAction() {
        binding.apply {
            btnUseVoucher.setOnClickListener{
                val intent = Intent(this@VoucherDetailActivity, CheckoutActivity::class.java)
                intent.putExtra("idVoucher", idVoucher)
                startActivity(intent)
            }
        }
    }

    private fun setupDetailedVoucher() {
        val voucher = intent.getParcelableExtra<VouchersItem>("voucher")
        if (voucher != null) {
            binding.apply {
                tvDetailedVoucherName.text = voucher.name
                tvDetailedVoucherDesc.text = voucher.description
                idVoucher = voucher.id
                discountPercentage = voucher.discount
//                Glide.with(this@VoucherDetailActivity)
//                    .load(voucher.url)
//                    .error(R.drawable.default_menu)
//                    .into(ivDetailedVoucherImage)
            }
        }
    }
}