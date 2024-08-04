package com.example.myapplication.ViewModel

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.provider.MediaStore
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.Model.AlbumModel

class AlbumViewModel : ViewModel() {

    private val _albumList = MutableLiveData<List<AlbumModel>>()
    val albumList: LiveData<List<AlbumModel>> get() = _albumList

    fun checkPermissions(context: Context, requestPermissions: () -> Unit) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_MEDIA_IMAGES
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fetchAlbums(context)
        } else {
            requestPermissions.invoke()
        }
    }

    fun fetchAlbums(context: Context) {
        val albums = getAllShownImagesPath(context)
        _albumList.postValue(albums)
    }


    private fun getAllShownImagesPath(context: Context): List<AlbumModel> {
        val listOfAllImages = mutableListOf<AlbumModel>()
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
                listOfAllImages.add(AlbumModel(imagePaths[0], albumName, imagePaths.size))
            }
        }

        return listOfAllImages
    }
}
