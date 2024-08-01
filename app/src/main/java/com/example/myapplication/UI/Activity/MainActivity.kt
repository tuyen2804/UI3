package com.example.myapplication.UI.Activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Adapter.RvAdapter
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: RvAdapter

    private val items = listOf(
        RvAdapter.Item(R.drawable.ic_launcher_foreground, "Item 1"),
        RvAdapter.Item(R.drawable.ic_launcher_foreground, "Item 2"),
        RvAdapter.Item(R.drawable.ic_launcher_foreground, "Item 3"),
        RvAdapter.Item(R.drawable.ic_launcher_foreground, "Item 4")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPreCompression.setOnClickListener {
            val intent = Intent(this, PhotoLibraryActivity::class.java).apply {
                putExtra("button_name", "PreCompression")
            }
            startActivity(intent)
        }

        binding.btnEdit.setOnClickListener {
            val intent = Intent(this, PhotoLibraryActivity::class.java).apply {
                putExtra("button_name", "EditImage")
            }
            startActivity(intent)
        }

        adapter = RvAdapter(items)
        binding.recyclerView.adapter = adapter
    }
}
