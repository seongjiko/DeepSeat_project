package com.deepseat.server.DeepSeatServer.model

class Seat(seatID: Int, roomID: Int, x: Int, y: Int, width: Int, height: Int) {
    var seatID: Int
    var roomID: Int
    var x: Int
    var y: Int
    var width: Int
    var height: Int

    init {
        this.seatID = seatID
        this.roomID = roomID
        this.x = x
        this.y = y
        this.width = width
        this.height = height
    }
}