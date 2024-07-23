package com.example.myapplication.UI.Activity

import android.app.Activity
import android.database.Cursor
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adapter.ImageAdapter
import com.example.myapplication.DataItem.ItemImage
import com.example.myapplication.R

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
        val items = getImagesFromAlbum(requireActivity(), albumName)
        adapter = ImageAdapter(items)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        recyclerView.adapter = adapter
    }

    private fun getImagesFromAlbum(activity: Activity, albumName: String): List<ItemImage> {
        val cursor: Cursor?
        val listOfImages = mutableListOf<ItemImage>()
        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val projection = arrayOf(
            MediaStore.MediaColumns.DATA
        )

        cursor = activity.contentResolver.query(
            uri, projection, "${MediaStore.Images.Media.BUCKET_DISPLAY_NAME} = ?",
            arrayOf(albumName), null
        )

        val columnIndexData = cursor!!.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)

        while (cursor.moveToNext()) {
            val absolutePathOfImage = cursor.getString(columnIndexData)
            listOfImages.add(ItemImage(absolutePathOfImage, "", false))
        }
        cursor.close()

        return listOfImages
    }
    override fun onDestroyView() {
        super.onDestroyView()
    }
}

