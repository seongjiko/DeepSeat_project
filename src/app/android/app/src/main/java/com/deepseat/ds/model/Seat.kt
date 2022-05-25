package com.deepseat.ds.model

class Seat(
    var seatID: Int,
    var roomID: Int,
    var x: Int = 0,
    var y: Int = 0,
    var width: Int = 0,
    var height: Int = 0
) {

    val minX: Int get() = x - width / 2
    val minY: Int get() = y - height / 2
    val maxX: Int get() = x + width / 2
    val maxY: Int get() = y + height / 2


}