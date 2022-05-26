package com.deepseat.ds.component

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.icu.number.Scale
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import com.deepseat.ds.R
import com.deepseat.ds.model.Seat
import com.deepseat.ds.vo.Observation

class SeatView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var seats: ArrayList<Seat> = arrayListOf()
    private var observations: HashMap<Int, Observation> = hashMapOf()

    fun drawSeats(seats: Array<Seat>, observations: Array<Observation>) {
        this.seats.clear()
        this.observations.clear()
        this.seats.addAll(seats)
        this.seats.forEach { seat ->
            observations.forEach { obs ->
                if (seat.seatID == obs.seatID) {
                    this.observations[seat.seatID] = obs
                }
            }
        }

        buildBitmap()
    }

    private fun buildBitmap() {
        invalidate()
        val bitmap = Bitmap.createBitmap(640, 480, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        draw(canvas)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.scale(2.0f, 2.0f)

        for (seat in seats) {
            val rect = Rect(seat.minX, seat.minY, seat.maxX, seat.maxY)
            observations[seat.seatID]?.let {
                val paint = when (it.state) {
                    0 -> paintEmpty
                    1 -> paintUsing
                    2 -> paintAbsent
                    3 -> paintLongAbsent
                    else -> paintError
                }
                canvas?.drawRect(rect, paint)
            }
            Log.e("seatview", "${rect.left}, ${rect.top}, ${rect.right}, ${rect.bottom}")
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val modeWidth = MeasureSpec.getMode(widthMeasureSpec)
        val modeHeight = MeasureSpec.getMode(heightMeasureSpec)

        val sizeWidth = MeasureSpec.getSize(widthMeasureSpec)
        val sizeHeight = MeasureSpec.getSize(heightMeasureSpec)

        if (modeWidth == MeasureSpec.EXACTLY && modeHeight == MeasureSpec.EXACTLY) {
            setMeasuredDimension(sizeWidth, sizeHeight)
        } else {
            setMeasuredDimension(640 * 2, 480 * 2)
        }
    }

    private val paintEmpty = Paint().apply {
        this.isAntiAlias = true
        this.color = context.getColor(R.color.color_empty)
        this.style = Paint.Style.FILL
        this.strokeWidth = 0f
    }

    private val paintUsing = Paint().apply {
        this.isAntiAlias = true
        this.color = context.getColor(R.color.color_using)
        this.style = Paint.Style.FILL
        this.strokeWidth = 0f
    }

    private val paintAbsent = Paint().apply {
        this.isAntiAlias = true
        this.color = context.getColor(R.color.color_absent)
        this.style = Paint.Style.FILL
        this.strokeWidth = 0f
    }

    private val paintLongAbsent = Paint().apply {
        this.isAntiAlias = true
        this.color = context.getColor(R.color.color_absent_long)
        this.style = Paint.Style.FILL
        this.strokeWidth = 0f
    }

    private val paintError = Paint().apply {
        this.isAntiAlias = true
        this.color = context.getColor(R.color.color_error)
        this.style = Paint.Style.FILL
        this.strokeWidth = 0f
    }
}