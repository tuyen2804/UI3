package com.example.myapplication.UI.Activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.Base.BaseActivity
import com.example.myapplication.DataItem.ListLibrary
import com.example.myapplication.UI.Fragment.LibraryFragment
import com.example.myapplication.databinding.ActivityPhotoLibraryBinding
import com.example.myapplication.Adapter.ListLibraryAdapter
import com.example.myapplication.R

class PhotoLibraryActivity : BaseActivity<ActivityPhotoLibraryBinding>() {

    private lateinit var listLibraryAdapter: ListLibraryAdapter
    private val fragmentList = mutableListOf<ListLibrary>()

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
    }

    private fun setupRecyclerView() {
        listLibraryAdapter = ListLibraryAdapter(fragmentList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerView.adapter = listLibraryAdapter
    }

    fun updateFragmentList(fragmentName: String) {
        fragmentList.clear()
        fragmentList.add(ListLibrary(fragmentName))
        listLibraryAdapter.notifyDataSetChanged()
    }

    fun addFragmentToList(fragmentName: String) {
        fragmentList.add(ListLibrary(fragmentName))
        listLibraryAdapter.notifyDataSetChanged()
    }

    override fun openFragment(
        fragmentClazz: Class<*>, containerViewId: Int, args: Bundle?, addBackStack: Boolean
    ) {
        super.openFragment(fragmentClazz, containerViewId, args, addBackStack)
        val fragmentInstance = supportFragmentManager.findFragmentByTag(fragmentClazz.simpleName)
        fragmentInstance?.let {
            addFragmentToList(getFragmentName(it))
        }
    }

    private fun getFragmentName(fragment: Fragment): String {
        return when (fragment) {
            is LibraryFragment -> "Library"
            is ImageFragment -> "Image"
            else -> "Unknown"
        }
    }

    fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_viewMain, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val currentFragment = supportFragmentManager.fragments.lastOrNull()
        currentFragment?.let {
            updateFragmentList(getFragmentName(it))
        }
    }
}
