package com.example.ctrl_c.ui.payment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.ctrl_c.databinding.ActivityPaymentBinding
import com.example.ctrl_c.ui.main.MainActivity

class PaymentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPaymentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setupAnimation()
    }


    private fun setupAnimation() {
        Handler().postDelayed({
            navigateToMainActivity()
            finish()
        }, 1800)
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this@PaymentActivity, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

}