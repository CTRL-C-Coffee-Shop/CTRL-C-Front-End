package com.example.ctrl_c.ui.order.delivery

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.ctrl_c.R
import com.example.ctrl_c.databinding.ActivityDeliveryOrderBinding
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode

class DeliveryOrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDeliveryOrderBinding
    private val AUTOCOMPLETE_REQUEST_CODE = 1
    private val PERMISSIONS_REQUEST_CODE = 123


    // Initialize the Places SDK
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeliveryOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        if (checkPermissions()) {
            buttonGetLocationAction()
        }else{
            requestPermissions()
        }

        Places.initialize(applicationContext, getString(R.string.google_maps_api_key))

        setupAction()
    }

    private fun checkPermissions(): Boolean {
        val fineLocationPermission = ContextCompat.checkSelfPermission(
            this, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        return fineLocationPermission
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSIONS_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, you can now use location-related features.
                buttonGetLocationAction()
            } else {
                // Permission denied, handle this case as needed.
            }
        }
    }

    private fun openPlacePicker() {
        val fields =
            listOf(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG)
        val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
            .build(this)

        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)
    }

    private fun buttonGetLocationAction(){
        binding.buttonChooseLocation.setOnClickListener {
            openPlacePicker()
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            when (resultCode) {
                RESULT_OK -> {
                    val place = Autocomplete.getPlaceFromIntent(data!!)
                    val placeName = place.name
                    val placeAddress = place.address
                    val placeLatLng = place.latLng

                    // You can use the selected place details as needed
                    Toast.makeText(
                        this,
                        "Place: $placeName, Address: $placeAddress, LatLng: $placeLatLng",
                        Toast.LENGTH_LONG
                    ).show()
                }

                AutocompleteActivity.RESULT_ERROR -> {
                    val status = Autocomplete.getStatusFromIntent(data!!)
                    Toast.makeText(this, "Error: ${status.statusMessage}", Toast.LENGTH_SHORT)
                        .show()
                }

                RESULT_CANCELED -> {
                    // The user canceled the operation.
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