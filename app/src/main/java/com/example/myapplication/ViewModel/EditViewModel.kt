package com.example.myapplication.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.Model.EditModel
import com.example.myapplication.Utils.FileUtils

class EditViewModel : ViewModel() {
    private val _imageData = MutableLiveData<List<EditModel>>()
    val imageData: LiveData<List<EditModel>> get() = _imageData

    fun setImageData(paths: List<String>) {
        val data = paths.map { path ->
            val size = FileUtils.getSizeFile(path)
            val width = FileUtils.getImageWidth(path)
            val height = FileUtils.getImageHeight(path)
            var typeImage = FileUtils.getImageType(path)
            EditModel(
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
