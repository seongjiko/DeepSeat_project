package com.deepseat.server.DeepSeatServer.vo

class Comment(commentID: Int, userID: String, docID: Int, content: String, wrote: String, edited: Boolean) {
    var commentID: Int
    var userID: String
    var docID: Int
    var content: String
    var wrote: String
    var edited: Boolean

    init {
        this.commentID = commentID
        this.userID = userID
        this.docID = docID
        this.content = content
        this.wrote = wrote
        this.edited = edited
    }
}