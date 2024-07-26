package com.example.myapplication.UI.Activity

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.Adapter.ListImageAdapter
import com.example.myapplication.databinding.ActivityListImageBinding

class ListImageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListImageBinding
    private lateinit var adapter: ListImageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val selectedImagePaths = intent.getStringArrayListExtra("selectedImagePaths")
        Log.d(TAG, "onCreate2: " + selectedImagePaths)
        if (selectedImagePaths != null) {
            adapter = ListImageAdapter(selectedImagePaths)
            binding.recyclerView.layoutManager = GridLayoutManager(this, 3)
            binding.recyclerView.adapter = adapter
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnNext.setOnClickListener(){
            startActivity(Intent(this,PreCompressionActivity::class.java))
        }
    }
}
