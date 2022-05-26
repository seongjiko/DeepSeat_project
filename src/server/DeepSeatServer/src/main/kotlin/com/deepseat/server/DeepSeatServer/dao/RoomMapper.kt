package com.deepseat.server.DeepSeatServer.dao

import com.deepseat.server.DeepSeatServer.vo.Room
import org.springframework.stereotype.Repository

@Repository
interface RoomMapper {

    public fun insertRoom(room: Room)
    public fun isRoomIDExists(roomID: Int): Int?
    public fun getRoomByID(roomID: Int): Room?
    public fun getRoomByApiKey(apiKey: String): List<Room>
    public fun getRooms(): List<Room>
    public fun updateRoom(room: Room)

}