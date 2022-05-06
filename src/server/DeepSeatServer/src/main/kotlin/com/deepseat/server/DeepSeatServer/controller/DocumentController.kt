package com.deepseat.server.DeepSeatServer.controller

import com.deepseat.server.DeepSeatServer.error.Errors
import com.deepseat.server.DeepSeatServer.service.DocumentService
import com.deepseat.server.DeepSeatServer.session.SessionConstants
import com.deepseat.server.DeepSeatServer.tool.ResponseBodyBuilder
import com.deepseat.server.DeepSeatServer.vo.Document
import com.deepseat.server.DeepSeatServer.vo.User
import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
class DocumentController {

    @Autowired
    private lateinit var documentService: DocumentService

    @PostMapping("/doc/{roomID}/{seatID}")
    fun writeDoc(
        request: HttpServletRequest,
        @PathVariable("roomID") roomID: Int,
        @PathVariable("seatID") seatID: Int,
        @RequestParam content: String
    ): String {

        val user = (request.session.getAttribute(SessionConstants.KEY_USER) as? User)
            ?: return ResponseBodyBuilder<Void>(Errors.Companion.UserError.notSignedIn).toString()

        val document = Document(0, user.userID, roomID, seatID, content, "", false)
        documentService.inertDocument(document)

        return ResponseBodyBuilder<Void>().toString()
    }

    @GetMapping("/doc/{roomID}/{seatID}/{docID}")
    fun getDoc(
        @PathVariable("roomID") roomID: Int,
        @PathVariable("seatID") seatID: Int,
        @PathVariable("docID") docID: Int
    ): String {
        val document = documentService.getDocumentById(docID)
            ?: return ResponseBodyBuilder<Void>(Errors.Companion.DatabaseError.notExists).toString()

        return ResponseBodyBuilder<Document>().data(document).toString()
    }

    @GetMapping("/doc/{roomID}/{seatID}")
    fun getDocList(@PathVariable("roomID") roomID: Int, @PathVariable("seatID") seatID: Int): String {
        val documentList = documentService.getDocumentsBySeatId(seatID)

        return ResponseBodyBuilder<List<Document>>().data(documentList).toString()
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

        val doc = documentService.getDocumentById(docID)
            ?: return ResponseBodyBuilder<Void>(Errors.Companion.DatabaseError.notExists).toString()

        if (user.userID != doc.userID)
            return ResponseBodyBuilder<Void>(Errors.Companion.UserError.notAuthorized).toString()

        doc.content = content
        documentService.updateDocument(doc)

        return ResponseBodyBuilder<Void>().toString()
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

        val doc = documentService.getDocumentById(docID)
            ?: return ResponseBodyBuilder<Void>(Errors.Companion.DatabaseError.notExists).toString()

        if (user.userID != doc.userID)
            return ResponseBodyBuilder<Void>(Errors.Companion.UserError.notAuthorized).toString()

        documentService.deleteDocument(docID)

        return ResponseBodyBuilder<Void>().toString()
    }

}
