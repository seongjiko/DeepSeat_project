package com.deepseat.server.DeepSeatServer.vo

class Observation(observationID: Int, roomID: Int, seatID: Int, date: String, state: Int) {
    var observationID: Int
    var roomID: Int
    var seatID: Int
    var date: String
    var state: Int

    init {
        this.observationID = observationID
        this.roomID = roomID
        this.seatID = seatID
        this.date = date
        this.state = state
    }

}