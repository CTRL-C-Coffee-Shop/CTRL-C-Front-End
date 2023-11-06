package com.example.ctrl_c.ui.order.delivery

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.ctrl_c.R
import com.example.ctrl_c.databinding.ActivityDeliveryOrderBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.io.IOException
import java.util.Locale


class DeliveryOrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDeliveryOrderBinding
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeliveryOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        setupAction()

    }

    private fun checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            getUserLocation()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                100
            )
        }
    }

    private fun getUserLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
            val location = task.result
            if (location != null) {
                try {
                    val geocoder = Geocoder(this, Locale.getDefault())
                    val address = geocoder.getFromLocation(location.latitude, location.longitude, 1)

                    val addressLine = address?.get(0)?.getAddressLine(0)
                    binding.buttonChooseLocation.text = addressLine
                    binding.buttonChooseLocation.textSize = 15f

                    Toast.makeText(this, "your location is : $addressLine", Toast.LENGTH_SHORT)
                        .show()

                    // pass nama user location disini.

                } catch (_: IOException) {

                }
            }
        }
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
                AdapterView.OnItemClickListener { adapterView, _, position, _ ->
                    val itemSelected = adapterView.getItemAtPosition(position)
                    //masukin nama storenya (bisa di pass ke API dari sini)
                    Toast.makeText(
                        this@DeliveryOrderActivity,
                        "You Choose $itemSelected for the store's location",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }

        binding.buttonChooseLocation.setOnClickListener {
            checkLocationPermission()
        }

    }
}