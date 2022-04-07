package com.deepseat.server.DeepSeatServer.controller

import com.deepseat.server.DeepSeatServer.dao.UserDao
import com.deepseat.server.DeepSeatServer.tool.PasswordTool
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import javax.servlet.http.HttpServletRequest

@Controller
class LoginController {

    @PostMapping("/login")
    fun login(request: HttpServletRequest, @RequestParam userID: String, @RequestParam userPW: String): String? {
        val userDao = UserDao()
        val user = userDao.get(userID)

        if (user != null && user.userPW == PasswordTool.encryptPassword(userPW, user.salt)) {
            request.session.setAttribute("user", user)
            return request.session.id

        } else {
            request.session.invalidate()
            return null
        }
    }

}