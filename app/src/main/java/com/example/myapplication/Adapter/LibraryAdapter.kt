package com.example.myapplication.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.LibraryInterface
import com.example.myapplication.R
import com.example.myapplication.DataItem.ItemAll

class LibraryAdapter(private val items: List<ItemAll>, private val onClickFile: LibraryInterface) : RecyclerView.Adapter<LibraryAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageViewItem)
        val txtNameAlbum: TextView = view.findViewById(R.id.txtNameImage)
        val txtCount: TextView =view.findViewById(R.id.txtCountFile)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_item_all, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        Glide.with(holder.imageView.context)
            .load(item.imagePath)
            .into(holder.imageView)
        holder.txtNameAlbum.text = item.text
        holder.txtCount.text=item.count.toString()
        holder.itemView.setOnClickListener {
            onClickFile.onClickFile(position, holder.txtNameAlbum.text.toString())
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
