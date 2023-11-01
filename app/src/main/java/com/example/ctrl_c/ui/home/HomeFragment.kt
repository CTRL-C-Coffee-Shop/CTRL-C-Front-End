package com.example.ctrl_c.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ctrl_c.databinding.FragmentHomeBinding
import com.example.ctrl_c.ui.order.pickup.SelfPickUpOrderActivity

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(LayoutInflater.from(requireActivity()))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAction()
    }

    private fun setupAction() {
        binding.pickUp.setOnClickListener {
            navigateToSelfPickUpOrderActivity()
        }
    }

    private fun navigateToSelfPickUpOrderActivity() {
        val intent = Intent(activity, SelfPickUpOrderActivity::class.java)
        startActivity(intent)
    }
    companion object {

    }
}