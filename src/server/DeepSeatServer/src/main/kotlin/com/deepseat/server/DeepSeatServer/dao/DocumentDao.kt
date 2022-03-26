package com.deepseat.server.DeepSeatServer.dao

import com.deepseat.server.DeepSeatServer.config.DBConfig
import com.deepseat.server.DeepSeatServer.model.Document
import java.sql.DriverManager
import java.sql.SQLException

class DocumentDao {

    private val dbConfig = DBConfig.getInstance()

    @Throws(ClassNotFoundException::class, SQLException::class)
    fun add(document: Document): Boolean {
        Class.forName(dbConfig.driverClassName)

        val connection = DriverManager.getConnection(dbConfig.url, dbConfig.username, dbConfig.password)
        val ps = connection.prepareStatement("INSERT INTO document(userID, roomID, seatID, content) VALUES(?, ?, ?, ?)")

        ps.setString(1, document.userID)
        ps.setInt(2, document.roomID)
        ps.setInt(3, document.seatID)
        ps.setString(4, document.content)

        val result = ps.executeUpdate()

        ps.close()
        connection.close()

        return result <= 0
    }

    @Throws(ClassNotFoundException::class, SQLException::class)
    fun get(docId: Int): Document? {
        Class.forName(dbConfig.driverClassName)

        val connection = DriverManager.getConnection(dbConfig.url, dbConfig.username, dbConfig.password)
        val ps = connection.prepareStatement("SELECT * FROM document WHERE docID = ?")

        ps.setInt(1, docId)

        val rs = ps.executeQuery()

        val result = if (rs.next()) {
            Document(
                rs.getInt("docID"),
                rs.getString("userID"),
                rs.getInt("roomID"),
                rs.getInt("seatID"),
                rs.getString("content"),
                rs.getString("wrote"),
                rs.getInt("edited") == 0
            )
        } else {
            null
        }

        ps.close()
        connection.close()

        return result
    }

    @Throws(ClassNotFoundException::class, SQLException::class)
    fun delete(docID: String): Boolean {
        Class.forName(dbConfig.driverClassName)

        val connection = DriverManager.getConnection(dbConfig.url, dbConfig.username, dbConfig.password)
        val ps = connection.prepareStatement("DELETE FROM document WHERE docID = ?")

        ps.setString(1, docID)

        val result = ps.executeUpdate()

        ps.close()
        connection.close()

        return result <= 0
    }

    @Throws(ClassNotFoundException::class, SQLException::class)
    fun update(document: Document): Boolean {
        Class.forName(dbConfig.driverClassName)

        val connection = DriverManager.getConnection(dbConfig.url, dbConfig.username, dbConfig.password)
        val ps = connection.prepareStatement("UPDATE document SET content = ?, edited = ? WHERE docID = ?")

        ps.setString(1, document.content)
        ps.setInt(2, 1)
        ps.setInt(3, document.docID)

        val result = ps.executeUpdate()

        ps.close()
        connection.close()

        return result <= 0
    }

}