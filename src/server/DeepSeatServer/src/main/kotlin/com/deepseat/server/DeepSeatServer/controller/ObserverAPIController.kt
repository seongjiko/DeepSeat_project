package com.deepseat.server.DeepSeatServer.controller

import com.deepseat.server.DeepSeatServer.error.Errors
import com.deepseat.server.DeepSeatServer.service.ApiKeyService
import com.deepseat.server.DeepSeatServer.service.ObservationService
import com.deepseat.server.DeepSeatServer.service.RoomService
import com.deepseat.server.DeepSeatServer.service.SeatService
import com.deepseat.server.DeepSeatServer.tool.ResponseBodyBuilder
import com.deepseat.server.DeepSeatServer.tool.SaltGenerator
import com.deepseat.server.DeepSeatServer.vo.Observation
import com.deepseat.server.DeepSeatServer.vo.Room
import com.deepseat.server.DeepSeatServer.vo.Seat
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.text.SimpleDateFormat
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Controller
class ObserverAPIController {

    @Autowired
    private lateinit var apiService: ApiKeyService

    @Autowired
    private lateinit var roomService: RoomService

    @Autowired
    private lateinit var seatService: SeatService

    @Autowired
    private lateinit var obService: ObservationService

    @Value("\${admin-id}")
    private lateinit var adminId: String

    @Value("\${admin-pw}")
    private lateinit var adminPw: String

    @GetMapping("/api/api-key")
    public fun generateApiForm(locale: Locale): String = "generate_api"

    @ResponseBody
    @PostMapping("/api/generate-api-key")
    public fun generateAPIKey(
        locale: Locale,
        request: HttpServletRequest,
        @RequestParam adminId: String,
        @RequestParam adminPw: String,
        response: HttpServletResponse
    ): String {
        return if (adminId == this.adminId && adminPw == this.adminPw) {
            val apiKey = SaltGenerator.generate(64)
            apiService.insertApiKey(apiKey)

            ResponseBodyBuilder<String>().data(apiKey).toString()
        } else {
            ResponseBodyBuilder<String>(Errors.Companion.UserError.notAuthorized).toString()
        }
    }

    @ResponseBody
    @PostMapping("/api/{roomID}/api-key-update")
    public fun updateApiKeyForRoom(
        locale: Locale,
        request: HttpServletRequest,
        @PathVariable("roomID") roomID: Int,
        @RequestParam adminId: String,
        @RequestParam adminPw: String,
        @RequestParam apiKey: String,
        response: HttpServletResponse
    ): String {
        if (adminId != this.adminId || adminPw != this.adminPw) {
            return ResponseBodyBuilder<String>(Errors.Companion.UserError.notAuthorized).toString()
        }

        val room = roomService.getRoomByID(roomID)
            ?: return ResponseBodyBuilder<Void>(Errors.Companion.DatabaseError.notExists).toString()

        room.apiKey = apiKey

        roomService.updateRoom(room)

        return ResponseBodyBuilder<Void>().toString()
    }

//    @ResponseBody
//    @PostMapping("/api/test")
//    public fun apiTest(
//        locale: Locale,
//        request: HttpServletRequest,
//        @RequestParam text: String,
//        response: HttpServletResponse
//    ): String {
//        println(request.toString())
//        print("Data: ")
//        println(text)
//        return ResponseBodyBuilder<String>().data(text).toString()
//    }
//
//    @ResponseBody
//    @PostMapping("/api/test/{roomID}/register")
//    public fun formatTest(
//        locale: Locale,
//        request: HttpServletRequest,
//        @PathVariable("roomID") roomID: Int,
//        @RequestParam apiKey: String,
//        @RequestParam roomName: String?,
//        @RequestParam totalCnt: Int,
//        @RequestParam data: String,
//        response: HttpServletResponse
//    ): String {
//        val authApiKey = apiService.authenticateApiKey(apiKey)
//            ?: return ResponseBodyBuilder<Void>(Errors.Companion.UserError.notAuthorized).toString()
//
//        val room = roomService.getRoomByID(roomID)
//            ?: return ResponseBodyBuilder<Void>(Errors.Companion.DatabaseError.notExists).toString()
//
//        if (authApiKey != room.apiKey) {
//            return ResponseBodyBuilder<Void>(Errors.Companion.UserError.notAuthorized).toString()
//        }
//
//        roomName?.let {
//            room.roomName = it
//            println("roomName changed to ${it}")
//        }
//
//        println(data)
//
//        val seatsJson = JSONObject(data)
//        val seatArray = seatsJson.optJSONArray("seats")
//
//        var seatResult = arrayListOf<String>()
//        println(seatArray)
//
//        for (json in seatArray) {
//            println(json)
//            val seatJson = json as JSONObject
//            println(seatJson)
//            val seatID = seatJson.get("seatID") as Int
//            val x = seatJson.get("x") as Int
//            val y = seatJson.get("y") as Int
//            val width = seatJson.get("width") as Int
//            val height = seatJson.get("height") as Int
//            val seat = Seat(seatID, roomID, x, y, width, height)
//            println("roomName changed to ${Gson().toJson(seat)}")
//            seatResult.add(Gson().toJson(seat))
//        }
//
//        return ResponseBodyBuilder<ArrayList<String>>().data(seatResult).toString()
//    }

}