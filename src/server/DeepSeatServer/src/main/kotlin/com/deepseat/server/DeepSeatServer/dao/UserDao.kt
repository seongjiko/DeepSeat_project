package com.deepseat.server.DeepSeatServer.dao

import com.deepseat.server.DeepSeatServer.config.DBConfig
import com.deepseat.server.DeepSeatServer.tool.SaltGenerator
import com.deepseat.server.DeepSeatServer.vo.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.sql.DriverManager
import java.sql.SQLException

@Repository
class UserDao {

    @Autowired
    private lateinit var dbConfig: DBConfig

    @Throws(ClassNotFoundException::class, SQLException::class)
    fun add(user: User): Boolean {
        Class.forName(dbConfig.driverClassName)

        val connection = DriverManager.getConnection(dbConfig.url, dbConfig.username, dbConfig.password)
        val ps = connection.prepareStatement("INSERT INTO user(userID, userPW, salt, nickname) VALUES(?, ?, ?, ?)")

        ps.setString(1, user.userID)
        ps.setString(2, user.userPW)
        ps.setString(3, SaltGenerator.generate())
        ps.setString(4, user.nickname)

        val result = ps.executeUpdate() > 0

        ps.close()
        connection.close()

        return result
    }

    @Throws(ClassNotFoundException::class, SQLException::class)
    fun get(userID: String): User? {
        Class.forName(dbConfig.driverClassName)

        val connection = DriverManager.getConnection(dbConfig.url, dbConfig.username, dbConfig.password)
        val ps = connection.prepareStatement("SELECT * FROM user WHERE userID = ?")

        ps.setString(1, userID)

        val rs = ps.executeQuery()

        val result = if (rs.next()) {
            User(
                rs.getString("userID"),
                rs.getString("userPW"),
                rs.getString("salt"),
                rs.getString("nickname")
            )
        } else {
            null
        }

        ps.close()
        connection.close()

        return result
    }

    @Throws(ClassNotFoundException::class, SQLException::class)
    fun delete(userID: String): Boolean {
        Class.forName(dbConfig.driverClassName)

        val connection = DriverManager.getConnection(dbConfig.url, dbConfig.username, dbConfig.password)
        val ps = connection.prepareStatement("DELETE FROM user WHERE userID = ?")

        ps.setString(1, userID)

        val result = ps.executeUpdate() > 0

        ps.close()
        connection.close()

        return result
    }

    @Throws(ClassNotFoundException::class, SQLException::class)
    fun update(user: User): Boolean {
        Class.forName(dbConfig.driverClassName)

        val connection = DriverManager.getConnection(dbConfig.url, dbConfig.username, dbConfig.password)
        val ps = connection.prepareStatement("UPDATE user SET userPW = ?, salt = ?, nickname = ? WHERE userID = ?")

        ps.setString(1, user.userPW)
        ps.setString(2, user.salt)
        ps.setString(3, user.nickname)
        ps.setString(4, user.userID)

        val result = ps.executeUpdate()

        ps.close()
        connection.close()

        return result > 0
    }

}