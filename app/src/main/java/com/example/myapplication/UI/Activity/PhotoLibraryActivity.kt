package com.example.myapplication.UI.Activity

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.Base.BaseActivity
import com.example.myapplication.Model.LibraryModel
import com.example.myapplication.UI.Fragment.LibraryFragment
import com.example.myapplication.databinding.ActivityPhotoLibraryBinding
import com.example.myapplication.Adapter.LibraryAdapter
import com.example.myapplication.R
import com.example.myapplication.UI.Fragment.ImageFragment

class PhotoLibraryActivity : BaseActivity<ActivityPhotoLibraryBinding>() {

    private lateinit var listLibraryAdapter: LibraryAdapter
    private var fragmentList = mutableListOf<LibraryModel>()

    override fun getViewBinding(inflater: LayoutInflater): ActivityPhotoLibraryBinding {
        return ActivityPhotoLibraryBinding.inflate(inflater)
    }

    override fun init() {
        // Initialize any required data here
    }

    override fun setUpView() {
        setupRecyclerView()
        // Load the initial fragment into FragmentContainerView
        openFragment(LibraryFragment::class.java, R.id.fragment_container_viewMain, null, false)

        // Set up click listener for return button
        binding.returnFragment.setOnClickListener {
            Log.d(TAG, "setUpView: quaylai")
            returnToPreviousFragment()
        }
    }

    private fun setupRecyclerView() {
        listLibraryAdapter = LibraryAdapter(fragmentList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerView.adapter = listLibraryAdapter
    }

    fun updateFragmentList(fragmentName: String) {
        fragmentList.clear()
        fragmentList.add(LibraryModel(fragmentName))
        listLibraryAdapter.notifyDataSetChanged()
    }

    fun addFragmentToList(fragmentName: String) {
        fragmentList.add(LibraryModel(fragmentName))
        listLibraryAdapter.notifyDataSetChanged()
    }

    override fun openFragment(
        fragmentClazz: Class<*>, containerViewId: Int, args: Bundle?, addBackStack: Boolean
    ) {
        val fragment = fragmentClazz.newInstance() as Fragment
        supportFragmentManager.beginTransaction()
            .replace(containerViewId, fragment, fragmentClazz.simpleName)
            .apply { if (addBackStack) addToBackStack(fragmentClazz.simpleName) }
            .commit()
        addFragmentToList(getFragmentName(fragment))
    }

    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_viewMain, fragment, fragment::class.java.simpleName)
            .addToBackStack(fragment::class.java.simpleName)
            .commit()

        // Update fragment list
        addFragmentToList(getFragmentName(fragment))
        Log.d(TAG, "replaceFragment: $fragmentList")
    }

    fun getFragmentName(fragment: Fragment): String {
        return when (fragment) {
            is LibraryFragment -> "Library"
            is ImageFragment -> "Image"
            else -> "Unknown"
        }
    }

    override fun onBackPressed() {
        val backStackCount = supportFragmentManager.backStackEntryCount
        if (backStackCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
        val currentFragment = supportFragmentManager.fragments.lastOrNull()
        currentFragment?.let {
            updateFragmentList(getFragmentName(it))
        }
    }

    private fun returnToPreviousFragment() {
        val backStackCount = supportFragmentManager.backStackEntryCount
        if (backStackCount > 0) {
            supportFragmentManager.popBackStack()
            val currentFragment = supportFragmentManager.fragments.lastOrNull()
            currentFragment?.let {
                updateFragmentList(getFragmentName(it))
            }
        }
    }
}
