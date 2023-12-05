package com.example.ctrl_c.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.ctrl_c.data.local.UserPreference
import com.example.ctrl_c.databinding.FragmentHomeBinding
import com.example.ctrl_c.ui.home.adapter.HomeAdapter
import com.example.ctrl_c.ui.order.delivery.DeliveryOrderActivity
import com.example.ctrl_c.ui.order.pickup.SelfPickUpOrderActivity
import com.google.android.material.tabs.TabLayoutMediator

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
        setupUserName()
        setupAction()
    }

    private fun setupAction() {
        binding.viewPagerHome.adapter = HomeAdapter()
        TabLayoutMediator(binding.tabLayout, binding.viewPagerHome) { tab, position ->
        }.attach()

        binding.viewPagerHome.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            })

        binding.pickUp.setOnClickListener {
            navigateToSelfPickUpOrderActivity()
        }
        binding.delivery.setOnClickListener {
            navigateToDeliveryOrderActivity()
        }

    }

    private fun setupUserName() {
        val pref = UserPreference(requireContext())
        val fullName = pref.getUserFullName()
        binding.textView2.text = fullName.toString()
    }

    private fun navigateToSelfPickUpOrderActivity() {
        val intent = Intent(activity, SelfPickUpOrderActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToDeliveryOrderActivity() {
        val intent = Intent(activity, DeliveryOrderActivity::class.java)
        startActivity(intent)
    }

    companion object {
        const val MAX_PAGE = 3
    }
}