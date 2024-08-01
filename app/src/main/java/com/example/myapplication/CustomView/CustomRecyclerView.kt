package com.example.myapplication.CustomView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class CustomRecyclerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    private val paint: Paint = Paint()

    init {
        // Tùy chỉnh màu sắc và độ dày của line
        paint.color = ContextCompat.getColor(context, R.color.default_background)
        paint.strokeWidth = context.resources.getDimension(R.dimen.divider_height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawDividers(canvas)
    }

    private fun drawDividers(canvas: Canvas) {
        val left = paddingLeft
        val right = width - paddingRight

        val childCount = childCount
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin
            val bottom = top + paint.strokeWidth

            canvas.drawLine(left.toFloat(), top.toFloat(), right.toFloat(), bottom, paint)
        }
    }
}
