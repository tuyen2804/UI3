package com.example.myapplication.Model

data class ImageModel(
    val imagePath: String,
    val size: String, // Thay thế hoặc thêm thuộc tính khác nếu cần
    var isChecked: Boolean
)