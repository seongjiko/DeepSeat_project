package com.deepseat.server.DeepSeatServer.controller

import com.deepseat.server.DeepSeatServer.error.Errors
import com.deepseat.server.DeepSeatServer.service.ApiKeyService
import com.deepseat.server.DeepSeatServer.tool.ResponseBodyBuilder
import com.deepseat.server.DeepSeatServer.tool.SaltGenerator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.Locale
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestController
class ObserverAPIController {

    @Autowired
    private lateinit var service: ApiKeyService

    @Value("\${admin-id}")
    private lateinit var adminId: String

    @Value("\${admin-pw}")
    private lateinit var adminPw: String

    @PostMapping("/api/generate-api-key")
    public fun generateAPIKey(
        locale: Locale,
        request: HttpServletRequest,
        @RequestParam adminId: String,
        @RequestParam adminPw: String,
        response: HttpServletResponse
    ): String {
        return if (adminId == this.adminId && adminPw == this.adminPw)  {
            val apiKey = SaltGenerator.generate(64)
            service.insertApiKey(apiKey)

            ResponseBodyBuilder<String>().data(apiKey).toString()
        } else {
            ResponseBodyBuilder<String>(Errors.Companion.UserError.notAuthorized).toString()
        }
    }

    @PostMapping("/api/{roomID}/update")
    public fun updateStatus(
        locale: Locale,
        request: HttpServletRequest,
        @PathVariable("roomID") roomID: String,
        @RequestParam apiKey: String,
        response: HttpServletResponse
    ): String {
        val authApiKey = service.authenticateApiKey(apiKey)

        if (authApiKey == null) {
            return ResponseBodyBuilder<Void>(Errors.Companion.UserError.notAuthorized).toString()
        }

        return ""
    }

    @PostMapping("/api/test")
    public fun apiTest(
        locale: Locale,
        request: HttpServletRequest,
        @RequestParam text: String,
        response: HttpServletResponse
    ): String {
        println(request.toString())
        print("Data: ")
        println(text)
        return ResponseBodyBuilder<String>().data(text).toString()
    }

}