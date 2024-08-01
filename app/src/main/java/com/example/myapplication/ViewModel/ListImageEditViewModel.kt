// ViewModel
package com.example.myapplication.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.Model.ListImageEditModel
import com.example.myapplication.Utils.FileUtils

class ListImageEditViewModel : ViewModel() {
    private val _images = MutableLiveData<List<ListImageEditModel>>()
    val images: LiveData<List<ListImageEditModel>> get() = _images

    fun loadImages(imagePaths: List<String>) {
        val models = imagePaths.map { path ->
            val size = FileUtils.getSizeFile(path)
            val width = FileUtils.getImageWidth(path)
            val height = FileUtils.getImageHeight(path)
            ListImageEditModel(
                imagePath = path,
                sizeImage = size,
                width = width.toString(),
                height = height.toString()
            )
        }
        _images.value = models
    }

    fun removeImage(image: ListImageEditModel) {
        _images.value = _images.value?.filter { it != image }
    }
}
