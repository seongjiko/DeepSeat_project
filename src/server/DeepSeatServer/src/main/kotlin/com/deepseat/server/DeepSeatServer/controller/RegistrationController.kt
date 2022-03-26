package com.deepseat.server.DeepSeatServer.controller

import com.deepseat.server.DeepSeatServer.dao.UserDao
import com.deepseat.server.DeepSeatServer.model.User
import com.deepseat.server.DeepSeatServer.tool.PasswordTool
import com.deepseat.server.DeepSeatServer.tool.SaltGenerator
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class RegistrationController {

    @PostMapping("/register")
    fun register(@RequestParam userID: String, @RequestParam userPW: String, @RequestParam nickname: String) {
        val userDao = UserDao()

        val salt = SaltGenerator.generate()
        val user = User(userID, PasswordTool.encryptPassword(userPW, salt), salt, nickname)

        userDao.add(user)
    }

    @PostMapping("/register/salt")
    fun getSalt(@RequestParam userID: String): String? {
        val userDao = UserDao()
        return userDao.get(userID)?.salt
    }

}