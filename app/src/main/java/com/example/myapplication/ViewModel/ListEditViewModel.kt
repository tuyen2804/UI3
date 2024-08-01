package com.example.myapplication.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.Model.ListEditModel
import com.example.myapplication.Utils.FileUtils

class ListEditViewModel : ViewModel() {
    private val _imageData = MutableLiveData<List<ListEditModel>>()
    val imageData: LiveData<List<ListEditModel>> get() = _imageData

    fun setImageData(paths: List<String>) {
        val data = paths.map { path ->
            val size = FileUtils.getSizeFile(path)
            val width = FileUtils.getImageWidth(path)
            val height = FileUtils.getImageHeight(path)
            var typeImage = FileUtils.getImageType(path)
            ListEditModel(
                imagePath = path,
                seekbar = 0.0f,
                sizeImage = size,
                typeImage = typeImage,
                width = width,
                height = height
            )
        }
        _imageData.value = data
    }
}
