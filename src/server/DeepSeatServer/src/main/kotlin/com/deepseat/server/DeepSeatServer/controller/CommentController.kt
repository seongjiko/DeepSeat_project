package com.deepseat.server.DeepSeatServer.controller

import com.deepseat.server.DeepSeatServer.error.Errors
import com.deepseat.server.DeepSeatServer.service.CommentService
import com.deepseat.server.DeepSeatServer.session.SessionConstants
import com.deepseat.server.DeepSeatServer.tool.ResponseBodyBuilder
import com.deepseat.server.DeepSeatServer.vo.Comment
import com.deepseat.server.DeepSeatServer.vo.User
import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
class CommentController {

    @Autowired
    private lateinit var service: CommentService

    @PostMapping("/comment/{docID}")
    fun addComment(
        request: HttpServletRequest,
        @PathVariable("docID") docID: Int,
        @RequestParam content: String
    ): String {
        val user: User
        try {
            user = request.session.getAttribute(SessionConstants.KEY_USER) as User
        } catch (e: Exception) {
            e.printStackTrace()
            return ResponseBodyBuilder<Void>(Errors.Companion.UserError.notSignedIn).toString()
        }

        val comment = Comment(0, user.userID, docID, content, "", false)
        service.insertComment(comment)

        return ResponseBodyBuilder<Void>().toString()
    }

    @GetMapping("/comment/{docID}")
    fun getCommentList(@PathVariable("docID") docID: Int): List<Comment> {
        return service.getComments(docID)
    }

    @DeleteMapping("/comment/{commentID}")
    fun deleteComment(request: HttpServletRequest, @PathVariable("commentID") commentID: Int): String {
        val user: User
        try {
            user = request.session.getAttribute(SessionConstants.KEY_USER) as User
        } catch (e: Exception) {
            e.printStackTrace()
            return ResponseBodyBuilder<Void>(Errors.Companion.UserError.notSignedIn).toString()
        }

        val comment = service.getCommentById(commentID)

        if (user.userID != comment?.userID) return ResponseBodyBuilder<Void>(Errors.Companion.UserError.notAuthorized).toString()

        service.deleteCommentById(commentID)

        return ResponseBodyBuilder<Void>().toString()
    }

    @PutMapping("/comment/{docID}/{commentID}")
    fun editComment(
        request: HttpServletRequest,
        @PathVariable("commentID") commentID: Int,
        @RequestParam content: String
    ): String {
        val user: User
        try {
            user = request.session.getAttribute(SessionConstants.KEY_USER) as User
        } catch (e: Exception) {
            e.printStackTrace()
            return ResponseBodyBuilder<Void>(Errors.Companion.UserError.notSignedIn).toString()
        }

        val comment = service.getCommentById(commentID)

        if (user.userID != comment?.userID) return ResponseBodyBuilder<Void>(Errors.Companion.UserError.notAuthorized).toString()

        comment.content = content
        service.updateCommentById(comment)

        return ResponseBodyBuilder<Void>().toString()
    }

}