package com.example.ctrl_c.ui.admin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ctrl_c.data.local.UserPreference
import com.example.ctrl_c.databinding.ActivityAdminPageBinding
import com.example.ctrl_c.factory.ViewModelFactory
import com.example.ctrl_c.helper.LoadingHandler
import com.example.ctrl_c.model.response.order.OrdersItem
import com.example.ctrl_c.model.result.Result
import com.example.ctrl_c.ui.admin.adapter.AdminPageAdapter
import com.example.ctrl_c.ui.authentication.login.LoginActivity
import com.example.ctrl_c.viewmodel.order.AdminOrderViewModel

class AdminPageActivity : AppCompatActivity(), LoadingHandler {
    private lateinit var binding: ActivityAdminPageBinding
    private lateinit var factory: ViewModelFactory
    private val viewModel: AdminOrderViewModel by viewModels { factory }
    private val adapter = AdminPageAdapter()
    private var isRefreshing = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        swipeRefresh()
        setupViewModel()
        setupOrderList()
        initRecycleView()
        setupAction()
    }

    private fun setupViewModel() {
        factory = ViewModelFactory.getInstance(binding.root.context)
    }

    private fun initRecycleView() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvOrder.layoutManager = layoutManager
        binding.rvOrder.adapter = adapter
    }

    private fun setupAction() {
        logout()
        adapter.setOnItemClickCallback(object : AdminPageAdapter.OnItemClickCallBack {
            override fun onItemClicked(data: OrdersItem) {
                updateStatusOrder(data.idOrder)
            }

        })
    }

    private fun setupOrderList() {
        viewModel.getALlOrderAdmin().observe(this) {
            it?.let { result ->
                when (result) {
                    is Result.Loading -> {
                        loadingHandler(true)
                    }

                    is Result.Error -> {
                        loadingHandler(false)
                        Toast.makeText(
                            this,
                            "Failed to fetch orders data",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is Result.Success -> {
                        loadingHandler(false)
                        adapter.setOrderList(result.data.orders)
                    }
                }
            }
        }
    }

    private fun updateStatusOrder(orderId: Int) {
        viewModel.updateStatusOrders("Completed", orderId).observe(this) {
            it?.let { result ->
                when (result) {
                    is Result.Loading -> {
                        loadingHandler(true)
                    }

                    is Result.Error -> {
                        loadingHandler(false)
                        Toast.makeText(
                            this,
                            "Failed to update order status!",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.d("INI ERORRRRR", "updateStatusOrder: $result ")
                    }

                    is Result.Success -> {
                        loadingHandler(false)
                        Toast.makeText(this, "Success updating order status!", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }

    private fun swipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            isRefreshing = true
            setupOrderList()
        }
    }


    private fun logout() {
        binding.button2.setOnClickListener {
            val pref = UserPreference(this)
            pref.clearPreferences()
            navigateToLoginActivity()
        }
    }

    private fun navigateToLoginActivity() {
        val intent = Intent(this@AdminPageActivity, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    override fun loadingHandler(isLoading: Boolean) {
        if (isLoading) {
            binding.loadingAnimation.visibility = View.VISIBLE
        } else {
            binding.loadingAnimation.visibility = View.GONE
            if (isRefreshing) {
                binding.swipeRefresh.isRefreshing = false
                isRefreshing = false
            }
        }
    }
}