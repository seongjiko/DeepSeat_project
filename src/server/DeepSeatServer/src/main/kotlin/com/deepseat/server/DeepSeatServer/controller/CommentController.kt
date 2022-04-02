package com.deepseat.server.DeepSeatServer.controller

import com.deepseat.server.DeepSeatServer.dao.CommentDao
import com.deepseat.server.DeepSeatServer.model.Comment
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class CommentController {

    @PostMapping("/{roomID}/{seatID}/{docID}")
    fun addComment(@PathVariable("roomID") roomID: Int, @PathVariable("seatID") seatID: Int, @PathVariable("docID") docID: Int, @RequestParam content: String) {
        val commentDao = CommentDao()
        val comment = Comment(0, "", docID, content, "", false)
        commentDao.add(comment)
    }

    @ResponseBody
    @GetMapping("/{roomID}/{seatID}/{docID}")
    fun getCommentList(@PathVariable("roomID") roomID: Int, @PathVariable("seatID") seatID: Int, @PathVariable("docID") docID: Int): Array<Comment> {
        val commentDao = CommentDao()
        return commentDao.getList(roomID, seatID, docID)
    }

    @DeleteMapping("/{roomID}/{seatID}/{docID}/{commentID}")
    fun deleteComment(@PathVariable("roomID") roomID: Int, @PathVariable("seatID") seatID: Int, @PathVariable("docID") docID: Int, @PathVariable("commentID") commentID: Int) {
        val commentDao = CommentDao()
        commentDao.delete(roomID, seatID, docID, commentID)
    }

    @PutMapping("/{roomID}/{seatID}/{docID}/{commentID}")
    fun editComment(@PathVariable("roomID") roomID: Int, @PathVariable("seatID") seatID: Int, @PathVariable("docID") docID: Int, @PathVariable("commentID") commentID: Int, @RequestParam content: String) {
        val commentDao = CommentDao()
        val comment = Comment(commentID, "", docID, content, "", true)
        commentDao.update(comment)
    }

}