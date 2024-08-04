// ViewModel
package com.example.myapplication.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.Model.ImageEditModel
import com.example.myapplication.Utils.FileUtils

class ImageEditViewModel : ViewModel() {
    private val _images = MutableLiveData<List<ImageEditModel>>()
    val images: LiveData<List<ImageEditModel>> get() = _images

    fun loadImages(imagePaths: List<String>) {
        val models = imagePaths.map { path ->
            val size = FileUtils.getSizeFile(path)
            val width = FileUtils.getImageWidth(path)
            val height = FileUtils.getImageHeight(path)
            ImageEditModel(
                imagePath = path,
                sizeImage = size,
                width = width.toString(),
                height = height.toString()
            )
        }
        _images.value = models
    }

    fun removeImage(image: ImageEditModel) {
        _images.value = _images.value?.filter { it != image }
    }
}
