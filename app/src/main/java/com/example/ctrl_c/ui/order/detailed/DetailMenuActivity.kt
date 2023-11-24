package com.example.ctrl_c.ui.order.detailed

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.ctrl_c.R
import com.example.ctrl_c.databinding.ActivityDetailMenuBinding
import com.example.ctrl_c.helper.LoadingHandler
import com.example.ctrl_c.model.response.product.ProductItem

class DetailMenuActivity : AppCompatActivity(), LoadingHandler {

    private lateinit var binding: ActivityDetailMenuBinding
    var productAmmount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        setupDetailedProduct()
        updateAmmount()
        setupAction()
    }

    private fun setupDetailedProduct() {
        val product = intent.getParcelableExtra<ProductItem>("product")
        if (product != null) {
            binding.apply {
                textView4.text = product.name
                textView5.text = product.description
                textView6.text = "Rp. ${product.price}"
                Glide.with(this@DetailMenuActivity)
                    .load(product.url)
                    .error(R.drawable.default_menu)
                    .into(imageView2)
            }
        }
    }

    private fun increaseAmmount() {
        productAmmount++
    }

    private fun decreaseAmmount() {
        if (productAmmount > 0) {
            productAmmount--
        }
    }

    private fun updateAmmount() {
        binding.textView12.text = productAmmount.toString()
    }

    private fun setupAction() {
        binding.apply {
            materialButton9.setOnClickListener {
                decreaseAmmount()
                updateAmmount()
            }
            materialButton10.setOnClickListener {
                increaseAmmount()
                updateAmmount()
            }
        }
    }

    override fun loadingHandler(isLoading: Boolean) {
        if (isLoading) {
            binding.loadingAnimation.visibility = View.VISIBLE
        } else {
            binding.loadingAnimation.visibility = View.GONE
        }
    }
}