package com.example.myapplication.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.Model.ImageModel
import com.example.myapplication.R

class ImageAdapter(private var items: List<ImageModel>) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageViewItem)
        val checkBox: CheckBox = view.findViewById(R.id.cbImage)
        val sizeTextView: TextView = view.findViewById(R.id.sizeImage)
        val imageBorder: ImageView = view.findViewById(R.id.imageBorder)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_item_image, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        Glide.with(holder.imageView.context)
            .load(item.imagePath)
            .into(holder.imageView)
        holder.checkBox.isChecked = item.isChecked
        holder.sizeTextView.text = item.size
        holder.imageBorder.visibility = if (item.isChecked) View.VISIBLE else View.GONE

        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            item.isChecked = isChecked
            holder.imageBorder.visibility = if (isChecked) View.VISIBLE else View.GONE
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateItems(newItems: List<ImageModel>) {
        items = newItems
        notifyDataSetChanged()
    }

    fun getSelectedImagePaths(): List<String> {
        return items.filter { it.isChecked }.map { it.imagePath }
    }
}
