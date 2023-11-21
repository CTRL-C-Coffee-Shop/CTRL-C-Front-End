package com.example.ctrl_c.ui.order.delivery

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.ctrl_c.R
import com.example.ctrl_c.databinding.ActivityDeliveryOrderBinding
import com.example.ctrl_c.factory.ViewModelFactory
import com.example.ctrl_c.helper.LoadingHandler
import com.example.ctrl_c.model.result.Result
import com.example.ctrl_c.viewmodel.product.ProductViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.io.IOException
import java.util.Locale


class DeliveryOrderActivity : AppCompatActivity() , LoadingHandler{
    private lateinit var binding: ActivityDeliveryOrderBinding
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var factory: ViewModelFactory
    private val viewModel: ProductViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeliveryOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        setupViewModel()
        setupAction()
        setupProductList()


    }

    private fun setupViewModel() {
        factory = ViewModelFactory.getInstance(binding.root.context)
    }

    private fun setupProductList() {
        viewModel.getAllProduct().observe(this) {
            it?.let { result ->
                when (result) {
                    is Result.Loading -> {
                        loadingHandler(true)
                    }

                    is Result.Error -> {
                        loadingHandler(false)
                        Toast.makeText(
                            this,
                            "Failed to fetch proudct data",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }

                    is Result.Success -> {
                        loadingHandler(false)
                        Toast.makeText(
                            this,
                            "Fetch Success!",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.d("INI DEBUGGING", "setupProductList: ${result.data}")
                    }
                }
            }
        }
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

    override fun loadingHandler(isLoading: Boolean) {
        if (isLoading) {
            binding.loadingAnimation.visibility = View.VISIBLE
        } else {
            binding.loadingAnimation.visibility = View.GONE
        }
    }
}