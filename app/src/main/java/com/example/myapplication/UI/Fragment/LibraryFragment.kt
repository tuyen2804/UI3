package com.example.myapplication.UI.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.Base.BaseFragment
import com.example.myapplication.Adapter.ViewLibraryAdapter
import com.example.myapplication.R
import com.example.myapplication.UI.Activity.ImageFragment
import com.example.myapplication.databinding.FragmentLibraryBinding
import com.google.android.material.tabs.TabLayoutMediator

class LibraryFragment : BaseFragment<FragmentLibraryBinding>() {

    private lateinit var adapter: ViewLibraryAdapter
    private val fragmentList = mutableListOf<Fragment>() // Danh sách lưu trữ các Fragment

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentLibraryBinding {
        return FragmentLibraryBinding.inflate(inflater, container, false)
    }

    override fun init() {
        // Initialization code here
    }

    override fun setUpView() {
        setupViewPager()
    }

    private fun setupViewPager() {
        // Initialize with the default fragments
        val initialFragments = listOf(AllFragment(), BrowserFragment())
        fragmentList.addAll(initialFragments)

        adapter = ViewLibraryAdapter(childFragmentManager, lifecycle, fragmentList)
        binding.viewLibrary.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewLibrary) { tab, position ->
            tab.text = when (position) {
                0 -> "All"
                1 -> "Browser"
                else -> null
            }
        }.attach()

        binding.viewLibrary.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                // Optional: handle page changes if needed
            }
        })
    }

    fun openImageFragment(albumName: String) {
        val imageFragment = ImageFragment().apply {
            arguments = Bundle().apply {
                putString("albumName", albumName)
            }
        }

        // Add ImageFragment to the fragment list and notify adapter
        fragmentList.add(imageFragment)
        adapter.notifyDataSetChanged()

        // Set the current item to the newly added fragment
        binding.viewLibrary.currentItem = fragmentList.size - 1
    }
}
