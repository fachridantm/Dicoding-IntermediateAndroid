package com.dicoding.intermediate.storyapp.ui.maps

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.intermediate.storyapp.R
import com.dicoding.intermediate.storyapp.databinding.ActivityMapsBinding
import com.dicoding.intermediate.storyapp.ui.home.HomeViewModel
import com.dicoding.intermediate.storyapp.utils.ViewModelFactory
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
        setupSession()
        setupList()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true
    }

    private fun setupList() {
        homeViewModel.list.observe(this@MapsActivity){
            it?.listStory?.forEach { list ->
                mMap.addMarker(
                    MarkerOptions()
                        .position(LatLng(list.lat, list.lon))
                        .title("User : ${list.name}")
                )
            }
        }
    }

    private fun setupSession() {
        homeViewModel.getSession().observe(this@MapsActivity) {
            token = it.token
            getStories(token)
        }
    }

    private fun setupViewModel() {
        factory = ViewModelFactory.getInstance(this)
    }

    private fun getStories(token: String){
        homeViewModel.getListStories(token)
    }

    private fun setupMap() {
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun setupView() {
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}