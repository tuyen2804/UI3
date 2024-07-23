package com.example.myapplication.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class RvAdapter(private val items: List<Item>) : RecyclerView.Adapter<RvAdapter.ViewHolder>() {

    data class Item(val imageResId: Int, val text: String)

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageViewRight)
        private val textView1: TextView = itemView.findViewById(R.id.textView1)
        private val textView2: TextView = itemView.findViewById(R.id.textView2)

        fun bind(item: Item) {
            imageView.setImageResource(item.imageResId)
            textView1.text = item.text
            textView2.text = "File ${item.text}" // Thay đổi mô tả tùy theo item nếu cần
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recycler_view, parent, false)
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
