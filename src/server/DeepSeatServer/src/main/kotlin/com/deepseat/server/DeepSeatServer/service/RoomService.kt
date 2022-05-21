package com.deepseat.server.DeepSeatServer.service

import com.deepseat.server.DeepSeatServer.vo.Room

interface RoomService {

    public fun insertRoom(room: Room)
    public fun isRoomIDExists(roomID: Int): Boolean
    public fun getRoomByID(roomID: Int): Room?
    public fun getRecentCreatedRoomByApiKey(apiKey: String): Room
    public fun getRooms(): List<Room>
    public fun updateRoom(room: Room)

}