package com.deepseat.server.DeepSeatServer.service

import com.deepseat.server.DeepSeatServer.dao.CommentMapper
import com.deepseat.server.DeepSeatServer.vo.Comment
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CommentServiceImpl: CommentService {

    @Autowired
    private lateinit var commentMapper: CommentMapper

    override fun getComments(docID: Int): List<Comment> {
        return commentMapper.getComments(docID)
    }

    override fun getCommentById(commentID: Int): Comment? {
        return commentMapper.getCommentById(commentID)
    }

    override fun insertComment(comment: Comment) {
        commentMapper.insertComment(comment)
    }

    override fun deleteCommentById(commentID: Int) {
        commentMapper.deleteCommentById(commentID)
    }

    override fun updateCommentById(comment: Comment) {
        commentMapper.updateCommentById(comment)
    }
}