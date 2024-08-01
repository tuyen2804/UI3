package com.example.myapplication.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myapplication.UI.Fragment.ByPixelFragment
import com.example.myapplication.UI.Fragment.BypercentageFragment

class PreAdapter (fragmentManager: FragmentManager, lifecycle: Lifecycle) :
FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ByPixelFragment()
            1 -> BypercentageFragment()
            else -> throw IllegalArgumentException("Invalid position")
        }
    }
}