package com.example.ctrl_c.ui.authentication.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.ctrl_c.databinding.ActivityLoginBinding
import com.example.ctrl_c.ui.authentication.register.RegisterActivity


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        playAnimation()
        setupAction()
    }

    private fun setupAction() {
        binding.btnSignUp.setOnClickListener{
            navigateToRegisterActivity()
        }
    }

    private fun navigateToRegisterActivity() {
        //intent to Register activity.
        val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun playAnimation() {
        val image =
            ObjectAnimator.ofFloat(binding.ivLoginIllustration, View.ALPHA, 1f).setDuration(500)
        val title = ObjectAnimator.ofFloat(binding.tvLogin, View.ALPHA, 1f).setDuration(500)
        val loginMessage =
            ObjectAnimator.ofFloat(binding.tvLoginMsg, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(
                image,
                title,
                loginMessage
            )
            startDelay = 300
        }.start()

        ObjectAnimator.ofFloat(binding.ivLoginIllustration, View.TRANSLATION_X, -50f, 50f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()
        ObjectAnimator.ofFloat(binding.tvLogin, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()
        ObjectAnimator.ofFloat(binding.tvLoginMsg, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()
    }
}