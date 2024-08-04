package com.example.myapplication.ViewModel

import android.content.Context
import android.provider.MediaStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.Model.ImageModel
import com.example.myapplication.Utils.FileUtils

class ImageViewModel : ViewModel() {

    private val _imageModels = MutableLiveData<List<ImageModel>>()
    val imageModels: LiveData<List<ImageModel>> get() = _imageModels

    fun loadImagesFromAlbum(context: Context, albumName: String) {
        val listOfImages = mutableListOf<ImageModel>()
        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.MediaColumns.DATA)

        context.contentResolver.query(
            uri, projection, "${MediaStore.Images.Media.BUCKET_DISPLAY_NAME} = ?",
            arrayOf(albumName), null
        )?.use { cursor ->
            val columnIndexData = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
            while (cursor.moveToNext()) {
                val absolutePathOfImage = cursor.getString(columnIndexData)
                val size = FileUtils.getSizeFile(absolutePathOfImage)
                listOfImages.add(ImageModel(absolutePathOfImage, size, false))
            }
        }

        _imageModels.value = listOfImages
    }
}
