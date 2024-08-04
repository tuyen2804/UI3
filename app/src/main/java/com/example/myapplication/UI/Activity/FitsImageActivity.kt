package com.example.myapplication.UI.Activity

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.PopupWindow
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adapter.FitsImageAdapter
import com.example.myapplication.Adapter.PopupAdapter
import com.example.myapplication.Dialog.RatingDialog
import com.example.myapplication.Model.PopupModel
import com.example.myapplication.R
import com.example.myapplication.ViewModel.EditViewModel
import com.example.myapplication.databinding.ActivityFitsImageBinding

class FitsImageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFitsImageBinding
    private val viewModel: EditViewModel by viewModels()
    private lateinit var adapter: FitsImageAdapter
    private var popupWindow: PopupWindow? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFitsImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val selectedImagePaths = intent.getStringArrayListExtra("selectedImagePaths")
        selectedImagePaths?.let {
            viewModel.setImageData(it)
        }

        adapter = FitsImageAdapter(emptyList()) { view, position ->
        }

        viewModel.imageData.observe(this) { images ->
            adapter.updateItems(images)
        }

        binding.recyclerView.layoutManager = GridLayoutManager(this, 1)
        binding.recyclerView.adapter = adapter

        binding.btnCompression.setOnClickListener {
            showRatingDialog()
        }

        binding.btnOriginal.setOnClickListener {
            showPopupMenu(it, null) { selectedItem ->
                binding.txtTypeImage.text = selectedItem
            }
        }

        binding.returnActivity.setOnClickListener {
            finish()
        }
    }

    @SuppressLint("MissingInflatedId")
    private fun showPopupMenu(anchor: View, currentTypeImage: String?, onItemClick: (String) -> Unit) {
        val popupItems = listOf(
            PopupModel("png"),
            PopupModel("jpg"),
            PopupModel("heic"),
            PopupModel("webp"),
            PopupModel("jpec")
        ).filter { it.typeImage != currentTypeImage }

        val inflater = LayoutInflater.from(this)
        val view = inflater.inflate(R.layout.popup_menu, null)

        val recyclerViewPopup = view.findViewById<RecyclerView>(R.id.recyclerViewPopup)
        val adapter = PopupAdapter(popupItems) { selectedItem ->
            onItemClick(selectedItem)
            popupWindow?.dismiss()
        }
        recyclerViewPopup.layoutManager = LinearLayoutManager(this)
        recyclerViewPopup.adapter = adapter

        popupWindow = PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true)
        popupWindow!!.showAsDropDown(anchor)
    }

    private fun showRatingDialog() {
        val ratingDialog = RatingDialog(this)
        ratingDialog.show()
    }
    private fun openDialog(gravity: Int) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.rating_dialog)

        val window = dialog.window
        if (window != null) {
            window.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )

            val windowAttributes = window.attributes
            windowAttributes.gravity = gravity
            window.attributes = windowAttributes

            if (gravity == Gravity.BOTTOM) {
                dialog.setCancelable(true)
            }
        }

        dialog.show()
    }

}
