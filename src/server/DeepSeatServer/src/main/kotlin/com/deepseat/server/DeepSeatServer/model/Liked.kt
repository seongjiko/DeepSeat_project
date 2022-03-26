package com.deepseat.server.DeepSeatServer.model

class Liked(likedID: Int, userID: String, docID: Int, commentID: String) {
    var likedID: Int
    var userID: String
    var docID: Int
    var commentID: String

    init {
        this.likedID = likedID
        this.userID = userID
        this.docID = docID
        this.commentID = commentID
    }
}