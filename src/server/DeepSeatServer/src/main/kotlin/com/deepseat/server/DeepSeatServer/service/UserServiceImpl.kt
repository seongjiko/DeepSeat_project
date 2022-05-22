package com.deepseat.server.DeepSeatServer.service

import com.deepseat.server.DeepSeatServer.dao.UserMapper
import com.deepseat.server.DeepSeatServer.vo.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserServiceImpl: UserService {

    @Autowired
    private lateinit var mapper: UserMapper

    override fun insertUser(user: User) {
        mapper.insertUser(user)
    }

    override fun getUser(userID: String): User? {
        return mapper.getUser(userID)
    }

    override fun getUserByNickname(nickname: String): User? {
        return mapper.getUserByNickname(nickname)
    }

    override fun deleteUser(userID: String) {
        mapper.deleteUser(userID)
    }

    override fun updateUser(user: User) {
        mapper.updateUser(user)
    }

    override fun verifyUser(userID: String) {
        mapper.verifyUser(userID)
    }

}