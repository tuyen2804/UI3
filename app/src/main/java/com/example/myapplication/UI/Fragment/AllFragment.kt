package com.example.myapplication.UI.Fragment

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.Adapter.LibraryAdapter
import com.example.myapplication.Base.BaseFragment
import com.example.myapplication.DataItem.ItemAll
import com.example.myapplication.LibraryInterface
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentAllBinding

class AllFragment : BaseFragment<FragmentAllBinding>(), LibraryInterface {
    private val arl: ArrayList<ItemAll> = arrayListOf()
    private lateinit var adapter: LibraryAdapter

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentAllBinding {
        return FragmentAllBinding.inflate(inflater, container, false)
    }

    override fun init() {
        // Initialization code here
    }

    override fun setUpView() {
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)

        // Initialize adapter here
        adapter = LibraryAdapter(arl, this)
        binding.recyclerView.adapter = adapter

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_MEDIA_IMAGES
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val dataImg = getAllShownImagesPath(requireActivity())
            arl.addAll(dataImg)
            adapter.notifyDataSetChanged()
        } else {
            requestPermissions(arrayOf(Manifest.permission.READ_MEDIA_IMAGES), 100)
        }
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
                    val dataImg = getAllShownImagesPath(requireActivity())
                    arl.addAll(dataImg)
                    adapter.notifyDataSetChanged()
                } else {
                    // Explain to the user that the feature is unavailable because
                    // the feature requires a permission that the user has denied.
                }
                return
            }
            else -> {
                // Ignore all other requests.
            }
        }
    }

    private fun getAllShownImagesPath(activity: Activity): List<ItemAll> {
        val cursor: Cursor?
        val listOfAllImages = mutableListOf<ItemAll>()
        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val projection = arrayOf(
            MediaStore.MediaColumns.DATA,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME
        )

        cursor = activity.contentResolver.query(
            uri, projection, null,
            null, null
        )

        val columnIndexData = cursor!!.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
        val columnIndexFolderName = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)

        val albumMap = mutableMapOf<String, Pair<String, Int>>()

        while (cursor.moveToNext()) {
            val absolutePathOfImage = cursor.getString(columnIndexData)
            val albumName = cursor.getString(columnIndexFolderName)

            if (albumMap.containsKey(albumName)) {
                val current = albumMap[albumName]!!
                albumMap[albumName] = Pair(current.first, current.second + 1)
            } else {
                albumMap[albumName] = Pair(absolutePathOfImage, 1)
            }
        }
        cursor.close()

        for ((albumName, pair) in albumMap) {
            val (firstImagePath, imageCount) = pair
            listOfAllImages.add(ItemAll(firstImagePath, albumName, imageCount))
        }

        return listOfAllImages
    }

    override fun onClickFile(position: Int, text: String) {
        // Tạo Bundle và truyền tên album
        val bundle = Bundle().apply {
            putString("albumName", text)
        }

        // Chuyển đổi fragment bằng phương thức openImageFragment từ LibraryFragment
        (parentFragment as? LibraryFragment)?.openImageFragment(text)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
