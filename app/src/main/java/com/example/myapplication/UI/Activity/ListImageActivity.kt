
package com.example.myapplication.UI.Activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.Adapter.ListImageEditAdapter
import com.example.myapplication.ViewModel.ListImageEditViewModel
import com.example.myapplication.databinding.ActivityListImageBinding

class ListImageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListImageBinding
    private val viewModel: ListImageEditViewModel by viewModels()
    private lateinit var adapter: ListImageEditAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val selectedImagePaths = intent.getStringArrayListExtra("selectedImagePaths")

        adapter = ListImageEditAdapter(emptyList()) { image ->
            viewModel.removeImage(image)
        }
        binding.recyclerView.layoutManager = GridLayoutManager(this, 3)
        binding.recyclerView.adapter = adapter

        viewModel.images.observe(this) { images ->
            adapter.updateItems(images)
        }

        selectedImagePaths?.let {
            viewModel.loadImages(it)
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnNext.setOnClickListener {
            val intent = Intent(this, PreCompressionActivity::class.java).apply {
                putStringArrayListExtra("selectedImagePaths", ArrayList(viewModel.images.value?.map { it.imagePath }))
            }
            startActivity(intent)
        }
        binding.returnActivity.setOnClickListener(){
            finish()
        }
    }
}
