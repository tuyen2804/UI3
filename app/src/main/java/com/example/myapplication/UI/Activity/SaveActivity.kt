package com.example.myapplication.UI.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.Adapter.SaveAdapter
import com.example.myapplication.Model.SaveModel
import com.example.myapplication.R

class SaveActivity : AppCompatActivity(), SaveAdapter.OnItemClickListener {

    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: SaveAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save)

        val mainView = findViewById<ViewPager2>(R.id.viewpager)
        val prevButton: ImageView = findViewById(R.id.btn1)
        val nextButton: ImageView = findViewById(R.id.btn2)
        ViewCompat.setOnApplyWindowInsetsListener(mainView) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewPager = findViewById(R.id.viewpager)
        val imagePaths = intent.getStringArrayListExtra("selectedImagePaths")
        val items = imagePaths!!.map { path ->
            SaveModel(
                imagePath = path,
                sizeImageOld = "1MB",
                widthOld = 1920,
                heightOld = 1080,
                sizeImageNew = "500KB",
                widthNew = 1280,
                heightNew = 720
            )
        }
        adapter = SaveAdapter(items, this)
        viewPager.adapter = adapter

        prevButton.setOnClickListener {
            val currentItem = viewPager.currentItem
            if (currentItem > 0) {
                viewPager.setCurrentItem(currentItem - 1, true)
            }
        }

        nextButton.setOnClickListener {
            val currentItem = viewPager.currentItem
            if (currentItem < adapter.itemCount - 1) {
                viewPager.setCurrentItem(currentItem + 1, true)
            }
        }
    }

    override fun onItemClick(imagePath: String) {
        val intent = Intent(this, ZoomActivity::class.java)
        intent.putExtra("imagePath", imagePath)
        startActivity(intent)
    }
}
