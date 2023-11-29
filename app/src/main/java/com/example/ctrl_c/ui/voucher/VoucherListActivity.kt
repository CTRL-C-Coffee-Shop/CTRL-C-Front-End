package com.example.ctrl_c.ui.voucher

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ctrl_c.databinding.ActivityVoucherListBinding
import com.example.ctrl_c.factory.ViewModelFactory
import com.example.ctrl_c.helper.LoadingHandler
import com.example.ctrl_c.model.response.voucher.VouchersItem
import com.example.ctrl_c.model.result.Result
import com.example.ctrl_c.ui.voucher.adapter.VoucherAdapter
import com.example.ctrl_c.viewmodel.voucher.VoucherViewModel

class VoucherListActivity : AppCompatActivity(), LoadingHandler {
    private lateinit var binding: ActivityVoucherListBinding
    private lateinit var factory: ViewModelFactory
    private val viewModel: VoucherViewModel by viewModels { factory }
    private val adapter = VoucherAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVoucherListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setupRecyclerView()
        setupViewModel()
        setupAction()
        setupVouchersList()
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvVouchersList.layoutManager = layoutManager
        binding.rvVouchersList.adapter = adapter
    }

    private fun setupVouchersList() {
        viewModel.getAllVouchers().observe(this) {
            it?.let { result ->
                when (result) {
                    is Result.Loading -> {
                        loadingHandler(true)
                    }

                    is Result.Error -> {
                        loadingHandler(false)
                        Toast.makeText(
                            this,
                            "Failed to fetch vouchers data",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is Result.Success -> {
                        loadingHandler(false)
                        adapter.setVoucherData(result.data.vouchers)
                    }
                }
            }
        }
    }
    private fun setupAction() {
        adapter.setOnItemClickCallback(object : VoucherAdapter.OnItemClickCallBack {
            override fun onItemClicked(data: VouchersItem) {
                showSelectedVoucher(data)
            }
        })

    }

    private fun setupViewModel() {
        factory = ViewModelFactory.getInstance(binding.root.context)
    }

    private fun showSelectedVoucher(data : VouchersItem) {
        val intent = Intent(this, VoucherDetailActivity::class.java)
        intent.putExtra("voucher", data)
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