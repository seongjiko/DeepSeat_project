package com.deepseat.server.DeepSeatServer.vo

import com.google.gson.JsonObject
import com.google.gson.JsonParser

class Seat() {
    var seatID: Int = 0
    var roomID: Int = 0
    var x: Int = 0
    var y: Int = 0
    var width: Int = 0
    var height: Int = 0

    constructor(seatID: Int, roomID: Int, x: Int, y: Int, width: Int, height: Int) : this() {
        this.seatID = seatID
        this.roomID = roomID
        this.x = x
        this.y = y
        this.width = width
        this.height = height
    }

    constructor(jsonString: String) : this() {
        val element = JsonParser.parseString(jsonString) as JsonObject
//        val element.get("")
    }
}