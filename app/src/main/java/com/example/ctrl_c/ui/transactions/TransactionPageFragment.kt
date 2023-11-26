package com.example.ctrl_c.ui.transactions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ctrl_c.R
import com.example.ctrl_c.databinding.FragmentHomeBinding
import com.example.ctrl_c.databinding.FragmentTransactionPageBinding

class TransactionPageFragment : Fragment() {

    private var _binding: FragmentTransactionPageBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTransactionPageBinding.inflate(LayoutInflater.from(requireActivity()))
        return binding.root
    }
}