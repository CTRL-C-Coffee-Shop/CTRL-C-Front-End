package com.example.ctrl_c.ui.transactions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ctrl_c.data.local.UserPreference
import com.example.ctrl_c.databinding.FragmentTransactionPageBinding
import com.example.ctrl_c.factory.ViewModelFactory
import com.example.ctrl_c.helper.LoadingHandler
import com.example.ctrl_c.model.result.Result
import com.example.ctrl_c.ui.transactions.adapter.UserOrdersAdapter
import com.example.ctrl_c.viewmodel.transaction.TransactionViewModel

class TransactionPageFragment : Fragment(), LoadingHandler {

    private var _binding: FragmentTransactionPageBinding? = null
    private val binding get() = _binding!!
    private lateinit var factory: ViewModelFactory
    private val viewModel: TransactionViewModel by viewModels { factory }
    private val adapter = UserOrdersAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTransactionPageBinding.inflate(LayoutInflater.from(requireActivity()))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        setupViewModel()
        getAllTransaction()
    }
    private fun initRecyclerView(){
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvTransaction.layoutManager = layoutManager
        binding.rvTransaction.adapter = adapter

    }
    private fun setupViewModel() {
        factory = ViewModelFactory.getInstance(binding.root.context)
    }
    private fun getAllTransaction() {
        val userId = getUserIdFromPreference()
        viewModel.getAllTransaction(userId).observe(requireActivity()) {
            it?.let { result ->
                when (result) {
                    is Result.Loading -> {
                        loadingHandler(true)
                    }

                    is Result.Error -> {
                        loadingHandler(false)
                        Toast.makeText(
                            requireContext(),
                            "Failed to fetch orders data",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    is Result.Success -> {
                        loadingHandler(false)
                        adapter.setOrdersList(result.data.orders)
                    }
                }
            }
        }
    }
    private fun getUserIdFromPreference(): Int {
        val preference = UserPreference(requireContext())
        return preference.getUserId()
    }
    override fun loadingHandler(isLoading: Boolean) {
        if (isLoading) {
            binding.loadingAnimation.visibility = View.VISIBLE
        } else {
            binding.loadingAnimation.visibility = View.GONE
        }
    }
}