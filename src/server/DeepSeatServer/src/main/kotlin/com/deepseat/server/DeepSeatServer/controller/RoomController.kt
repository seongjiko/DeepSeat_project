package com.deepseat.server.DeepSeatServer.controller

import com.deepseat.server.DeepSeatServer.error.Errors
import com.deepseat.server.DeepSeatServer.service.ApiKeyService
import com.deepseat.server.DeepSeatServer.service.RoomService
import com.deepseat.server.DeepSeatServer.tool.ResponseBodyBuilder
import com.deepseat.server.DeepSeatServer.vo.Room
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.servlet.http.HttpServletRequest

@Controller
class RoomController {

    @Autowired
    private lateinit var apiService: ApiKeyService

    @Autowired
    private lateinit var roomService: RoomService

    @GetMapping("/api/room/create")
    public fun roomGenerateForm(locale: Locale): String = "generate_room"

    @ResponseBody
    @PostMapping("/api/room/create")
    public fun createRoom(
        locale: Locale,
        request: HttpServletRequest,
        @RequestParam roomName: String,
        @RequestParam apiKey: String,
        @RequestParam longitude: Double,
        @RequestParam latitude: Double
    ): String {
        val authApiKey = apiService.authenticateApiKey(apiKey)
            ?: return ResponseBodyBuilder<Void>(Errors.Companion.UserError.notAuthorized).toString()

        val room = Room(-1, roomName, authApiKey, longitude, latitude)
        roomService.insertRoom(room)

        val roomCreated = roomService.getRecentCreatedRoomByApiKey(apiKey)

        return ResponseBodyBuilder<Int>().data(roomCreated.roomID).toString()
    }

    @ResponseBody
    @GetMapping("/api/room")
    public fun getRooms(locale: Locale): String {
        return ResponseBodyBuilder<List<Room>>().data(roomService.getRooms()).toString()
    }

    @ResponseBody
    @GetMapping("/api/room/geo")
    public fun getRoomsByGeoPoint(
        locale: Locale,
        @RequestParam longitude: Double,
        @RequestParam latitude: Double
    ): String {
        val rooms = roomService.getRoomsByGeoPoint(longitude, latitude)

        return ResponseBodyBuilder<List<Room>>().data(rooms).toString()
    }

    @ResponseBody
    @GetMapping("/api/room/{roomID}/status")
    public fun getRoomStatus(locale: Locale, @PathVariable("roomID") roomID: Int): String {
        val seatCount = roomService.getSeatCount(roomID)
        val availableCount = roomService.getAvailableSeatCount(roomID)
        val data = mapOf("total" to seatCount, "available" to availableCount)
        return ResponseBodyBuilder<Map<String, Int>>().data(data).toString()
    }

}