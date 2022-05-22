package com.deepseat.server.DeepSeatServer.service

import com.deepseat.server.DeepSeatServer.vo.User

interface UserService {

    fun insertUser(user: User)
    fun getUser(userID: String): User?
    fun getUserByNickname(nickname: String): User?
    fun deleteUser(userID: String)
    fun updateUser(user: User)
    fun verifyUser(userID: String)
}