package com.deepseat.server.DeepSeatServer.dao

import com.deepseat.server.DeepSeatServer.vo.Room
import org.springframework.stereotype.Repository

@Repository
interface RoomMapper {

    public fun insertRoom(room: Room)
    public fun isRoomIDExists(roomID: Int): Int?
    public fun getRoomByID(roomID: Int): Room?
    public fun getRoomByApiKey(apiKey: String): List<Room>
    public fun getRoomsByGeoPoint(
        longitude: Double,
        latitude: Double,
        maxLon: Double,
        minLon: Double,
        maxLat: Double,
        minLat: Double
    ): List<Room>

    public fun getRooms(): List<Room>
    public fun getSeatCount(roomID: Int): Int
    public fun getAvailableSeatCount(roomID: Int): Int
    public fun updateRoom(room: Room)

}