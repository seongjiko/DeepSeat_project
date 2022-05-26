package com.deepseat.server.DeepSeatServer.service

import com.deepseat.server.DeepSeatServer.dao.LikeMapper
import com.deepseat.server.DeepSeatServer.vo.Liked
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class LikedServiceImpl : LikedService {

    @Autowired
    private lateinit var mapper: LikeMapper

    override fun insertLike(liked: Liked) {
        mapper.insertLike(liked)
    }

    override fun getLikedById(likedID: Int): Liked? {
        return mapper.getLikedById(likedID)
    }

    override fun getLikedCountOfDocument(docID: Int): Int {
        return mapper.getLikedCountOfDocument(docID)
    }

    override fun getLikedCountOfComment(commentID: Int): Int {
        return mapper.getLikedCountOfComment(commentID)
    }

    override fun getLikedOfDocument(docID: Int, userID: String): Liked? {
        return mapper.getLikedOfDocument(docID, userID)
    }

    override fun getLikedOfComment(commentID: Int, userID: String): Liked? {
        return mapper.getLikedOfComment(commentID, userID)
    }

    override fun deleteLike(likedID: Int) {
        return mapper.deleteLike(likedID)
    }

    override fun deleteLikeFromDocument(docID: Int, userID: String) {
        mapper.deleteLikeFromDocument(docID, userID)
    }

    override fun deleteLikeFromComment(commentID: Int, userID: String) {
        mapper.deleteLikeFromComment(commentID, userID)
    }
}