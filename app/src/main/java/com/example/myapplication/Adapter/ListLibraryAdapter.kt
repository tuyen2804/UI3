package com.example.myapplication.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.DataItem.ListLibrary
import com.example.myapplication.R
import androidx.fragment.app.Fragment

class ListLibraryAdapter(
    private val items: List<ListLibrary>,
) : RecyclerView.Adapter<ListLibraryAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView1)
        val txtNameAlbum: TextView = view.findViewById(R.id.txtNameType)
        fun bind(item: ListLibrary) {
            imageView.setImageResource(R.drawable.chevronrights)
            txtNameAlbum.text = item.txtNameLibrary
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_item_fragmentlibrary, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
