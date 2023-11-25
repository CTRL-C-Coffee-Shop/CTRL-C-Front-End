package com.example.ctrl_c.ui.order.detailed

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.ctrl_c.R
import com.example.ctrl_c.databinding.ActivityDetailMenuBinding
import com.example.ctrl_c.helper.LoadingHandler
import com.example.ctrl_c.model.response.product.ProductItem

class DetailMenuActivity : AppCompatActivity(), LoadingHandler {

    private lateinit var binding: ActivityDetailMenuBinding

    // buat harga dan jumlah barang
    private var productAmount: Int = 0
    private var productPrice: Int = 0

    //buat radio button
    private var drinkType: String = ""
    private var cupSize: String = ""
    private var sweetnessLevel: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setupDetailedProduct()
        updateAmount()
        updatePrice()
        setupAction()
    }

    private fun setupDetailedProduct() {
        val product = intent.getParcelableExtra<ProductItem>("product")
        if (product != null) {
            binding.apply {
                textView4.text = product.name
                textView5.text = product.description
                textView6.text = "Rp. ${product.price}.000"
                Glide.with(this@DetailMenuActivity)
                    .load(product.url)
                    .error(R.drawable.default_menu)
                    .into(imageView)
            }
            productPrice = product.price
        }
    }

    private fun increaseAmount() {
        productAmount++
    }

    private fun decreaseAmount() {
        if (productAmount > 0) {
            productAmount--
        }
    }

    private fun updateAmount() {
        binding.textView12.text = productAmount.toString()
    }

    private fun updatePrice() {
        binding.button.text = "Add Rp. ${productPrice * productAmount}.000"
    }

    private fun setupAction() {
        binding.apply {
            materialButton9.setOnClickListener {
                decreaseAmount()
                updateAmount()
                updatePrice()
            }
            materialButton10.setOnClickListener {
                increaseAmount()
                updateAmount()
                updatePrice()
            }

            radioGroup.setOnCheckedChangeListener { group, checkedId ->
                when (checkedId) {
                    R.id.radio_regular_cup -> {
                        cupSize = radioRegularCup.text.toString()
                    }

                    R.id.radio_large_cup -> {
                        cupSize = radioRegularCup.text.toString()
                    }
                }
            }
            radioGroupSweetness.setOnCheckedChangeListener { group, checkedId ->
                when (checkedId) {
                    R.id.radio_normal_sweet -> {
                        sweetnessLevel = radioNormalSweet.text.toString()
                    }

                    R.id.radio_less_sweet -> {
                        sweetnessLevel = radioLessSweet.text.toString()
                    }
                }

            }
            radioGroupWarmth.setOnCheckedChangeListener { group, checkedId ->
                when (checkedId) {
                    R.id.radio_hot -> {
                        drinkType = radioHot.text.toString()
                    }

                    R.id.radio_iced -> {
                        drinkType = radioIced.text.toString()
                    }
                }

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