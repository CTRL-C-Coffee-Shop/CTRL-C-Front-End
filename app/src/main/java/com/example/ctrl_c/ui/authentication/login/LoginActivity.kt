package com.example.ctrl_c.ui.authentication.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.ctrl_c.data.local.UserPreference
import com.example.ctrl_c.databinding.ActivityLoginBinding
import com.example.ctrl_c.factory.ViewModelFactory
import com.example.ctrl_c.helper.LoadingHandler
import com.example.ctrl_c.model.response.authentication.LoginResponse
import com.example.ctrl_c.model.result.Result
import com.example.ctrl_c.ui.authentication.register.RegisterActivity
import com.example.ctrl_c.ui.main.MainActivity
import com.example.ctrl_c.viewmodel.authetntication.login.LoginViewModel


class LoginActivity : AppCompatActivity(), LoadingHandler {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var factory: ViewModelFactory
    private val viewModel: LoginViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        setupViewModel()
        playAnimation()
        setupAction()
    }

    private fun setupViewModel() {
        factory = ViewModelFactory.getInstance(binding.root.context)
    }

    private fun setupAction() {
        binding.btnSignUp.setOnClickListener {
            navigateToRegisterActivity()
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            viewModel.login(email, password).observe(this) {
                it?.let { result ->
                    when (result) {
                        is Result.Loading -> {
                            loadingHandler(true)
                        }

                        is Result.Error -> {
                            loadingHandler(false)
                            Toast.makeText(
                                this,
                                "Failed to Login",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }

                        is Result.Success -> {
                            loadingHandler(false)
                            Toast.makeText(
                                this,
                                "Login Success!",
                                Toast.LENGTH_SHORT
                            ).show()
                            saveTokenToPreference(result.data)
                            saveNameToPreference(result.data)
                            navigateToMainActivity()
                        }
                    }

                }
            }
        }
    }

    private fun saveTokenToPreference(data: LoginResponse) {
        val pref = UserPreference(this)
        val result = data.token
        pref.saveToken(result)
    }

    private fun saveNameToPreference(data: LoginResponse) {
        val pref = UserPreference(this)
        val result = data.name
        pref.saveName(result)
    }

    private fun navigateToRegisterActivity() {
        //intent to Register activity.
        val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToMainActivity() {
        //intent to Main activity / Home Page.
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
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

    override fun loadingHandler(isLoading: Boolean) {
        if (isLoading) {
            binding.loadingAnimation.visibility = View.VISIBLE
        } else {
            binding.loadingAnimation.visibility = View.GONE
        }
    }
}