package com.deepseat.server.DeepSeatServer.controller

import com.deepseat.server.DeepSeatServer.error.Errors
import com.deepseat.server.DeepSeatServer.service.UserService
import com.deepseat.server.DeepSeatServer.session.SessionConstants
import com.deepseat.server.DeepSeatServer.tool.PasswordTool
import com.deepseat.server.DeepSeatServer.tool.ResponseBodyBuilder
import com.deepseat.server.DeepSeatServer.tool.SaltGenerator
import com.deepseat.server.DeepSeatServer.vo.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*
import javax.servlet.http.HttpServletRequest

@RestController
class UserController {

    @Autowired
    private lateinit var service: UserService

    @PostMapping("/register")
    fun register(
        request: HttpServletRequest,
        @RequestParam userID: String,
        @RequestParam userPW: String,
        @RequestParam userPWCheck: String,
        @RequestParam nickname: String,
        @RequestParam email: String
    ): String {

        if (userPW != userPWCheck) {
            return ResponseBodyBuilder<Void>(Errors.Companion.RegistrationError.registerPWCheckNotMatch).toString()
        }

        if (service.getUser(userID) != null) {
            return ResponseBodyBuilder<Void>(Errors.Companion.UserError.userExists).toString()
        }

        val salt = SaltGenerator.generate()
        val user = User(userID, PasswordTool.encryptPassword(userPW, salt), salt, nickname, email)

        service.insertUser(user)

        return ResponseBodyBuilder<Void>().toString()
    }

    @PostMapping("/login")
    fun login(request: HttpServletRequest, @RequestParam userID: String, @RequestParam userPW: String): String {
        val user = service.getUser(userID) ?: return ResponseBodyBuilder<Void>(Errors.Companion.UserError.notRegistered).toString()

        if (user.userPW != PasswordTool.encryptPassword(userPW, user.salt))
            return ResponseBodyBuilder<Void>(Errors.Companion.UserError.notRegistered).toString()

        val session = request.session
        session.setAttribute(SessionConstants.KEY_USER, user)

        return ResponseBodyBuilder<String>().data(session.id).toString()
    }

    @PostMapping("/logout")
    fun logout(request: HttpServletRequest): String {
        request.session.invalidate()
        return ResponseBodyBuilder<Void>().toString()
    }

    @PostMapping("/edit-user")
    fun editUser(
        locale: Locale,
        request: HttpServletRequest,
        @RequestParam nickname: String
    ): String {
        val user = request.session.getAttribute("user") as? User
            ?: return ResponseBodyBuilder<Boolean>(Errors.Companion.UserError.notSignedIn).data(false).toString()

        if (service.getUserByNickname(nickname) != null) {
            return ResponseBodyBuilder<Boolean>().data(false).toString()
        }

        user.nickname = nickname
        service.updateUser(user)

        return ResponseBodyBuilder<Boolean>().data(true).toString()
    }

    @PostMapping("/user")
    fun getUser(request: HttpServletRequest): String {
        val user = request.session.getAttribute(SessionConstants.KEY_USER) as? User
        user?.let {
            it.userPW = ""
            it.salt = ""
            return ResponseBodyBuilder<User>().data(it).toString()
        }

        return ResponseBodyBuilder<Void>(Errors.Companion.UserError.notSignedIn).toString()
    }

    @PostMapping("/id-check")
    fun checkID(
        locale: Locale,
        @RequestParam userID: String
    ): String {
        val user = service.getUser(userID)

        return ResponseBodyBuilder<Boolean>().data(user == null).toString()
    }

    @PostMapping("/nickname-check")
    fun checkNickname(locale: Locale, @RequestParam nickname: String): String {
        return ResponseBodyBuilder<Boolean>().data(service.getUserByNickname(nickname) == null).toString()
    }

}