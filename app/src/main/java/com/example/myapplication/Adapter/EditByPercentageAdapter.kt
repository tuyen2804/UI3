package com.example.myapplication.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.Model.EditModel
import com.example.myapplication.R

class EditByPercentageAdapter (
    var itemEdits: List<EditModel>,
    private val layoutId: Int
) : RecyclerView.Adapter<EditByPercentageAdapter.ViewHolder>() {

    private var selectedPosition: Int = RecyclerView.NO_POSITION

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imageView: ImageView = view.findViewById(R.id.imgEdit)
        var sizeImageView: TextView = view.findViewById(R.id.sizeImage)
        var widthHeight: TextView = view.findViewById(R.id.width_height)
        val imgBorder: ImageView = view.findViewById(R.id.imageBorder)
        var txtProgress: TextView = view.findViewById(R.id.txtProgress)
        val seekBar: SeekBar =view.findViewById(R.id.seekbarEdit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemEdits[position]

        Glide.with(holder.imageView.context)
            .load(item.imagePath)
            .into(holder.imageView)
        holder.sizeImageView.text = item.sizeImage
        holder.widthHeight.text = "${item.width} x ${item.height}"

        val isSelected = holder.adapterPosition == selectedPosition
        holder.imgBorder.visibility = if (isSelected) View.VISIBLE else View.GONE


        holder.itemView.setOnClickListener {
            val previousPosition = selectedPosition
            selectedPosition = holder.adapterPosition
            notifyItemChanged(previousPosition)
            notifyItemChanged(selectedPosition)
        }
        holder.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                holder.txtProgress.text="$p1%"
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }


        })
    }

    override fun getItemCount(): Int = itemEdits.size

    fun updateItems(newItems: List<EditModel>) {
        itemEdits = newItems
        notifyDataSetChanged()
    }
}
