package com.dicoding.intermediate.storyapp.ui.maps

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.dicoding.intermediate.storyapp.R
import com.dicoding.intermediate.storyapp.databinding.ActivityMapsBinding
import com.dicoding.intermediate.storyapp.ui.home.HomeViewModel
import com.dicoding.intermediate.storyapp.utils.ViewModelFactory
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var factory: ViewModelFactory
    private var token = ""
    private val homeViewModel: HomeViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupView()
        setupMap() // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        setupViewModel()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.apply {
            isZoomControlsEnabled = true
            isIndoorLevelPickerEnabled = true
            isCompassEnabled = true
            isMapToolbarEnabled = true
        }

        setupSession()
        setupList()
        getMyLocation()

    }

    private fun setupView() {
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setupMap() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun setupViewModel() {
        factory = ViewModelFactory.getInstance(this)
    }

    private fun setupSession() {
        homeViewModel.getSession().observe(this@MapsActivity) {
            token = it.token
            getStories(token)
        }
    }

    private fun setupList() {
        homeViewModel.list.observe(this@MapsActivity) {
            it?.listStory?.forEach { list ->
                mMap.addMarker(
                    MarkerOptions()
                        .position(LatLng(list.lat, list.lon))
                        .title("Story from : ${list.name}")
                        .snippet("ID : ${list.id}")
                )
            }
            if (it.listStory.isNotEmpty()) {
                val random = (0 until it.listStory.size).random()
                mMap.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        LatLng(
                            it.listStory[random].lat,
                            it.listStory[random].lon
                        ), 15f
                    )
                )
            } else {
                val lastLocation = mMap.myLocation.let { location: Location? ->
                    LatLng(
                        location?.latitude ?: 0.0,
                        location?.longitude ?: 0.0
                    )
                }
                mMap.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(lastLocation, 15f)
                )
            }
        }
    }

    private fun getStories(token: String) {
        homeViewModel.getListStoriesWithLocation(token)
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                getMyLocation()
            }
        }

    private fun getMyLocation() {
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }
}