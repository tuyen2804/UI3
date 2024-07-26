package com.example.myapplication.ViewModel

import android.content.Context
import android.provider.MediaStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.Model.ListAlbumModel

class ViewAlbumModel : ViewModel() {

    private val _albumList = MutableLiveData<List<ListAlbumModel>>()
    val albumList: LiveData<List<ListAlbumModel>> get() = _albumList

    fun fetchAlbums(context: Context) {
        val albums = getAllShownImagesPath(context)
        _albumList.postValue(albums)
    }

    private fun getAllShownImagesPath(context: Context): List<ListAlbumModel> {
        val listOfAllImages = mutableListOf<ListAlbumModel>()
        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val projection = arrayOf(
            MediaStore.MediaColumns.DATA,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME
        )

        context.contentResolver.query(uri, projection, null, null, null)?.use { cursor ->
            val columnIndexData = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
            val columnIndexFolderName = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)

            val albumMap = mutableMapOf<String, MutableList<String>>()

            while (cursor.moveToNext()) {
                val absolutePathOfImage = cursor.getString(columnIndexData)
                val albumName = cursor.getString(columnIndexFolderName)

                albumMap.computeIfAbsent(albumName) { mutableListOf() }.add(absolutePathOfImage)
            }

            for ((albumName, imagePaths) in albumMap) {
                listOfAllImages.add(ListAlbumModel(imagePaths[0], albumName, imagePaths.size))
            }
        }

        return listOfAllImages
    }
}
