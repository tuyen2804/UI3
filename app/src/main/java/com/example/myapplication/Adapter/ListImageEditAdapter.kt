package com.example.myapplication.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.Model.ListImageEditModel
import com.example.myapplication.R

class ListImageEditAdapter(private var items: List<ListImageEditModel>) : RecyclerView.Adapter<ListImageEditAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView)
        val sizeImage: TextView = view.findViewById(R.id.sizeImage)
        val widthHeight: TextView = view.findViewById(R.id.width_height)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_list_image_edit, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        Glide.with(holder.imageView.context)
            .load(item.imagePath)
            .into(holder.imageView)
        holder.sizeImage.text = item.sizeImage.toString()
        holder.widthHeight.text = "${item.width} x ${item.height}"
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateItems(newItems: List<ListImageEditModel>) {
        items = newItems
        notifyDataSetChanged()
    }
}
