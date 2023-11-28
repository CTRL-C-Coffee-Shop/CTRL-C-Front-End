package com.example.ctrl_c.ui.main

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.ctrl_c.R
import com.example.ctrl_c.databinding.ActivityMainBinding
import com.example.ctrl_c.ui.home.HomeFragment
import com.example.ctrl_c.ui.profile.ProfileFragment
import com.example.ctrl_c.ui.transactions.TransactionPageFragment
import androidx.work.Constraints
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.ctrl_c.helper.ReminderWorker
import java.util.concurrent.TimeUnit

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

    override fun onDestroy() {
        super.onDestroy()
        updateLastActiveTime(applicationContext)
        scheduleInactivityCheck() // Memindahkan pemanggilan ke onDestroy
    }

    private fun scheduleInactivityCheck() {
        val constraints = Constraints.Builder()
            .setRequiresDeviceIdle(true) // Set to true if you want the task to run when the device is idle
            .build()

        val inactivityWork = PeriodicWorkRequestBuilder<ReminderWorker>(
            repeatInterval = 1, // Repeat interval in minutes
            TimeUnit.SECONDS
        )
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueue(inactivityWork)
    }
    fun updateLastActiveTime(context: Context) {
        val sharedPreferences = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        val currentTime = System.currentTimeMillis()

        sharedPreferences.edit().putLong("lastActiveTime", currentTime).apply() // Update last active time in SharedPreferences
    }

    private fun setupAction() {
        binding.bottomNavigation.setOnItemReselectedListener { menu ->
            when (menu.itemId) {

                R.id.home_bottom_nav -> replaceFragment(HomeFragment())
                R.id.transaction_bottom_nav -> replaceFragment(TransactionPageFragment())
                R.id.profile_bottom_nav -> replaceFragment(ProfileFragment())

                else -> {

                }
            }
            true
        }
    }
}