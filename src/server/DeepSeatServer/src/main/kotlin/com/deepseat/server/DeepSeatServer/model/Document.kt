package com.deepseat.server.DeepSeatServer.model

class Document(docID: Int, userID: String, roomID: Int, seatID: Int,content: String, wrote: String, edited: Boolean) {
    var docID: Int
    var userID: String
    var roomID: Int
    var seatID: Int
    var content: String
    var wrote: String
    var edited: Boolean

    init {
        this.docID = docID
        this.userID = userID
        this.roomID = roomID
        this.seatID = seatID
        this.content = content
        this.wrote = wrote
        this.edited = edited
    }
}