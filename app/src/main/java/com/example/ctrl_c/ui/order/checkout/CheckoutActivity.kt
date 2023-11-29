package com.example.ctrl_c.ui.order.checkout

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ctrl_c.data.local.UserPreference
import com.example.ctrl_c.databinding.ActivityCheckoutBinding
import com.example.ctrl_c.factory.ViewModelFactory
import com.example.ctrl_c.helper.LoadingHandler
import com.example.ctrl_c.model.result.Result
import com.example.ctrl_c.ui.main.MainActivity
import com.example.ctrl_c.ui.order.checkout.adapter.OrderCheckoutAdapter
import com.example.ctrl_c.viewmodel.cart.CartViewModel


class CheckoutActivity : AppCompatActivity(), LoadingHandler {

    private lateinit var binding: ActivityCheckoutBinding
    private lateinit var factory: ViewModelFactory
    private val viewModel: CartViewModel by viewModels { factory }
    private val adapter = OrderCheckoutAdapter()
    private var discount = 0
    private var totalPrice = 0
    private var isRefreshing = false
    private var storeLocation: Int = 1
    private var productIdList = mutableListOf<Int>()
    private var productAmountList = mutableListOf<Int>()
    private var productWarmthList = mutableListOf<Int>()
    private var productSizeList = mutableListOf<Int>()
    private var productSugarLvlList = mutableListOf<Int>()

    private var voucherId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        swipeRefresh()
        setupViewModel()
        setupGetAllOrdersAPI()
        initRecyclerView()
        getStoreLocationFromIntent()
        setupAction()
        setTotalPrice(totalPrice)
    }

    private fun setupAction() {
        binding.apply {
            btnRemoveAllProductsFromCart.setOnClickListener {
                removeAllItemsFromCart()
            }
            buttonCreateOrder.setOnClickListener {
                createOrder()
            }
        }
        adapter.setOnItemClickListener(object : OrderCheckoutAdapter.OnItemClickListener {
            override fun onDeleteClick(position: Int) {
                val itemToDelete = adapter.currentList[position].product.id
                deleteItemFromCart(itemToDelete)
                adapter.updateCartData(position)
            }
        })
    }

    private fun swipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            isRefreshing = true
            setupGetAllOrdersAPI()
            setTotalPrice(0)
        }
    }

    private fun deleteItemFromCart(itemId: Int) {
        val pref = UserPreference(this)
        val userId = pref.getUserId()

        viewModel.deleteItemInCart(userId, itemId).observe(this) {
            it?.let { result ->
                when (result) {
                    is Result.Loading -> {
                        loadingHandler(true)
                    }

                    is Result.Error -> {
                        loadingHandler(false)
                        Toast.makeText(
                            this, "Failed to remove products from cart  ", Toast.LENGTH_SHORT
                        ).show()
                    }

                    is Result.Success -> {
                        loadingHandler(false)
                        Toast.makeText(
                            this,
                            "Product have been removed successfully from the cart ",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun getVoucher() {
        voucherId = intent.getIntExtra("idVoucher", 0)
    }

    private fun removeAllItemsFromCart() {
        val pref = UserPreference(this)
        val userId = pref.getUserId()

        viewModel.deleteAllItemsInCart(userId).observe(this) {
            it?.let { result ->
                when (result) {
                    is Result.Loading -> {
                        loadingHandler(true)
                    }

                    is Result.Error -> {
                        loadingHandler(false)
                        Toast.makeText(
                            this, "Failed to delete products from cart  ", Toast.LENGTH_SHORT
                        ).show()
                    }

                    is Result.Success -> {
                        loadingHandler(false)
                        Toast.makeText(
                            this, "All items have been removed from the cart ", Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
        adapter.clearCartData()
    }

    private fun setupGetAllOrdersAPI() {
        val pref = UserPreference(this)
        val userId = pref.getUserId()
        viewModel.getOrderInCart(userId).observe(this) {
            it?.let { result ->
                when (result) {
                    is Result.Loading -> {
                        loadingHandler(true)
                    }

                    is Result.Error -> {
                        loadingHandler(false)
                        Toast.makeText(
                            this, "Failed to fetch product data", Toast.LENGTH_SHORT
                        ).show()
                    }

                    is Result.Success -> {
                        loadingHandler(false)
                        adapter.setCartData(result.data.cart)
                        totalPrice = adapter.totalPrice
                        setTotalPrice(totalPrice)

                        val cartItems = result.data.cart
                        cartItems.forEach { cartItem ->
                            productIdList.add(cartItem.product.id)
                            productAmountList.add(cartItem.amount)
                            productWarmthList.add(cartItem.warmth.toInt())
                            productSizeList.add(cartItem.size.toInt())
                            productSugarLvlList.add(cartItem.sugarLvl.toInt())
                        }

                    }
                }
            }
        }
    }

    private fun setTotalPrice(totalPrice: Int) {
        binding.apply {
            textView18.text = "Rp. ${totalPrice}.000"
            tvTotalPrice.text = "Rp. ${totalPrice}.000"
        }
    }

    private fun getStoreLocationFromIntent() {
        storeLocation = intent.getIntExtra("storeLocation", 1)
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
    }

    private fun createOrder() {
        val pref = UserPreference(this)
        val userId = pref.getUserId()
        viewModel.createOrder(
            userId,
            storeLocation,
            1,
            totalPrice,
            productIdList,
            productAmountList,
            productWarmthList,
            productSizeList,
            productSugarLvlList
        ).observe(this) {
            it?.let { result ->
                when (result) {
                    is Result.Loading -> {
                        loadingHandler(true)
                    }

                    is Result.Error -> {
                        loadingHandler(false)
                        Toast.makeText(
                            this, "Failed to create order", Toast.LENGTH_SHORT
                        ).show()
                    }

                    is Result.Success -> {
                        loadingHandler(false)
                        Toast.makeText(
                            this, "Success creating Order! ", Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun setupViewModel() {
        factory = ViewModelFactory.getInstance(binding.root.context)
    }

    override fun loadingHandler(isLoading: Boolean) {
        if (isLoading) {
            binding.loadingAnimation.visibility = View.VISIBLE
        } else {
            binding.loadingAnimation.visibility = View.GONE
            if (isRefreshing) {
                binding.swipeRefreshLayout.isRefreshing = false
                isRefreshing = false
            }
        }
    }

}