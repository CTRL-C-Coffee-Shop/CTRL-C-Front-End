//package com.example.ctrl_c.ui.admin
//
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.widget.Toast
//import com.example.ctrl_c.databinding.ActivityAdminPageBinding
//
//class AdminPageActivity : AppCompatActivity() {
//    private lateinit var binding: ActivityAdminPageBinding
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityAdminPageBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
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
//    }
//}