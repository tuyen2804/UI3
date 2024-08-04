package com.example.myapplication.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.Model.EditModel
import com.example.myapplication.R

class FitsImageAdapter(var itemEdits: List<EditModel>,private var clickPopupMenu:(View,Int)->Unit): RecyclerView.Adapter<FitsImageAdapter.ViewHolder>() {

    private var selectedPosition: Int = RecyclerView.NO_POSITION

    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        var imageView: ImageView=view.findViewById(R.id.imgEdit)
        var sizeImage: TextView=view.findViewById(R.id.sizeImage)
        var width_height: TextView=view.findViewById(R.id.width_height)
        var popupMenu:LinearLayout=view.findViewById(R.id.popupMenu)
        var imgBorder:ImageView=view.findViewById(R.id.imageBorder)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FitsImageAdapter.ViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.layout_fits_image,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: FitsImageAdapter.ViewHolder, position: Int) {
        val item = itemEdits[position]

        Glide.with(holder.imageView.context)
            .load(item.imagePath)
            .into(holder.imageView)
        holder.sizeImage.text = item.sizeImage
        holder.width_height.text = "${item.width} x ${item.height}"

        val isSelected = holder.adapterPosition == selectedPosition
        holder.imgBorder.visibility = if (isSelected) View.VISIBLE else View.GONE


        holder.itemView.setOnClickListener {
            val previousPosition = selectedPosition
            selectedPosition = holder.adapterPosition
            notifyItemChanged(previousPosition)
            notifyItemChanged(selectedPosition)
        }
        holder.popupMenu.setOnClickListener {
            clickPopupMenu(it, holder.adapterPosition)
            val previousPosition = selectedPosition
            selectedPosition = holder.adapterPosition
            notifyItemChanged(previousPosition)
            notifyItemChanged(selectedPosition)
        }
    }

    override fun getItemCount(): Int = itemEdits.size
    fun updateItems(newItems: List<EditModel>) {
        itemEdits = newItems
        notifyDataSetChanged()
    }
}