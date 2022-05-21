package com.deepseat.server.DeepSeatServer.vo

class Observation(
    var observationID: Int,
    var roomID: Int,
    var seatID: Int,
    var date: String,
    var time: String,
    var state: Int,
)