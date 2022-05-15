package com.deepseat.ds.model

class Document(
    var docId: Int,
    var userId: String,
    var roomId: String,
    var seatId: String,
    var content: String,
    var wrote: String,
    var edited: Boolean
) {
}