package com.example.ctrl_c.ui.order.detailed

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.ctrl_c.R
import com.example.ctrl_c.data.local.UserPreference
import com.example.ctrl_c.databinding.ActivityDetailMenuBinding
import com.example.ctrl_c.factory.ViewModelFactory
import com.example.ctrl_c.helper.LoadingHandler
import com.example.ctrl_c.model.response.product.ProductItem
import com.example.ctrl_c.model.result.Result
import com.example.ctrl_c.ui.order.checkout.CheckoutActivity
import com.example.ctrl_c.viewmodel.cart.CartViewModel

class DetailMenuActivity : AppCompatActivity(), LoadingHandler {

    private lateinit var binding: ActivityDetailMenuBinding

    private lateinit var factory: ViewModelFactory
    private val viewModel: CartViewModel by viewModels { factory }

    // buat harga dan jumlah barang
    private var productAmount: Int = 0
    private var productPrice: Int = 0

    private var productId: Int = 0

    //buat radio button
    private var drinkType: Int = 0
    private var cupSize: Int = 0
    private var sweetnessLevel: Int = 0
    private var storeLocation: String? = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setupDetailedProduct()
        setupViewModel()
        updateAmount()
        updatePrice()
        getStoreLocationFromIntent()
        setupAction()

    }


    private fun getStoreLocationFromIntent() {
        storeLocation = intent.getStringExtra("storeLocation")
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
            productId = product.id
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
                        cupSize = 1
                    }

                    R.id.radio_large_cup -> {
                        cupSize = 2
                    }
                }
            }
            radioGroupSweetness.setOnCheckedChangeListener { group, checkedId ->
                when (checkedId) {
                    R.id.radio_normal_sweet -> {
                        sweetnessLevel = 1
                    }

                    R.id.radio_less_sweet -> {
                        sweetnessLevel = 2
                    }
                }

            }
            radioGroupWarmth.setOnCheckedChangeListener { group, checkedId ->
                when (checkedId) {
                    R.id.radio_hot -> {
                        drinkType = 1
                    }

                    R.id.radio_iced -> {
                        drinkType = 2
                    }
                }

            }

            button.setOnClickListener {
                postOrderToCart()
            }

        }
    }

    private fun postOrderToCart() {
        val pref = UserPreference(this)
        val userId = pref.getUserId()

        viewModel.postOrderToCart(
            userId,
            productId,
            productAmount,
            drinkType,
            cupSize,
            sweetnessLevel
        ).observe(this) {
            it?.let { result ->
                when (result) {
                    is Result.Loading -> {
                        loadingHandler(true)
                    }

                    is Result.Error -> {
                        loadingHandler(false)
                        Toast.makeText(
                            this,
                            "Failed to post order to cart",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is Result.Success -> {
                        loadingHandler(false)
                        Toast.makeText(this, "Success to post order to cart", Toast.LENGTH_SHORT)
                            .show()
                        navigateToCheckOutPage()
                    }
                }
            }
        }
    }

    private fun navigateToCheckOutPage() {
        val intent = Intent(this@DetailMenuActivity, CheckoutActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    private fun setupViewModel() {
        factory = ViewModelFactory.getInstance(binding.root.context)
    }

    override fun loadingHandler(isLoading: Boolean) {
        if (isLoading) {
            binding.loadingAnimation.visibility = View.VISIBLE
        } else {
            binding.loadingAnimation.visibility = View.GONE
        }
    }
}