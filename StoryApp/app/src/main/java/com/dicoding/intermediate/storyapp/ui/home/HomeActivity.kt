package com.dicoding.intermediate.storyapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.intermediate.storyapp.R
import com.dicoding.intermediate.storyapp.databinding.ActivityHomeBinding
import com.dicoding.intermediate.storyapp.ui.add.AddStoryActivity
import com.dicoding.intermediate.storyapp.ui.welcome.WelcomeActivity
import com.dicoding.intermediate.storyapp.utils.ViewModelFactory

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var factory: ViewModelFactory
    private var token = ""
    private val homeViewModel: HomeViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupView()
        setupViewModel()
        setupAdapter()
        setupAction()
    }

    private fun setupAction() {
        binding.fabAdd.setOnClickListener {
            startActivity(Intent(this, AddStoryActivity::class.java))
        }
    }

    private fun setupAdapter() {
        homeViewModel.list.observe(this@HomeActivity) { adapter ->
            if (adapter != null) {
                binding.rvStories.adapter = ListStoryAdapter(adapter.listStory)
            }

        }
    }

    private fun setupViewModel() {
        factory = ViewModelFactory.getInstance(this)

        showLoading()
        homeViewModel.getSession().observe(this@HomeActivity) {
            token = it.token
            if (!it.isLogin) {
                moveActivity()
            } else {
                getListStories(token)
            }
        }
        showToast()
    }

    private fun setupView() {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            rvStories.setHasFixedSize(true)
            rvStories.layoutManager = LinearLayoutManager(this@HomeActivity)
        }
    }

    private fun showLoading() {
        homeViewModel.isLoading.observe(this@HomeActivity) {
            binding.pbHome.visibility = if (it) View.VISIBLE else View.GONE
        }
    }

    private fun showToast() {
        homeViewModel.toastText.observe(this@HomeActivity) { toastText ->
            Toast.makeText(
                this@HomeActivity, toastText, Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun moveActivity() {
        startActivity(Intent(this@HomeActivity, WelcomeActivity::class.java))
        finish()
    }

    private fun getListStories(token: String) {
        homeViewModel.getListStories(token)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.btn_language -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                true
            }
            R.id.btn_logout -> {
                homeViewModel.logout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}