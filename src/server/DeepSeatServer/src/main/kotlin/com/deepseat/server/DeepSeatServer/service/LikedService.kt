package com.deepseat.server.DeepSeatServer.service

import com.deepseat.server.DeepSeatServer.vo.Liked

interface LikedService {

    fun insertLike(liked: Liked)
    fun getLikedById(likedID: Int): Liked?
    fun getLikedCountOfDocument(docID: Int): Int
    fun getLikedCountOfComment(commentID: Int): Int
    fun getLikedOfDocument(docID: Int, userID: String): Liked?
    fun getLikedOfComment(commentID: Int, userID: String): Liked?
    fun deleteLike(likedID: Int)
    fun deleteLikeFromDocument(docID: Int, userID: String)
    fun deleteLikeFromComment(commentID: Int, userID: String)
}