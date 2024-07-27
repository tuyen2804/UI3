package com.example.myapplication.UI.Fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.myapplication.LibraryInterface
import com.example.myapplication.Adapter.ListAlbumAdapter
import com.example.myapplication.UI.Activity.PhotoLibraryActivity
import com.example.myapplication.ViewModel.AlbumViewModel
import com.example.myapplication.databinding.FragmentAllBinding

class AllFragment : Fragment(), LibraryInterface {

    private var _binding: FragmentAllBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AlbumViewModel by viewModels()
    private lateinit var adapter: ListAlbumAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAllBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
        setUpObserver()
    }

    private fun setUpView() {
        adapter = ListAlbumAdapter(this)
        binding.recyclerView.adapter = adapter
        viewModel.checkPermissions(requireContext()) {
            requestPermissions(arrayOf(Manifest.permission.READ_MEDIA_IMAGES), 100)
        }
    }

    private fun setUpObserver() {
        viewModel.albumList.observe(viewLifecycleOwner, Observer { albums ->
            adapter.setData(albums)
        })
    }

    override fun onClickFile(position: Int, text: String) {
        val bundle = Bundle().apply {
            putString("albumName", text)
        }
        val imageFragment = ImageFragment().apply {
            arguments = bundle
        }
        (activity as? PhotoLibraryActivity)?.replaceFragment(imageFragment)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            100 -> {
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    viewModel.fetchAlbums(requireContext())
                } else {
                    // Explain to the user that the feature is unavailable because the permissions were denied.
                }
                return
            }
            else -> {
                // Ignore other requests.
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
