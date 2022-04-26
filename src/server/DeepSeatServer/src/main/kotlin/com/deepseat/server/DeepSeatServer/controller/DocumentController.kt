package com.deepseat.server.DeepSeatServer.controller

import com.deepseat.server.DeepSeatServer.dao.DocumentDao
import com.deepseat.server.DeepSeatServer.error.Errors
import com.deepseat.server.DeepSeatServer.session.SessionConstants
import com.deepseat.server.DeepSeatServer.vo.Document
import com.deepseat.server.DeepSeatServer.vo.User
import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
class DocumentController {

    @Autowired
    private lateinit var documentDao: DocumentDao

    @PostMapping("/doc/{roomID}/{seatID}")
    fun writeDoc(
        request: HttpServletRequest,
        @PathVariable("roomID") roomID: Int,
        @PathVariable("seatID") seatID: Int,
        @RequestParam content: String
    ): String {

        val user = (request.session.getAttribute(SessionConstants.KEY_USER) as? User)
            ?: return Gson().toJson(Errors.Companion.UserError.notSignedIn)

        val document = Document(0, user.userID, roomID, seatID, content, "", false)

        return if (documentDao.add(document)) Gson().toJson(Errors.success)
        else Gson().toJson(Errors.Companion.DatabaseError.dbInsertFailure)
    }

    @GetMapping("/doc/{roomID}/{seatID}/{docID}")
    fun getDoc(
        @PathVariable("roomID") roomID: Int,
        @PathVariable("seatID") seatID: Int,
        @PathVariable("docID") docID: Int
    ): String {
        val document = documentDao.get(docID)
        return Gson().toJson(document)
    }

    @GetMapping("/doc/{roomID}/{seatID}")
    fun getDocList(@PathVariable("roomID") roomID: Int, @PathVariable("seatID") seatID: Int): String {
        val documentList = documentDao.getList()
        return Gson().toJson(documentList)
    }

    @PutMapping("/doc/{roomID}/{seatID}/{docID}")
    fun editDoc(
        request: HttpServletRequest,
        @PathVariable("roomID") roomID: Int,
        @PathVariable("seatID") seatID: Int,
        @PathVariable("docID") docID: Int,
        @RequestParam content: String
    ): String {

        val user = (request.session.getAttribute(SessionConstants.KEY_USER) as? User)
            ?: return Gson().toJson(Errors.Companion.UserError.notSignedIn)

        val doc = documentDao.get(docID) ?: return Gson().toJson(Errors.Companion.DatabaseError.notExists)

        if (user.userID != doc.userID) return Gson().toJson(Errors.Companion.UserError.notAuthorized)

        doc.content = content

        return if (documentDao.update(doc)) Gson().toJson(Errors.success)
        else Gson().toJson(Errors.Companion.DatabaseError.dbUpdateFailure)
    }

    @DeleteMapping("/doc/{roomID}/{seatID}/{docID}")
    fun deleteDoc(
        request: HttpServletRequest,
        @PathVariable("roomID") roomID: Int,
        @PathVariable("seatID") seatID: Int,
        @PathVariable("docID") docID: Int
    ): String {

        val user = (request.session.getAttribute(SessionConstants.KEY_USER) as? User)
            ?: return Gson().toJson(Errors.Companion.UserError.notSignedIn)

        val doc = documentDao.get(docID) ?: return Gson().toJson(Errors.Companion.DatabaseError.notExists)

        if (user.userID != doc.userID) return Gson().toJson(Errors.Companion.DatabaseError.notExists)

        return if (documentDao.delete(docID)) Gson().toJson(Errors.success)
        else Gson().toJson(Errors.Companion.DatabaseError.dbDeleteFailure)
    }

}
