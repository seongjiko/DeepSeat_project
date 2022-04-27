package com.deepseat.server.DeepSeatServer.controller

import com.deepseat.server.DeepSeatServer.dao.UserDao
import com.deepseat.server.DeepSeatServer.error.Errors
import com.deepseat.server.DeepSeatServer.session.SessionConstants
import com.deepseat.server.DeepSeatServer.tool.PasswordTool
import com.deepseat.server.DeepSeatServer.tool.SaltGenerator
import com.deepseat.server.DeepSeatServer.vo.User
import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
class UserController {

    @Autowired
    private lateinit var userDao: UserDao

    @PostMapping("/register")
    fun register(
        request: HttpServletRequest,
        @RequestParam userID: String,
        @RequestParam userPW: String,
        @RequestParam userPWCheck: String,
        @RequestParam nickname: String
    ): String {

        if (userPW != userPWCheck) {
            return Gson().toJson(Errors.Companion.RegistrationError.registerPWCheckNotMatch)
        }

        val salt = SaltGenerator.generate()
        val user = User(userID, PasswordTool.encryptPassword(userPW, salt), salt, nickname)

        val success = userDao.add(user)

        return if (success) {
            val session = request.session
            session.setAttribute(SessionConstants.KEY_USER, user)
            session.id
        } else {
            Gson().toJson(Errors.Companion.DatabaseError.dbInsertFailure)
        }
    }

    @PostMapping("/login")
    fun login(request: HttpServletRequest, @RequestParam userID: String, @RequestParam userPW: String): String {
        val user = userDao.get(userID) ?: return Gson().toJson(Errors.Companion.UserError.notRegistered)

        if (user.userPW != PasswordTool.encryptPassword(userPW, user.salt))
            return Gson().toJson(Errors.Companion.UserError.wrongPassword)

        val session = request.session
        session.setAttribute(SessionConstants.KEY_USER, user)

        return session.id
    }

    @PostMapping("/user")
    fun getUser(request: HttpServletRequest): String {
        return Gson().toJson(request.session.getAttribute(SessionConstants.KEY_USER))
    }

}