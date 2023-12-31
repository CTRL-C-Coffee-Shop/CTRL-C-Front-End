package com.example.ctrl_c.ui.order.pickup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ctrl_c.R
import com.example.ctrl_c.databinding.ActivitySelfPickUpOrderBinding
import com.example.ctrl_c.factory.ViewModelFactory
import com.example.ctrl_c.helper.LoadingHandler
import com.example.ctrl_c.model.response.product.ProductItem
import com.example.ctrl_c.model.result.Result
import com.example.ctrl_c.ui.order.adapter.ProductAdapter
import com.example.ctrl_c.ui.order.detailed.DetailMenuActivity
import com.example.ctrl_c.viewmodel.product.ProductViewModel

class SelfPickUpOrderActivity : AppCompatActivity(), LoadingHandler {
    private lateinit var binding: ActivitySelfPickUpOrderBinding
    private lateinit var factory: ViewModelFactory
    private val viewModel: ProductViewModel by viewModels { factory }
    private val adapter = ProductAdapter()
    private var storeLocation: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelfPickUpOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        setupRecyclerView()
        setupViewModel()
        setupProductList()
        setupStoreLocation()
        setupAction()
    }

    private fun setupViewModel() {
        factory = ViewModelFactory.getInstance(binding.root.context)
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvMenu.layoutManager = layoutManager
        binding.rvMenu.adapter = adapter
    }

    private fun setupProductList() {
        viewModel.getAllProduct().observe(this) {
            it?.let { result ->
                when (result) {
                    is Result.Loading -> {
                        loadingHandler(true)
                    }

                    is Result.Error -> {
                        loadingHandler(false)
                        Toast.makeText(
                            this,
                            "Failed to fetch proudct data",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is Result.Success -> {
                        loadingHandler(false)
                        adapter.setProductData(result.data.product)
                    }
                }
            }
        }
    }

    private fun setupStoreLocation() {
        viewModel.getAllStore().observe(this) {
            it?.let { result ->
                when (result) {
                    is Result.Loading -> {
                        loadingHandler(true)
                    }

                    is Result.Error -> {
                        loadingHandler(false)
                        Toast.makeText(
                            this,
                            "Failed to fetch store's location",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is Result.Success -> {
                        loadingHandler(false)

                        //binding ke dropdown menu
                        val listLocation = result.data.kedai.map { "${it.name} - ${it.address}" }
                        val adapter = ArrayAdapter(this, R.layout.store_location_list, listLocation)

                        binding.autoComplete.apply {
                            setAdapter(adapter)
                            onItemClickListener =
                                AdapterView.OnItemClickListener { adapterView, _, position, _ ->
                                    val itemSelected = adapterView.getItemAtPosition(position)
                                    //masukin nama storenya (bisa di pass ke API dari sini)
                                    Toast.makeText(
                                        this@SelfPickUpOrderActivity,
                                        "You Choose $itemSelected for the store's location",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    storeLocation = position + 1
                                }
                        }
                    }

                }
            }
        }
    }


    private fun setupAction() {
        adapter.setOnItemClickCallback(object : ProductAdapter.OnItemClickCallBack {
            override fun onItemClicked(data: ProductItem) {
                showSelectedProduct(data)
            }
        })
    }
    private fun showSelectedProduct(data: ProductItem) {
        val intent = Intent(this, DetailMenuActivity::class.java)
        intent.putExtra("product", data)
        intent.putExtra("storeLocation", storeLocation)
        startActivity(intent)
    }

    override fun loadingHandler(isLoading: Boolean) {
        if (isLoading) {
            binding.loadingAnimation.visibility = View.VISIBLE
        } else {
            binding.loadingAnimation.visibility = View.GONE
        }
    }
}