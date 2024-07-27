package com.example.myapplication.UI.Fragment

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adapter.ImageAdapter
import com.example.myapplication.R
import com.example.myapplication.UI.Activity.ListImageActivity
import com.example.myapplication.ViewModel.ListImageViewModel

class ImageFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var btnCompression: Button
    private lateinit var adapter: ImageAdapter
    private val viewModel: ListImageViewModel by viewModels()
    private lateinit var albumName: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_image, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)
        btnCompression = view.findViewById(R.id.btnCompression)

        albumName = arguments?.getString("albumName") ?: ""

        adapter = ImageAdapter(emptyList())
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        recyclerView.adapter = adapter

        viewModel.imageModels.observe(viewLifecycleOwner) { images ->
            adapter.updateItems(images)
        }

        viewModel.loadImagesFromAlbum(requireContext(), albumName)

        btnCompression.setOnClickListener {
            val selectedImagePaths = adapter.getSelectedImagePaths()
            val intent = Intent(requireContext(), ListImageActivity::class.java)
            intent.putStringArrayListExtra("selectedImagePaths", ArrayList(selectedImagePaths))
            Log.d(TAG, "onViewCreated: $selectedImagePaths")
            startActivity(intent)
        }
    }
}
