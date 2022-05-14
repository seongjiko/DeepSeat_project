package com.deepseat.server.DeepSeatServer.service

import com.deepseat.server.DeepSeatServer.vo.Comment

interface CommentService {

    fun getComments(docID: Int): List<Comment>
    fun getCommentById(commentID: Int): Comment?
    fun insertComment(comment: Comment)
    fun deleteCommentById(commentID: Int)
    fun updateCommentById(comment: Comment)

}