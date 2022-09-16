package com.deepseat.server.DeepSeatServer.controller

import com.deepseat.server.DeepSeatServer.error.Errors
import com.deepseat.server.DeepSeatServer.service.ApiKeyService
import com.deepseat.server.DeepSeatServer.service.ObservationService
import com.deepseat.server.DeepSeatServer.service.RoomService
import com.deepseat.server.DeepSeatServer.service.SeatService
import com.deepseat.server.DeepSeatServer.tool.ResponseBodyBuilder
import com.deepseat.server.DeepSeatServer.vo.Observation
import com.deepseat.server.DeepSeatServer.vo.Seat
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.text.SimpleDateFormat
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Controller
class SeatController {

    @Autowired
    private lateinit var apiService: ApiKeyService

    @Autowired
    private lateinit var roomService: RoomService

    @Autowired
    private lateinit var seatService: SeatService

    @Autowired
    private lateinit var obService: ObservationService

    @ResponseBody
    @PostMapping("/api/room/{roomID}/register")
    public fun updateRoom(
        locale: Locale,
        request: HttpServletRequest,
        @PathVariable("roomID") roomID: Int,
        @RequestParam apiKey: String,
        @RequestParam roomName: String?,
        @RequestParam totalCnt: Int,
        @RequestParam data: String,
        response: HttpServletResponse
    ): String {

        val authApiKey = apiService.authenticateApiKey(apiKey)
            ?: return ResponseBodyBuilder<Void>(Errors.Companion.UserError.notAuthorized).toString()

        val room = roomService.getRoomByID(roomID)
            ?: return ResponseBodyBuilder<Void>(Errors.Companion.DatabaseError.notExists).toString()

        if (authApiKey != room.apiKey) {
            return ResponseBodyBuilder<Void>(Errors.Companion.UserError.notAuthorized).toString()
        }

        roomName?.let {
            room.roomName = it
            roomService.updateRoom(room)
        }

        val seatsJson = JSONObject(data)
        val seatArray = seatsJson.optJSONArray("seats")

        for (json in seatArray) {
            val seatJson = json as JSONObject
            val seatID = seatJson.get("seatID") as Int
            val x = seatJson.get("x") as Int
            val y = seatJson.get("y") as Int
            val width = seatJson.get("width") as Int
            val height = seatJson.get("height") as Int
            val seat = Seat(seatID, roomID, x, y, width, height)
            if (seatService.getSeatByID(seatID) != null) {
                seatService.updateSeat(seat)
            } else {
                seatService.insertSeat(seat)
            }
        }

        return ResponseBodyBuilder<Void>().toString()
    }

    @ResponseBody
    @GetMapping("/api/room/{roomID}")
    public fun getSeats(locale: Locale, @PathVariable("roomID") roomID: Int): String {
        return ResponseBodyBuilder<List<Seat>>().data(seatService.getSeats(roomID)).toString()
    }

    @ResponseBody
    @PostMapping("/api/room/{roomID}/status")
    public fun updateStatus(
        locale: Locale,
        request: HttpServletRequest,
        @PathVariable("roomID") roomID: Int,
        @RequestParam apiKey: String,
        @RequestParam roomName: String?,
        @RequestParam totalCnt: Int,
        @RequestParam data: String,
        response: HttpServletResponse
    ): String {

        val authApiKey = apiService.authenticateApiKey(apiKey)
            ?: return ResponseBodyBuilder<Void>(Errors.Companion.UserError.notAuthorized).toString()

        val room = roomService.getRoomByID(roomID)
            ?: return ResponseBodyBuilder<Void>(Errors.Companion.DatabaseError.notExists).toString()

        if (authApiKey != room.apiKey) {
            return ResponseBodyBuilder<Void>(Errors.Companion.UserError.notAuthorized).toString()
        }

        roomName?.let {
            room.roomName = it
            roomService.updateRoom(room)
        }

        val seatsJson = JSONObject(data)
        val seatArray = seatsJson.optJSONArray("seatStats")

        for (json in seatArray) {
            val seatJson = json as JSONObject
            val seatID = seatJson.get("seatID") as Int
            val status = seatJson.get("status") as Int

            val calendar = Calendar.getInstance()
            val dateFormat = SimpleDateFormat("yyyy-MM-dd")
            val timeFormat = SimpleDateFormat("hh:mm:ss")
            val date = dateFormat.format(calendar.time)
            val time = timeFormat.format(calendar.time)

            val observation = Observation(-1, roomID, seatID, date, time, status)
            obService.insertObservation(observation)
        }

        return ResponseBodyBuilder<Void>().toString()
    }

    @ResponseBody
    @GetMapping("/api/room/{roomID}/status")
    public fun getStatus(
        locale: Locale,
        @PathVariable("roomID") roomID: Int
    ): String {
        val room = roomService.getRoomByID(roomID)
            ?: return ResponseBodyBuilder<Void>(Errors.Companion.DatabaseError.notExists).toString()

        val seats = seatService.getSeats(room.roomID)
        var observations = arrayListOf<Observation>()

        for (s in seats) {
            val observation = obService.getMostRecentObservation(room.roomID, s.seatID)
            observation?.let { observations.add(it) }
        }

        return ResponseBodyBuilder<List<Observation>>().data(observations).toString()
    }

}