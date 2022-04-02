package com.deepseat.server.DeepSeatServer.dao

import com.deepseat.server.DeepSeatServer.config.DBConfig
import com.deepseat.server.DeepSeatServer.model.Document
import java.sql.DriverManager
import java.sql.SQLException

class DocumentDao {

    private val dbConfig = DBConfig.getInstance()

    @Throws(ClassNotFoundException::class, SQLException::class)
    fun add(document: Document): Int? {
        Class.forName(dbConfig.driverClassName)

        val connection = DriverManager.getConnection(dbConfig.url, dbConfig.username, dbConfig.password)
        val ps = connection.prepareStatement("INSERT INTO document(userID, roomID, seatID, content) VALUES(?, ?, ?, ?);")

        ps.setString(1, document.userID)
        ps.setInt(2, document.roomID)
        ps.setInt(3, document.seatID)
        ps.setString(4, document.content)

        val result = ps.executeUpdate()

        ps.close()
        connection.close()

        val getPs = connection.prepareStatement("SELECT docID FROM document WHERE userID = ?, roomID = ?, seatID = ? DESC;")

        ps.setString(1, document.userID)
        ps.setInt(2, document.roomID)
        ps.setInt(3, document.seatID)

        val getResult = getPs.executeQuery()

        return if (getResult.next()) {
            getResult.getInt("docID")
        } else {
            null
        }
    }

    @Throws(ClassNotFoundException::class, SQLException::class)
    fun get(docId: Int): Document? {
        Class.forName(dbConfig.driverClassName)

        val connection = DriverManager.getConnection(dbConfig.url, dbConfig.username, dbConfig.password)
        val ps = connection.prepareStatement("SELECT * FROM document WHERE docID = ?;")

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
    fun getList(): Array<Document> {
        Class.forName(dbConfig.driverClassName)

        val connection = DriverManager.getConnection(dbConfig.url, dbConfig.username, dbConfig.password)
        val ps = connection.prepareStatement("SELECT * FROM document;")

        val rs = ps.executeQuery()

        val result: ArrayList<Document> = arrayListOf()

        while (rs.next()) {
            result.add(Document(
                    rs.getInt("docID"),
                    rs.getString("userID"),
                    rs.getInt("roomID"),
                    rs.getInt("seatID"),
                    rs.getString("content"),
                    rs.getString("wrote"),
                    rs.getInt("edited") == 0
            ))
        }

        ps.close()
        connection.close()

        return result.toTypedArray()
    }

    @Throws(ClassNotFoundException::class, SQLException::class)
    fun delete(docID: Int): Boolean {
        Class.forName(dbConfig.driverClassName)

        val connection = DriverManager.getConnection(dbConfig.url, dbConfig.username, dbConfig.password)
        val ps = connection.prepareStatement("DELETE FROM document WHERE docID = ?;")

        ps.setInt(1, docID)

        val result = ps.executeUpdate()

        ps.close()
        connection.close()

        return result > 0
    }

    @Throws(ClassNotFoundException::class, SQLException::class)
    fun update(document: Document): Boolean {
        Class.forName(dbConfig.driverClassName)

        val connection = DriverManager.getConnection(dbConfig.url, dbConfig.username, dbConfig.password)
        val ps = connection.prepareStatement("UPDATE document SET content = ?, edited = ? WHERE docID = ?;")

        ps.setString(1, document.content)
        ps.setInt(2, 1)
        ps.setInt(3, document.docID)

        val result = ps.executeUpdate()

        ps.close()
        connection.close()

        return result > 0
    }

}