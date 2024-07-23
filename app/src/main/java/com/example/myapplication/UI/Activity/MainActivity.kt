package com.example.myapplication.UI.Activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainBinding
import android.content.Intent
import com.example.myapplication.Adapter.RvAdapter
import com.example.myapplication.R

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
        binding.button3Container.setOnClickListener {

            val intent = Intent(this, PhotoLibraryActivity::class.java)
            startActivity(intent)
        }
        binding.button2Container.setOnClickListener {
            Toast.makeText(this,"hiện",Toast.LENGTH_LONG).show()
        }

        adapter = RvAdapter(items)
        binding.recyclerView.adapter = adapter

    }

}

