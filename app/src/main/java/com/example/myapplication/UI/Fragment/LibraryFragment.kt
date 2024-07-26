package com.example.myapplication.UI.Fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.Base.BaseFragment
import com.example.myapplication.Adapter.ViewLibraryAdapter
import com.example.myapplication.UI.Activity.PhotoLibraryActivity
import com.example.myapplication.databinding.FragmentLibraryBinding
import com.google.android.material.tabs.TabLayoutMediator

class LibraryFragment : BaseFragment<FragmentLibraryBinding>() {

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
        val adapter = ViewLibraryAdapter(childFragmentManager, lifecycle)
        binding.viewLibrary.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewLibrary) { tab, position ->
            tab.text = when (position) {
                0 -> "All"
                1 -> "Browser"
                else -> null
            }
        }.attach()

        // Update RecyclerView in PhotoLibraryActivity when page changes
        binding.viewLibrary.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                val fragmentName = when (position) {
                    0 -> "All"
                    1 -> "Browser"
                    else -> "Unknown"
                }
                (activity as? PhotoLibraryActivity)?.updateFragmentList(fragmentName)
            }
        })
    }


}
