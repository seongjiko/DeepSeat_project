package com.deepseat.server.DeepSeatServer.service

import com.deepseat.server.DeepSeatServer.dao.RoomMapper
import com.deepseat.server.DeepSeatServer.vo.Room
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RoomServiceImpl : RoomService {

    @Autowired
    private lateinit var mapper: RoomMapper

    override fun insertRoom(room: Room) {
        mapper.insertRoom(room)
    }

    override fun isRoomIDExists(roomID: Int): Boolean {
        return (mapper.isRoomIDExists(roomID) ?: 0) > 0
    }

    override fun getRoomByID(roomID: Int): Room? {
        return mapper.getRoomByID(roomID)
    }

    override fun getRoomsByGeoPoint(
        longitude: Double,
        latitude: Double
    ): List<Room> {
        val maxLon = longitude + 0.05
        val minLon = longitude - 0.05
        val maxLat = longitude + 0.05
        val minLat = longitude - 0.05
        return mapper.getRoomsByGeoPoint(longitude, latitude, maxLon, minLon, maxLat, minLat)
    }

    override fun getSeatCount(roomID: Int): Int {
        return mapper.getSeatCount(roomID)
    }

    override fun getAvailableSeatCount(roomID: Int): Int {
        return getAvailableSeatCount(roomID)
    }

    override fun getRecentCreatedRoomByApiKey(apiKey: String): Room {
        return mapper.getRoomByApiKey(apiKey)[0]
    }

    override fun getRooms(): List<Room> {
        return mapper.getRooms()
    }

    override fun updateRoom(room: Room) {
        mapper.updateRoom(room)
    }
}