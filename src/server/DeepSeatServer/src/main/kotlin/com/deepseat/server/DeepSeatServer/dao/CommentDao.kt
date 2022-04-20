package com.deepseat.server.DeepSeatServer.dao

import com.deepseat.server.DeepSeatServer.config.DBConfig
import com.deepseat.server.DeepSeatServer.vo.Comment
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.sql.DriverManager
import java.sql.SQLException

@Repository
class CommentDao {

    @Autowired
    private val dbConfig = DBConfig.getInstance()

    @Throws(ClassNotFoundException::class, SQLException::class)
    fun add(comment: Comment): Boolean {
        Class.forName(dbConfig.driverClassName)

        val connection = DriverManager.getConnection(dbConfig.url, dbConfig.username, dbConfig.password)
        val ps = connection.prepareStatement("INSERT INTO comment(userID, docID, content) VALUES(?, ?, ?)")

        ps.setString(1, comment.userID)
        ps.setInt(2, comment.docID)
        ps.setString(3, comment.content)

        val result = ps.executeUpdate()

        ps.close()
        connection.close()

        return result > 0
    }

    @Throws(ClassNotFoundException::class, SQLException::class)
    fun get(commentID: Int): Comment? {
        Class.forName(dbConfig.driverClassName)

        val connection = DriverManager.getConnection(dbConfig.url, dbConfig.username, dbConfig.password)
        val ps = connection.prepareStatement("SELECT * FROM comment WHERE commentID = ?")

        ps.setInt(1, commentID)

        val rs = ps.executeQuery()

        val result = if (rs.next()) {
            Comment(
                    rs.getInt("commentID"),
                    rs.getString("userID"),
                    rs.getInt("docID"),
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
    fun getList(docID: Int): Array<Comment> {
        Class.forName(dbConfig.driverClassName)

        val connection = DriverManager.getConnection(dbConfig.url, dbConfig.username, dbConfig.password)
        val ps = connection.prepareStatement("SELECT * FROM comment WHERE docID = ?")

        ps.setInt(1, docID)

        val rs = ps.executeQuery()

        val result: ArrayList<Comment> = arrayListOf()

        while (rs.next()) {
            result.add(Comment(
                    rs.getInt("commentID"),
                    rs.getString("userID"),
                    rs.getInt("docID"),
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
    fun delete(commentID: Int): Boolean {
        Class.forName(dbConfig.driverClassName)

        val connection = DriverManager.getConnection(dbConfig.url, dbConfig.username, dbConfig.password)
        val ps = connection.prepareStatement("DELETE FROM comment WHERE commentID = ?")

        ps.setInt(1, commentID)

        val result = ps.executeUpdate()

        ps.close()
        connection.close()

        return result > 0
    }

    @Throws(ClassNotFoundException::class, SQLException::class)
    fun update(comment: Comment): Boolean {
        Class.forName(dbConfig.driverClassName)

        val connection = DriverManager.getConnection(dbConfig.url, dbConfig.username, dbConfig.password)
        val ps = connection.prepareStatement("UPDATE comment SET content = ?, edited = ? WHERE commentID = ?")

        ps.setString(1, comment.content)
        ps.setInt(2, 1)
        ps.setInt(3, comment.commentID)

        val result = ps.executeUpdate()

        ps.close()
        connection.close()

        return result > 0
    }

}