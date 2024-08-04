package com.example.myapplication.UI.Activity

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.Adapter.TabEditAdapter
import com.example.myapplication.Adapter.TabPreAdapter
import com.example.myapplication.R
import com.example.myapplication.UI.Fragment.EditByPixelFragment
import com.example.myapplication.UI.Fragment.EditBypercentageFragment
import com.example.myapplication.UI.Fragment.PreByPixelFragment
import com.example.myapplication.UI.Fragment.PreBypercentageFragment
import com.example.myapplication.ViewModel.EditViewModel
import com.example.myapplication.databinding.ActivityEditBinding
import com.example.myapplication.databinding.ActivityPreCompressionBinding
import com.google.android.material.tabs.TabLayoutMediator

class EditActivity : AppCompatActivity() {
    private val viewModel: EditViewModel by viewModels()
    private lateinit var binding: ActivityEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupViewPager()

        // Extract data from intent and set to ViewModel
        val selectedImagePaths = intent.getStringArrayListExtra("selectedImagePaths")
        selectedImagePaths?.let {
            viewModel.setImageData(it)
        }

        binding.returnActivity.setOnClickListener {
            finish()
        }
    }

    private fun setupViewPager() {
        val viewPager = findViewById<ViewPager2>(R.id.viewPager)
        val tabLayout = findViewById<com.google.android.material.tabs.TabLayout>(R.id.tabLayout)
        val adapter = TabEditAdapter(supportFragmentManager, lifecycle)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "By pixel"
                1 -> "By percentage"
                else -> null
            }
        }.attach()

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                // Notify the fragment to update its data
                val fragment = supportFragmentManager.findFragmentByTag("f$position")
                if (fragment is EditByPixelFragment) {
                    fragment.updateData()
                }
                if (fragment is EditBypercentageFragment) {
                    fragment.updateData()
                }
            }
        })
    }
}
