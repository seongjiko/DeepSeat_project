package com.deepseat.server.DeepSeatServer.vo

class Liked(likedID: Int, userID: String, docID: Int?, commentID: Int?) {
    var likedID: Int
    var userID: String
    var docID: Int?
    var commentID: Int?

    init {
        this.likedID = likedID
        this.userID = userID
        this.docID = docID
        this.commentID = commentID
    }

    constructor(userID: String, docID: Int? = null, commentID: Int? = null) : this(0, userID, docID, commentID)
}