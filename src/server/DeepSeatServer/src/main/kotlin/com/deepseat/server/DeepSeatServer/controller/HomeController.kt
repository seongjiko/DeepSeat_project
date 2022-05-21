package com.deepseat.server.DeepSeatServer.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import java.util.*

@Controller
class HomeController {

    @GetMapping("/")
    public fun home(locale: Locale): String {
        return "index"
    }


}