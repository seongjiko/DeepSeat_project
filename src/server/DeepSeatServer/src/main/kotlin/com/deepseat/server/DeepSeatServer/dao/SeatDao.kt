package com.deepseat.server.DeepSeatServer.dao

import com.deepseat.server.DeepSeatServer.config.DBConfig
import com.deepseat.server.DeepSeatServer.vo.Seat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.sql.DriverManager
import java.sql.SQLException

@Repository
class SeatDao {

    @Autowired
    private lateinit var dbConfig: DBConfig

    @Throws(ClassNotFoundException::class, SQLException::class)
    fun add(seat: Seat): Boolean{
        val connection = DriverManager.getConnection(dbConfig.url, dbConfig.username, dbConfig.password)
        val ps = connection.prepareStatement("INSERT INTO seat(roomID, x, y, width, height) VALUES(?, ?, ?, ?, ?)")

        ps.setInt(1,seat.roomID)
        ps.setInt(2,seat.x)
        ps.setInt(3,seat.y)
        ps.setInt(4,seat.width)
        ps.setInt(5,seat.height)

        val result = ps.executeUpdate()

        ps.close()
        connection.close()

        return result > 0
    }

    @Throws(ClassNotFoundException::class, SQLException::class)
    fun get(seatID: Int, roomID: Int): Seat?{
        Class.forName(dbConfig.driverClassName)

        val connection = DriverManager.getConnection(dbConfig.url, dbConfig.username, dbConfig.password)
        val ps = connection.prepareStatement("SELECT * FROM seat WHERE seatID = ? AND roomID = ?")

        ps.setInt(1, seatID)
        ps.setInt(2,roomID)

        val rs = ps.executeQuery()

        val result = if (rs.next()){
            Seat(
                    rs.getInt("seatID"),
                    rs.getInt("roomID"),
                    rs.getInt("x"),
                    rs.getInt("y"),
                    rs.getInt("width"),
                    rs.getInt("height")
            )
        } else{
            null
        }

        ps.close()
        connection.close()

        return result
    }



}