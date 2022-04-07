package com.deepseat.server.DeepSeatServer.model

class Room(roomID: Int, roomName: String) {
    var roomID: Int
    var roomName: String

    init {
        this.roomID = roomID
        this.roomName = roomName
    }
}