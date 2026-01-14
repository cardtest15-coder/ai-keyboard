package com.aikeyboard

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import java.util.Random

class AIKeyboardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : KeyboardView(context, attrs, defStyleAttr) {

    private val predictionPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val predictions = mutableListOf<String>()
    
    init {
        predictionPaint.color = ContextCompat.getColor(context, android.R.color.white)
        predictionPaint.textSize = 40f
        predictionPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    }

    fun updatePredictions(newPredictions: List<String>) {
        predictions.clear()
        predictions.addAll(newPredictions)
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        
        drawPredictions(canvas)
    }

    private fun drawPredictions(canvas: Canvas) {
        val margin = 20
        var x = margin.toFloat()
        val y = height - 50f
        
        predictions.forEach { prediction ->
            val textBounds = Rect()
            predictionPaint.getTextBounds(prediction, 0, prediction.length, textBounds)
            
            if (x + textBounds.width() > width - margin) {
                x = margin.toFloat()
            }
            
            canvas.drawText(prediction, x, y, predictionPaint)
            x += textBounds.width() + margin * 2
        }
    }
}