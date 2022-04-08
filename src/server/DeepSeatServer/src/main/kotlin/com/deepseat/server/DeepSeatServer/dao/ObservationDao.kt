package com.deepseat.server.DeepSeatServer.dao

import com.deepseat.server.DeepSeatServer.config.DBConfig
import com.deepseat.server.DeepSeatServer.model.Observation
import com.deepseat.server.DeepSeatServer.model.Room
import com.deepseat.server.DeepSeatServer.model.Seat
import java.sql.DriverManager
import java.sql.SQLException

class ObservationDao {
    private val dbConfig = DBConfig.getInstance()

    @Throws(ClassNotFoundException::class, SQLException::class)
    fun add(observation: Observation): Boolean{
        val connection = DriverManager.getConnection(dbConfig.url, dbConfig.username, dbConfig.password)
        val ps = connection.prepareStatement("INSERT INTO observation (roomID, seatID, date, state) VALUES(?, ?, ?, ?)")

        ps.setInt(1,observation.roomID)
        ps.setInt(2,observation.seatID)
        ps.setString(3,observation.date)
        ps.setInt(4,observation.state)


        val result = ps.executeUpdate()

        ps.close()
        connection.close()

        return result > 0
    }

    @Throws(ClassNotFoundException::class, SQLException::class)
    fun get(seatID: Int, roomID: Int, date: String): Observation?{
        Class.forName(dbConfig.driverClassName)

        val connection = DriverManager.getConnection(dbConfig.url, dbConfig.username, dbConfig.password)
        val ps = connection.prepareStatement("SELECT * FROM seat WHERE seatID = ? AND roomID = ? AND date = ?")

        ps.setInt(1, seatID)
        ps.setInt(2,roomID)
        ps.setString(3,date)

        val rs = ps.executeQuery()

        val result = if (rs.next()){
            Observation(
                    rs.getInt("observationID"),
                    rs.getInt("seatID"),
                    rs.getInt("roomID"),
                    rs.getString("date"),
                    rs.getInt("state")
            )
        } else{
            null
        }

        ps.close()
        connection.close()

        return result
    }
}