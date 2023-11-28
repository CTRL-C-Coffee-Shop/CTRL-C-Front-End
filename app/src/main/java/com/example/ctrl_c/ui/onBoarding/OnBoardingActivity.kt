package com.example.ctrl_c.ui.onBoarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit
import com.example.ctrl_c.R
import com.example.ctrl_c.data.local.UserPreference
import com.example.ctrl_c.databinding.ItemOnBoardingBinding
import com.example.ctrl_c.helper.ReminderWorker
import com.example.ctrl_c.ui.admin.AdminPageActivity
import com.example.ctrl_c.ui.authentication.login.LoginActivity
import com.example.ctrl_c.ui.main.MainActivity
import com.example.ctrl_c.ui.onBoarding.adapter.OnBoardingAdapter
import com.google.android.material.tabs.TabLayoutMediator


class OnBoardingActivity : AppCompatActivity() {
    private lateinit var binding: ItemOnBoardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ItemOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        checkToken()
        setupAction()
    }

    private fun setupAction() {
        binding.viewPager2.adapter = OnBoardingAdapter()
        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, poisiton ->
        }.attach()

        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                if (position == MAX_STEP - 1) {
                    binding.btnNext.text = getString(R.string.get_started_title)
                    binding.btnNext.contentDescription = getString(R.string.get_started_title)
                } else {
                    binding.btnNext.text = getString(R.string.next_title)
                    binding.btnNext.contentDescription = getString(R.string.next_title)
                }
            }
        })

        binding.btnNext.setOnClickListener {
            if (binding.btnNext.text.toString() == getString(R.string.get_started_title)) {
                navigateToLoginActivity()
            } else {
                // to change current page - on click "Next BUTTON"
                val current = (binding.viewPager2.currentItem) + 1
                binding.viewPager2.currentItem = current

                // to update button text
                if (current >= MAX_STEP - 1) {
                    binding.btnNext.text = getString(R.string.get_started_title)
                    binding.btnNext.contentDescription = getString(R.string.get_started_title)
                } else {
                    binding.btnNext.text = getString(R.string.next_title)
                    binding.btnNext.contentDescription = getString(R.string.next_title)
                }
            }
        }

        binding.btnSkip.setOnClickListener {
            navigateToLoginActivity()
        }
    }

    private fun navigateToLoginActivity() {
        //intent to login activity.
        val intent = Intent(this@OnBoardingActivity, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    private fun navigateToMainActivity() {
        //intent to main activity
        val intent = Intent(this@OnBoardingActivity, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    private fun checkToken() {
        //checking if user already logged in or not.
        val pref = UserPreference(this)
        val token = pref.getToken()
        val userTyoe = pref.getUserType()
        if (token != null) {
            if (!userTyoe) {
                startApplicationReminder()
                navigateToMainActivity()
            } else {
                navigateToAdminActivity()
            }
        }
    }

    private fun navigateToAdminActivity() {
        val intent = Intent(this@OnBoardingActivity, AdminPageActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    private fun startApplicationReminder() {
        val workRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
            //Changeable Timestamp
            .setInitialDelay(5, TimeUnit.SECONDS)
            .build()

        WorkManager.getInstance(this).enqueue(workRequest)
    }

    companion object {
        const val MAX_STEP = 2
    }

}


