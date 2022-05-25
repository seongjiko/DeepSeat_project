package com.deepseat.ds.component

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.deepseat.ds.R
import com.deepseat.ds.model.Seat
import com.deepseat.ds.vo.Observation
import kotlinx.coroutines.delay

class SeatView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var seats: ArrayList<Seat> = arrayListOf()
    private var observations: HashMap<Int, Observation> = hashMapOf()

    fun setData(seats: Array<Seat>, observations: Array<Observation>) {
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

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        for (seat in seats) {
            val rect = Rect(seat.minX, seat.minY, seat.maxX, seat.maxY)
            val paint = when (observations[seat.seatID]!!.state) {
                0 -> paintEmpty
                1 -> paintUsing
                2 -> paintAbsent
                3 -> paintLongAbsent
                else -> paintEmpty
            }
            canvas?.drawRect(rect, paint)
            Log.e("seatview", "${rect.left}, ${rect.top}, ${rect.right}, ${rect.bottom}")
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

    private fun buildBitmap() {
        val bitmap = Bitmap.createBitmap(640, 480, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        draw(canvas)

    }

}