package com.deepseat.server.DeepSeatServer.dao

import com.deepseat.server.DeepSeatServer.vo.Liked
import org.apache.ibatis.annotations.Param
import org.springframework.stereotype.Repository

@Repository
interface LikeMapper {

    fun insertLike(liked: Liked)
    fun getLikedById(likedID: Int): Liked?
    fun getLikedCountOfDocument(docID: Int): Int
    fun getLikedCountOfComment(commentID: Int): Int
    fun getLikedOfDocument(@Param("docID") docID: Int, @Param("userID") userID: String): Liked?
    fun getLikedOfComment(@Param("commentID") docID: Int, @Param("userID") userID: String): Liked?
    fun deleteLike(likedID: Int)
    fun deleteLikeFromDocument(@Param("docID") docID: Int, @Param("userID") userID: String)
    fun deleteLikeFromComment(@Param("commentID") commentID: Int, @Param("userID") userID: String)
}