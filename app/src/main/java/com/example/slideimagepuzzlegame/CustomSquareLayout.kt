package com.example.slideimagepuzzlegame

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout


class CustomSquareLayout(context: Context?, attrs: AttributeSet) :
    RelativeLayout(context, attrs) {
    private val heightRatio: Float
    private var maxHeight: Int
    fun setMaxHeight(maxHeight: Int) {
        this.maxHeight = maxHeight
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        // We have to manually resize the child views to match the parent.
        for (i in 0 until childCount) {
            val child: View = getChildAt(i)
            val params = child.getLayoutParams() as LayoutParams
            if (params.height == LayoutParams.MATCH_PARENT) {
                params.height = bottom - top
            }
        }
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = MeasureSpec.getSize(widthMeasureSpec)
        var height = Math.ceil((heightRatio * width.toFloat()).toDouble()).toInt()
        if (maxHeight != -1 && height > maxHeight) height = maxHeight
        setMeasuredDimension(width, height)
    }

    init {
        heightRatio = attrs.getAttributeFloatValue(null, "heightRatio", 1.0f)
        maxHeight = -1
    }
}