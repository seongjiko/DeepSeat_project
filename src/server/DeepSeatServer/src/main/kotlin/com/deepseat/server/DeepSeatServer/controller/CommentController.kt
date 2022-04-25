package com.deepseat.server.DeepSeatServer.controller

import com.deepseat.server.DeepSeatServer.dao.CommentDao
import com.deepseat.server.DeepSeatServer.error.Errors
import com.deepseat.server.DeepSeatServer.session.SessionConstants
import com.deepseat.server.DeepSeatServer.vo.Comment
import com.deepseat.server.DeepSeatServer.vo.User
import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
class CommentController {

    @Autowired
    private lateinit var commentDao: CommentDao

    @PostMapping("/comment/{docID}")
    fun addComment(
        request: HttpServletRequest,
        @PathVariable("docID") docID: Int,
        @RequestParam content: String
    ): String {
        val user: User
        try {
            user = request.getAttribute(SessionConstants.KEY_USER) as User
        } catch (e: Exception) {
            e.printStackTrace()
            return Gson().toJson(Errors.Companion.UserError.notSignedIn)
        }

        val comment = Comment(0, user.userID, docID, content, "", false)
        return if (commentDao.add(comment)) Gson().toJson(Errors.success)
        else Gson().toJson(Errors.Companion.DatabaseError.dbInsertFailure)
    }

    @GetMapping("/comment/{docID}")
    fun getCommentList(@PathVariable("docID") docID: Int): String {
        return Gson().toJson(commentDao.getList(docID))
    }

    @DeleteMapping("/comment/{commentID}")
    fun deleteComment(request: HttpServletRequest, @PathVariable("commentID") commentID: Int): String {
        val user: User
        try {
            user = request.getAttribute(SessionConstants.KEY_USER) as User
        } catch (e: Exception) {
            e.printStackTrace()
            return Gson().toJson(Errors.Companion.UserError.notSignedIn)
        }

        val comment = commentDao.get(commentID) ?: return Gson().toJson(Errors.Companion.DatabaseError.notExists)

        if (user.userID != comment.userID) return Gson().toJson(Errors.Companion.UserError.notAuthorized)

        return if (commentDao.delete(commentID)) Gson().toJson(Errors.success)
        else Gson().toJson(Errors.Companion.DatabaseError.dbDeleteFailure)
    }

    @PutMapping("/comment/{docID}/{commentID}")
    fun editComment(
        request: HttpServletRequest,
        @PathVariable("commentID") commentID: Int,
        @RequestParam content: String
    ): String {
        val user: User
        try {
            user = request.getAttribute(SessionConstants.KEY_USER) as User
        } catch (e: Exception) {
            e.printStackTrace()
            return Gson().toJson(Errors.Companion.UserError.notSignedIn)
        }

        val comment = commentDao.get(commentID) ?: return Gson().toJson(Errors.Companion.DatabaseError.notExists)

        if (user.userID != comment.userID) return Gson().toJson(Errors.Companion.UserError.notAuthorized)

        comment.content = content

        return if (commentDao.update(comment)) Gson().toJson(Errors.success)
        else Gson().toJson(Errors.Companion.DatabaseError.dbUpdateFailure)
    }

}