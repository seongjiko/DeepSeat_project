package com.deepseat.server.DeepSeatServer.controller

import com.deepseat.server.DeepSeatServer.dao.LikedDao
import com.deepseat.server.DeepSeatServer.session.SessionConstants
import com.deepseat.server.DeepSeatServer.vo.Liked
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
class LikedController {

    @Autowired
    private lateinit var likedDao: LikedDao

    @PostMapping("/liked/{docID}")
    fun liked(request: HttpServletRequest, @PathVariable("docID") docID: Int) {

        val userID = (request.getAttribute(SessionConstants.KEY_USER) as? String) ?: return

        val liked = Liked(userID = userID, docID = docID)
        likedDao.add(liked)
    }

    @PostMapping("/liked/{roomID}/{seatID}/{docID}/{commentID}")
    fun liked(
        @PathVariable("roomID") roomID: Int,
        @PathVariable("seatID") seatID: Int,
        @PathVariable("docID") docID: Int,
        @PathVariable("commentID") commentID: Int
    ) {
        val liked = Liked(0, "", docID, commentID)
        likedDao.add(liked)
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
        @PathVariable("roomID") roomID: Int,
        @PathVariable("seatID") seatID: Int,
        @PathVariable("docID") docID: Int,
        @PathVariable("likedID") likedID: Int
    ) {
        likedDao.delete(likedID)
    }

    @DeleteMapping("/liked/{roomID}/{seatID}/{docID}/{commentID}/{likedID}")
    fun unliked(
        @PathVariable("roomID") roomID: Int,
        @PathVariable("seatID") seatID: Int,
        @PathVariable("docID") docID: Int,
        @PathVariable("commentID") commentID: Int,
        @PathVariable("likedID") likedID: Int
    ) {
        likedDao.delete(likedID)
    }

}