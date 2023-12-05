package com.example.ctrl_c.ui.authentication.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.ctrl_c.databinding.ActivityRegisterBinding
import com.example.ctrl_c.factory.ViewModelFactory
import com.example.ctrl_c.helper.LoadingHandler
import com.example.ctrl_c.model.result.Result.Error
import com.example.ctrl_c.model.result.Result.Loading
import com.example.ctrl_c.model.result.Result.Success
import com.example.ctrl_c.ui.authentication.login.LoginActivity
import com.example.ctrl_c.viewmodel.authetntication.register.RegisterViewModel

class RegisterActivity : AppCompatActivity(), LoadingHandler {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var factory: ViewModelFactory
    private val viewModel: RegisterViewModel by viewModels { factory }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        setupViewModel()
        setupAction()
        playAnimation()
    }

    private fun setupAction() {
        binding.btnSignUp.setOnClickListener {
            val fullname = binding.etFullname.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (fullname.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.register(fullname, email, password, 0).observe(this) {
                it?.let { result ->
                    when (result) {
                        is Loading -> {
                            loadingHandler(true)
                        }

                        is Error -> {
                            loadingHandler(false)
                            Toast.makeText(
                                this,
                                "Failed to Register",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }

                        is Success -> {
                            loadingHandler(false)
                            Toast.makeText(
                                this,
                                "Register Success!",
                                Toast.LENGTH_SHORT
                            ).show()
                            navigateToSignInActivity()
                        }
                    }
                }

            }

        }

        binding.btnLogin.setOnClickListener {
            navigateToSignInActivity()
        }
    }

    private fun setupViewModel() {
        factory = ViewModelFactory.getInstance(binding.root.context)
    }

    private fun navigateToSignInActivity() {
        //intent to login activity.
        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    private fun playAnimation() {
        val image =
            ObjectAnimator.ofFloat(binding.ivSignupIllustration, View.ALPHA, 1f).setDuration(500)
        val tvSignUp = ObjectAnimator.ofFloat(binding.tvSignup, View.ALPHA, 1f).setDuration(500)
        val tvSignUpMessage =
            ObjectAnimator.ofFloat(binding.tvSignupMsg, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(
                image, tvSignUp, tvSignUpMessage
            )
            startDelay = 300
        }.start()

        ObjectAnimator.ofFloat(binding.ivSignupIllustration, View.TRANSLATION_X, -50f, 50f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        ObjectAnimator.ofFloat(binding.tvSignup, View.TRANSLATION_X, -50f, 50f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        ObjectAnimator.ofFloat(binding.tvSignupMsg, View.TRANSLATION_X, -50f, 50f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()
    }

    override fun loadingHandler(isLoading: Boolean) {
        if (isLoading) {
            binding.loadingAnimation.visibility = View.VISIBLE
        } else {
            binding.loadingAnimation.visibility = View.GONE
        }
    }


}