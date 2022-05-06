package com.deepseat.server.DeepSeatServer.dao

import com.deepseat.server.DeepSeatServer.vo.Comment
import org.springframework.stereotype.Repository

@Repository
interface CommentMapper {

    fun getComments(docID: Int): List<Comment>
    fun getCommentById(commentID: Int): Comment?
    fun insertComment(comment: Comment)
    fun deleteCommentById(commentID: Int)
    fun updateCommentById(comment: Comment)

}