package com.example.myapplication.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.Model.SaveModel
import com.example.myapplication.R

class SaveAdapter(private val items: List<SaveModel>, private val listener: OnItemClickListener) : RecyclerView.Adapter<SaveAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val sizeOld: TextView = itemView.findViewById(R.id.sizeOld)
        private val sizeNew: TextView = itemView.findViewById(R.id.sizeNew)
        private val widthHeightOld: TextView = itemView.findViewById(R.id.width_heightOld)
        private val widthHeightNew: TextView = itemView.findViewById(R.id.width_heightNew)
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)
        private val imagePath: TextView = itemView.findViewById(R.id.imgPath)
        private val btnZoom: ImageView = itemView.findViewById(R.id.btnZoom)

        fun bind(item: SaveModel) {
            sizeOld.text = item.sizeImageOld
            sizeNew.text = item.sizeImageNew
            widthHeightOld.text = "${item.widthOld}x${item.heightOld}"
            widthHeightNew.text = "${item.widthNew}x${item.heightNew}"
            imagePath.text = item.imagePath
            Glide.with(itemView.context).load(item.imagePath).into(imageView)

            btnZoom.setOnClickListener {
                listener.onItemClick(item.imagePath)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_saved, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    interface OnItemClickListener {
        fun onItemClick(imagePath: String)
    }
}
