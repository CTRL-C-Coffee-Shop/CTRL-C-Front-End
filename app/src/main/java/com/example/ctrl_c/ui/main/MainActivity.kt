package com.example.ctrl_c.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.ctrl_c.R
import com.example.ctrl_c.databinding.ActivityMainBinding
import com.example.ctrl_c.ui.home.HomeFragment
import com.example.ctrl_c.ui.order.checkout.CheckoutActivity
import com.example.ctrl_c.ui.profile.ProfileFragment
import com.example.ctrl_c.ui.transactions.TransactionPageFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        replaceFragment(HomeFragment())
        setupAction()
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()

    }

    private fun setupAction() {
        binding.bottomNavigation.setOnItemReselectedListener { menu ->
            when (menu.itemId) {

                R.id.home_bottom_nav -> replaceFragment(HomeFragment())
                R.id.transaction_bottom_nav -> replaceFragment(TransactionPageFragment())
                R.id.profile_bottom_nav -> replaceFragment(ProfileFragment())
                R.id.cart_bottom_nav -> {
                    val intent = Intent(this, CheckoutActivity::class.java)
                    startActivity(intent)
                }

                else -> {

                }
            }
            true
        }
    }
}