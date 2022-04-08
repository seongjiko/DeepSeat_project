package com.deepseat.server.DeepSeatServer.dao

import com.deepseat.server.DeepSeatServer.config.DBConfig
import com.deepseat.server.DeepSeatServer.model.Room
import com.deepseat.server.DeepSeatServer.model.Seat
import java.sql.DriverManager
import java.sql.SQLException

class RoomDao {
    private val dbConfig = DBConfig.getInstance()

    @Throws(ClassNotFoundException::class, SQLException::class)
    fun add(room: Room): Boolean{
        val connection = DriverManager.getConnection(dbConfig.url, dbConfig.username, dbConfig.password)
        val ps = connection.prepareStatement("INSERT INTO room (roomName) VALUES(?)")

        ps.setString(1,room.roomName)


        val result = ps.executeUpdate()

        ps.close()
        connection.close()

        return result > 0
    }

    @Throws(ClassNotFoundException::class, SQLException::class)
    fun get(roomID: Int): Room?{
        Class.forName(dbConfig.driverClassName)

        val connection = DriverManager.getConnection(dbConfig.url, dbConfig.username, dbConfig.password)
        val ps = connection.prepareStatement("SELECT * FROM room WHERE roomID = ?")

        ps.setInt(1,roomID)

        val rs = ps.executeQuery()

        val result = if (rs.next()){
            Room(
                    rs.getInt("roomID"),
                    rs.getString("roomName")
            )
        } else{
            null
        }

        ps.close()
        connection.close()

        return result
    }
}