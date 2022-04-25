package com.deepseat.server.DeepSeatServer.dao

import com.deepseat.server.DeepSeatServer.config.DBConfig
import com.deepseat.server.DeepSeatServer.vo.Liked
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.Types

@Repository
class LikedDao {

    @Autowired
    private lateinit var dbConfig: DBConfig

    @Throws(ClassNotFoundException::class, SQLException::class)
    fun add(liked: Liked): Boolean {
        Class.forName(dbConfig.driverClassName)

        val connection = DriverManager.getConnection(dbConfig.url, dbConfig.username, dbConfig.password)
        val ps = connection.prepareStatement("INSERT INTO liked(likedID, userID, docID, commentID) VALUES(?, ?, ?, ?);")

        ps.setInt(1, liked.likedID)
        ps.setString(2, liked.userID)

        if (liked.docID != null && liked.commentID == null) {
            ps.setInt(3, liked.docID!!)
            ps.setNull(4, Types.INTEGER)
        } else if (liked.docID == null && liked.commentID != null) {
            ps.setNull(3, Types.INTEGER)
            ps.setInt(4, liked.commentID!!)
        } else {
            ps.setNull(3, Types.INTEGER)
            ps.setNull(4, Types.INTEGER)
        }

        val result = ps.executeUpdate()

        ps.close()
        connection.close()

        return result > 0
    }

    @Throws(ClassNotFoundException::class, SQLException::class)
    fun get(likedID: Int): Liked? {
        Class.forName(dbConfig.driverClassName)

        val connection = DriverManager.getConnection(dbConfig.url, dbConfig.username, dbConfig.password)
        val ps = connection.prepareStatement("SELECT * FROM liked WHERE likedID = ?;")

        ps.setInt(1, likedID)

        val rs = ps.executeQuery()

        val result = if (rs.next()) {
            Liked(
                    rs.getInt("likedID"),
                    rs.getString("userID"),
                    rs.getObject("docID") as Int?,
                    rs.getObject("commentID") as Int?
            )
        } else {
            null
        }

        ps.close()
        connection.close()

        return result
    }

    @Throws(ClassNotFoundException::class, SQLException::class)
    fun getList(docID: Int = -1, commentID: Int = -1): Array<Liked> {
        Class.forName(dbConfig.driverClassName)

        val connection = DriverManager.getConnection(dbConfig.url, dbConfig.username, dbConfig.password)
        val ps = connection.prepareStatement("SELECT * FROM liked WHERE docID = ? OR commentID = ?;")

        ps.setInt(1, docID)
        ps.setInt(2, commentID)

        val rs = ps.executeQuery()

        val result: ArrayList<Liked> = arrayListOf()

        while (rs.next()) {
            result.add(Liked(
                    rs.getInt("likedID"),
                    rs.getString("userID"),
                    rs.getObject("docID") as Int?,
                    rs.getObject("commentID") as Int?
            ))
        }

        ps.close()
        connection.close()

        return result.toTypedArray()
    }

    @Throws(ClassNotFoundException::class, SQLException::class)
    fun delete(likedID: Int): Boolean {
        Class.forName(dbConfig.driverClassName)

        val connection = DriverManager.getConnection(dbConfig.url, dbConfig.username, dbConfig.password)
        val ps = connection.prepareStatement("DELETE FROM liked WHERE likedID = ?;")

        ps.setInt(1, likedID)

        val result = ps.executeUpdate() <= 0

        ps.close()
        connection.close()

        return result
    }

}