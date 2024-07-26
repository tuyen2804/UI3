package com.example.myapplication.Model

data class ListImageModel(
    val imagePath: String,
    val size: String, // Thay thế hoặc thêm thuộc tính khác nếu cần
    var isChecked: Boolean
)