package com.example.ctrl_c.ui.order.detailed

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.ctrl_c.R
import com.example.ctrl_c.databinding.ActivityDetailMenuBinding
import com.example.ctrl_c.helper.LoadingHandler
import com.example.ctrl_c.model.response.product.ProductItem

class DetailMenuActivity : AppCompatActivity(), LoadingHandler {

    private lateinit var binding: ActivityDetailMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        setupDetailedProduct()
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

    override fun loadingHandler(isLoading: Boolean) {
        if (isLoading) {
            binding.loadingAnimation.visibility = View.VISIBLE
        } else {
            binding.loadingAnimation.visibility = View.GONE
        }
    }
}