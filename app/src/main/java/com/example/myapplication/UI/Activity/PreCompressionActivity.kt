package com.example.myapplication.UI.Activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.Adapter.PreAdapter
import com.example.myapplication.R
import com.google.android.material.tabs.TabLayoutMediator

class PreCompressionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pre_compression)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }
        setupViewPager()
    }

        private fun setupViewPager() {
            val viewPager = findViewById<ViewPager2>(R.id.viewPager)
            val tabLayout = findViewById<com.google.android.material.tabs.TabLayout>(R.id.tabLayout)
            val adapter = PreAdapter(supportFragmentManager, lifecycle)
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
                    val fragmentName = when (position) {
                        0 -> "By pixel"
                        1 -> "By percentage"
                        else -> "Unknown"
                    }
                    // Update RecyclerView or any other views here
                }
            })
        }
    }