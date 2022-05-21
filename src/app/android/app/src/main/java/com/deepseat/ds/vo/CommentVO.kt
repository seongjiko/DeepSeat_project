package com.deepseat.ds.vo

class CommentVO(
    var commentID: Int,
    var docID: Int,
    var userID: String,
    var nickname: String,
    var content: String,
    var wrote: String,
    var iLiked: Boolean,
    var liked: Int
)