package com.example.ctrl_c.ui.order.delivery

import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ctrl_c.R
import com.example.ctrl_c.databinding.ActivityDeliveryOrderBinding

class DeliveryOrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDeliveryOrderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeliveryOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        setupAction()
    }

    private fun setupAction() {
        //data dummy buat lokasi kedai
        val listLocation = listOf(
            "CTRL+C 23 Paskal",
            "CTRL+C Istana Plaza",
            "CTRL+C Dipati Ukur",
            "CTRL+C Dago",
            "CTRL+C Paris Van Java"
        )
        val adapter = ArrayAdapter(this, R.layout.store_location_list, listLocation)

        binding.autoComplete.apply {
            setAdapter(adapter)
            onItemClickListener =
                AdapterView.OnItemClickListener { adapterView, view, position, id ->
                    val itemSelected = adapterView.getItemAtPosition(position)
                    //masukin nama storenya (bisa di pass ke API dari sini)
                    Toast.makeText(
                        this@DeliveryOrderActivity,
                        "You Choose $itemSelected as the location",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }
}