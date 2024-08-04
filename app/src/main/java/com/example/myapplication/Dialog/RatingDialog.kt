package com.example.myapplication.Dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import androidx.appcompat.widget.AppCompatButton
import com.example.myapplication.R

class RatingDialog(context: Context) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rating_dialog)

        val imgRating = findViewById<ImageView>(R.id.imgRating)
        val ratingBar = findViewById<RatingBar>(R.id.ratingBar)
        val btnRate = findViewById<AppCompatButton>(R.id.btnRating)
        val btnExit = findViewById<AppCompatButton>(R.id.btnExit)

        ratingBar.onRatingBarChangeListener = RatingBar.OnRatingBarChangeListener { _, rating, _ ->
            imgRating.setImageResource(getImageResource(rating.toInt()))
        }
        btnRate.setOnClickListener {
            dismiss()
        }

        btnExit.setOnClickListener {
            dismiss()
        }
    }
    private fun getImageResource(rating: Int): Int {
        return when (rating) {
            1 -> R.drawable.rating1
            2 -> R.drawable.rating2
            3 -> R.drawable.rating3
            4 -> R.drawable.rating4
            5 -> R.drawable.rating5
            else -> R.drawable.rating1
        }
    }
}
