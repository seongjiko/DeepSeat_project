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

    override fun getRecentCreatedRoomByApiKey(apiKey: String): Room {
        return mapper.getRoomByApiKey(apiKey)[0]
    }

    override fun updateRoom(room: Room) {
        mapper.updateRoom(room)
    }
}