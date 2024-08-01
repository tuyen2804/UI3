package com.example.myapplication.Adapter

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Model.ListPopupModel
import com.example.myapplication.R

class PopupAdapter(private val items: List<ListPopupModel>, private val onItemClick: (String) -> Unit) : RecyclerView.Adapter<PopupAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.txtTypeImage)
        val itemPopup: LinearLayout = view.findViewById(R.id.itemPopup)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_popupmenu, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.textView.text = item.typeImage
        holder.itemPopup.setOnClickListener {
            onItemClick(item.typeImage)
            Log.d(TAG, "onBindViewHolder: ${item.typeImage}")
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
