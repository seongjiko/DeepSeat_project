package com.deepseat.server.DeepSeatServer.controller

import com.deepseat.server.DeepSeatServer.dao.LikedDao
import com.deepseat.server.DeepSeatServer.model.Liked
import com.sun.xml.bind.v2.model.core.ID
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping

@Controller
class LikedController {

    @PostMapping("/{roomID}/{seatID}/{docID}")
    fun liked(@PathVariable("roomID") roomID: Int, @PathVariable("seatID") seatID: Int, @PathVariable("docID") docID: Int){
        val likedDao = LikedDao()
        val liked = Liked(0,"",docID,null)
        likedDao.add(liked)
    }

    @PostMapping("/{roomID}/{seatID}/{docID}/{commentID}")
    fun liked(@PathVariable("roomID") roomID: Int, @PathVariable("seatID") seatID: Int, @PathVariable("docID") docID: Int, @PathVariable("commentID") commentID: Int){
        val likedDao = LikedDao()
        val liked = Liked(0,"",docID,commentID)
        likedDao.add(liked)
    }

    @GetMapping("/{roomID}/{seatID}/{docID}")
    fun getLiked(@PathVariable("roomID") roomID: Int, @PathVariable("seatID") seatID: Int, @PathVariable("docID") docID: Int){
        val likedDao = LikedDao()
        likedDao.getList(docID, -1)
    }

    @GetMapping("/{roomID}/{seatID}/{docID}/{commentID}")
    fun getLiked(@PathVariable("roomID") roomID: Int, @PathVariable("seatID") seatID: Int, @PathVariable("docID") docID: Int, @PathVariable("commentID") commentID: Int){
        val likedDao = LikedDao()
        likedDao.getList(-1, commentID)
    }

    @DeleteMapping("/{roomID}/{seatID}/{docID}/{likedID}")
    fun unliked(@PathVariable("roomID") roomID: Int, @PathVariable("seatID") seatID: Int, @PathVariable("docID") docID: Int, @PathVariable("likedID") likedID: Int){
        val likedDao = LikedDao()
        likedDao.delete(likedID)
    }

    @DeleteMapping("/{roomID}/{seatID}/{docID}/{commentID}/{likedID}")
    fun unliked(@PathVariable("roomID") roomID: Int, @PathVariable("seatID") seatID: Int, @PathVariable("docID") docID: Int, @PathVariable("commentID") commentID: Int,  @PathVariable("likedID") likedID: Int){
        val likedDao = LikedDao()
        likedDao.delete(likedID)
    }

}