package com.deepseat.server.DeepSeatServer.vo

class Room(roomID: Int, roomName: String) {
    var roomID: Int
    var roomName: String

    init {
        this.roomID = roomID
        this.roomName = roomName
    }
}