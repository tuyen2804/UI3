package com.example.myapplication.UI.Fragment

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adapter.ImageAdapter
import com.example.myapplication.Model.ListImageModel
import com.example.myapplication.R
import com.example.myapplication.UI.Activity.ListImageActivity

class ImageFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ImageAdapter
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

        // Nhận tên album từ arguments
        albumName = arguments?.getString("albumName") ?: ""

        // Lấy hình ảnh từ album và cập nhật RecyclerView
        val items = getImagesFromAlbum(requireContext(), albumName)
        adapter = ImageAdapter(items)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        recyclerView.adapter = adapter

        val btnCompression: Button = view.findViewById(R.id.btnCompression)
        btnCompression.setOnClickListener {
            val selectedImagePaths = getSelectedImagePaths()
            val intent = Intent(requireContext(), ListImageActivity::class.java)
            intent.putStringArrayListExtra("selectedImagePaths", ArrayList(selectedImagePaths))
            Log.d(TAG, "onViewCreated1: "+ArrayList(selectedImagePaths))
            startActivity(intent)
        }
    }

    private fun getImagesFromAlbum(context: Context, albumName: String): List<ListImageModel> {
        val listOfImages = mutableListOf<ListImageModel>()
        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val projection = arrayOf(
            MediaStore.MediaColumns.DATA
        )

        context.contentResolver.query(
            uri, projection, "${MediaStore.Images.Media.BUCKET_DISPLAY_NAME} = ?",
            arrayOf(albumName), null
        )?.use { cursor ->
            val columnIndexData = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)

            while (cursor.moveToNext()) {
                val absolutePathOfImage = cursor.getString(columnIndexData)
                listOfImages.add(ListImageModel(absolutePathOfImage, "", false))
            }
        }

        return listOfImages
    }

    private fun getSelectedImagePaths(): List<String> {
        return adapter.items.filter { it.isChecked }.map { it.imagePath }
    }
}
