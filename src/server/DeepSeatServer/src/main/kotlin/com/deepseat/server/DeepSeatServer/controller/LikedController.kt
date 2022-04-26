package com.deepseat.server.DeepSeatServer.controller

import com.deepseat.server.DeepSeatServer.dao.LikedDao
import com.deepseat.server.DeepSeatServer.error.Errors
import com.deepseat.server.DeepSeatServer.session.SessionConstants
import com.deepseat.server.DeepSeatServer.vo.Liked
import com.deepseat.server.DeepSeatServer.vo.User
import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
class LikedController {

    @Autowired
    private lateinit var likedDao: LikedDao

    @PostMapping("/liked/{docID}")
    fun liked(request: HttpServletRequest, @PathVariable("docID") docID: Int) {

        val userID = (request.session.getAttribute(SessionConstants.KEY_USER) as? String) ?: return

        val liked = Liked(userID = userID, docID = docID)
        likedDao.add(liked)
    }

    @PostMapping("/liked/{roomID}/{seatID}/{docID}/{commentID}")
    fun liked(
        request: HttpServletRequest,
        @PathVariable("roomID") roomID: Int,
        @PathVariable("seatID") seatID: Int,
        @PathVariable("docID") docID: Int,
        @PathVariable("commentID") commentID: Int
    ): String {

        val session = request.session
        val user = (session.getAttribute(SessionConstants.KEY_USER) as? User)
            ?: return Gson().toJson(Errors.Companion.UserError.notRegistered)

        val liked = Liked(0, user.userID, docID, commentID)
        if (likedDao.add(liked)) {
            return Gson().toJson(Errors.success)
        } else {
            return Gson().toJson(Errors.Companion.DatabaseError.dbInsertFailure)
        }
    }

    @GetMapping("/liked/{roomID}/{seatID}/{docID}")
    fun getLiked(
        @PathVariable("roomID") roomID: Int,
        @PathVariable("seatID") seatID: Int,
        @PathVariable("docID") docID: Int
    ) {
        likedDao.getList(docID, -1)
    }

    @GetMapping("/liked/{roomID}/{seatID}/{docID}/{commentID}")
    fun getLiked(
        @PathVariable("roomID") roomID: Int,
        @PathVariable("seatID") seatID: Int,
        @PathVariable("docID") docID: Int,
        @PathVariable("commentID") commentID: Int
    ) {
        likedDao.getList(-1, commentID)
    }

    @DeleteMapping("/liked/{roomID}/{seatID}/{docID}/{likedID}")
    fun unliked(
        request: HttpServletRequest,
        @PathVariable("roomID") roomID: Int,
        @PathVariable("seatID") seatID: Int,
        @PathVariable("docID") docID: Int,
        @PathVariable("likedID") likedID: Int
    ): String {

        val session = request.session
        val user = (session.getAttribute(SessionConstants.KEY_USER) as? User)
            ?: return Gson().toJson(Errors.Companion.UserError.notRegistered)

        val like = likedDao.get(likedID) ?: return Gson().toJson(Errors.Companion.DatabaseError.notExists)

        if (user.userID != like.userID) return Gson().toJson(Errors.Companion.UserError.notAuthorized)

        return if ( likedDao.delete(likedID)) {
            Gson().toJson(Errors.success)
        } else {
            Gson().toJson(Errors.Companion.DatabaseError.dbInsertFailure)
        }
    }

    @DeleteMapping("/liked/{roomID}/{seatID}/{docID}/{commentID}/{likedID}")
    fun unliked(
        request: HttpServletRequest,
        @PathVariable("roomID") roomID: Int,
        @PathVariable("seatID") seatID: Int,
        @PathVariable("docID") docID: Int,
        @PathVariable("commentID") commentID: Int,
        @PathVariable("likedID") likedID: Int
    ): String {
        val session = request.session
        val user = (session.getAttribute(SessionConstants.KEY_USER) as? User)
            ?: return Gson().toJson(Errors.Companion.UserError.notRegistered)

        val like = likedDao.get(likedID) ?: return Gson().toJson(Errors.Companion.DatabaseError.notExists)

        if (user.userID != like.userID) return Gson().toJson(Errors.Companion.UserError.notAuthorized)

        return if ( likedDao.delete(likedID)) {
            Gson().toJson(Errors.success)
        } else {
            Gson().toJson(Errors.Companion.DatabaseError.dbInsertFailure)
        }
    }

}