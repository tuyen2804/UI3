package com.example.myapplication.UI.Activity

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.Adapter.ImageEditAdapter
import com.example.myapplication.ViewModel.ImageEditViewModel
import com.example.myapplication.databinding.ActivityListImageBinding

class ListImageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListImageBinding
    private val listImageEditViewModel: ImageEditViewModel by viewModels()
    private lateinit var adapter: ImageEditAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val selectedImagePaths = intent.getStringArrayListExtra("selectedImagePaths")

        adapter = ImageEditAdapter(emptyList()) { image ->
            listImageEditViewModel.removeImage(image)
        }
        binding.recyclerView.layoutManager = GridLayoutManager(this, 3)
        binding.recyclerView.adapter = adapter

        listImageEditViewModel.images.observe(this) { images ->
            adapter.updateItems(images)
        }

        selectedImagePaths?.let {
            listImageEditViewModel.loadImages(it)
        }

        val nameAction = getNameAction()

        binding.btnNext.setOnClickListener {
            when (nameAction) {
                "FastCompressor" -> {
                    val intent = Intent(this, SaveActivity::class.java).apply {
                        putStringArrayListExtra("selectedImagePaths", ArrayList(listImageEditViewModel.images.value?.map { it.imagePath }))
                    }
                    startActivity(intent)
                }
                "PreCompression" -> {
                    val intent = Intent(this, PreCompressionActivity::class.java).apply {
                        putStringArrayListExtra("selectedImagePaths", ArrayList(listImageEditViewModel.images.value?.map { it.imagePath }))
                    }
                    startActivity(intent)
                }
                "EditImage" -> {
                    val intent = Intent(this, EditActivity::class.java).apply {
                        putStringArrayListExtra("selectedImagePaths", ArrayList(listImageEditViewModel.images.value?.map { it.imagePath }))
                    }
                    startActivity(intent)
                }
                "Fitstruetosize" -> {
                    val intent = Intent(this, FitsImageActivity::class.java).apply {
                        putStringArrayListExtra("selectedImagePaths", ArrayList(listImageEditViewModel.images.value?.map { it.imagePath }))
                    }
                    startActivity(intent)
                }
                else -> {
                    Log.d(TAG, "onCreate: Unknown action $nameAction")
                }
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.returnActivity.setOnClickListener {
            finish()
        }
    }

    private fun getNameAction(): String? {
        val sharedPref = getSharedPreferences("app_prefs", MODE_PRIVATE)
        return sharedPref.getString("nameAction", null)
    }
}
