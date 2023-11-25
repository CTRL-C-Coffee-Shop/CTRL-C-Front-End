package com.example.ctrl_c.ui.admin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ctrl_c.data.local.UserPreference
import com.example.ctrl_c.databinding.ActivityAdminPageBinding
import com.example.ctrl_c.ui.authentication.login.LoginActivity

class AdminPageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()

//        with(binding) {
//            searchView.setupWithSearchBar(searchBar)
//            searchView
//                .editText
//                .setOnEditorActionListener { textView, actionId, event ->
//                    searchBar.text = searchView.text
//                    searchView.hide()
//                    Toast.makeText(this@AdminPageActivity, searchView.text, Toast.LENGTH_SHORT).show()
//                    false
//                }
//        }
    }

    private fun setupAction() {
        binding.button2.setOnClickListener {
            val pref = UserPreference(this)
            pref.clearPreferences()
            navigateToLoginActivity()
        }
    }

    private fun navigateToLoginActivity() {
        val intent = Intent(this@AdminPageActivity, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}