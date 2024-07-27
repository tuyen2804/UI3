package com.example.myapplication.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.Model.ListEditModel
import com.example.myapplication.R

class ListEditAdapter(private var itemEdits: List<ListEditModel>) : RecyclerView.Adapter<ListEditAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imageView: ImageView = view.findViewById(R.id.imgEdit)
        var sizeImageView: TextView = view.findViewById(R.id.sizeImage)
        var widthHeight: TextView = view.findViewById(R.id.width_height)
        var typeImage: TextView = view.findViewById(R.id.typeImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_list_bypixel, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemEdits[position]
        Glide.with(holder.imageView.context).load(item.imagePath).into(holder.imageView)
        holder.sizeImageView.text = item.sizeImage
        holder.widthHeight.text = "${item.width} x ${item.height}"
        holder.typeImage.text = item.typeImage
    }

    override fun getItemCount(): Int = itemEdits.size

    fun updateItems(newItems: List<ListEditModel>) {
        itemEdits = newItems
        notifyDataSetChanged()
    }
}
