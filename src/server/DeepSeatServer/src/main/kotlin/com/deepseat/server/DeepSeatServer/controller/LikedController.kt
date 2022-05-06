package com.deepseat.server.DeepSeatServer.controller

import com.deepseat.server.DeepSeatServer.error.Errors
import com.deepseat.server.DeepSeatServer.service.LikedService
import com.deepseat.server.DeepSeatServer.session.SessionConstants
import com.deepseat.server.DeepSeatServer.tool.ResponseBodyBuilder
import com.deepseat.server.DeepSeatServer.vo.Liked
import com.deepseat.server.DeepSeatServer.vo.User
import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
class LikedController {

    @Autowired
    private lateinit var service: LikedService

    @PostMapping("/liked/{docID}")
    fun liked(request: HttpServletRequest, @PathVariable("docID") docID: Int): String {

        val userID = (request.session.getAttribute(SessionConstants.KEY_USER) as? String)
            ?: return ResponseBodyBuilder<Void>(Errors.Companion.UserError.notSignedIn).toString()

        val liked = Liked(userID = userID, docID = docID)
        service.insertLike(liked)

        return ResponseBodyBuilder<Void>().toString()
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
            ?: return ResponseBodyBuilder<Void>(Errors.Companion.UserError.notSignedIn).toString()

        val liked = Liked(0, user.userID, docID, commentID)
        service.insertLike(liked)

        return ResponseBodyBuilder<Void>().toString()
    }

    @GetMapping("/liked/{roomID}/{seatID}/{docID}")
    fun getLiked(
        @PathVariable("roomID") roomID: Int,
        @PathVariable("seatID") seatID: Int,
        @PathVariable("docID") docID: Int
    ): String {
        return ResponseBodyBuilder<Int>().data(service.getLikedCountOfDocument(docID)).toString()
    }

    @GetMapping("/liked/{roomID}/{seatID}/{docID}/{commentID}")
    fun getLiked(
        @PathVariable("roomID") roomID: Int,
        @PathVariable("seatID") seatID: Int,
        @PathVariable("docID") docID: Int,
        @PathVariable("commentID") commentID: Int
    ): String {
        return ResponseBodyBuilder<Int>().data(service.getLikedCountOfComment(commentID)).toString()
    }

    @DeleteMapping("/liked/{roomID}/{seatID}/{docID}")
    fun unliked(
        request: HttpServletRequest,
        @PathVariable("roomID") roomID: Int,
        @PathVariable("seatID") seatID: Int,
        @PathVariable("docID") docID: Int
    ): String {

        val session = request.session
        val user = (session.getAttribute(SessionConstants.KEY_USER) as? User)
            ?: return ResponseBodyBuilder<Void>(Errors.Companion.UserError.notSignedIn).toString()

        val like = service.getLikedOfDocument(docID, user.userID)
            ?: return ResponseBodyBuilder<Void>(Errors.Companion.DatabaseError.notExists).toString()

        if (user.userID != like.userID)
            return ResponseBodyBuilder<Void>(Errors.Companion.UserError.notAuthorized).toString()

        service.deleteLike(like.likedID)

        return ResponseBodyBuilder<Void>().toString()
    }

    @DeleteMapping("/liked/{roomID}/{seatID}/{docID}/{commentID}/")
    fun unliked(
        request: HttpServletRequest,
        @PathVariable("roomID") roomID: Int,
        @PathVariable("seatID") seatID: Int,
        @PathVariable("docID") docID: Int,
        @PathVariable("commentID") commentID: Int
    ): String {
        val session = request.session
        val user = (session.getAttribute(SessionConstants.KEY_USER) as? User)
            ?: return ResponseBodyBuilder<Void>(Errors.Companion.UserError.notSignedIn).toString()

        val like = service.getLikedOfComment(commentID, user.userID)
            ?: return ResponseBodyBuilder<Void>(Errors.Companion.DatabaseError.notExists).toString()

        if (user.userID != like.userID)
            return ResponseBodyBuilder<Void>(Errors.Companion.UserError.notAuthorized).toString()

        service.deleteLike(like.likedID)

        return ResponseBodyBuilder<Void>().toString()
    }

}