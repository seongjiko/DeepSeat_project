package com.deepseat.server.DeepSeatServer.controller

import com.deepseat.server.DeepSeatServer.error.Errors
import com.deepseat.server.DeepSeatServer.service.*
import com.deepseat.server.DeepSeatServer.session.SessionConstants
import com.deepseat.server.DeepSeatServer.tool.ResponseBodyBuilder
import com.deepseat.server.DeepSeatServer.vo.Document
import com.deepseat.server.DeepSeatServer.vo.DocumentVO
import com.deepseat.server.DeepSeatServer.vo.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
class DocumentController {

    @Autowired
    private lateinit var documentService: DocumentService

    @Autowired
    private lateinit var commentService: CommentService

    @Autowired
    private lateinit var likeService: LikedService

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var roomService: RoomService

    @Autowired
    private lateinit var seatService: SeatService

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

    @GetMapping("/doc/vo")
    fun getAllDoc(request: HttpServletRequest): String {
        val user = request.session.getAttribute(SessionConstants.KEY_USER) as? User
        val userId = user?.userID
        val documents = documentService.getDocuments()
        val docVOs = ArrayList<DocumentVO>()

        for (d in documents) {
            val docVO = DocumentVO(
                d.docID,
                d.userID,
                userService.getUser(d.userID)?.nickname ?: "탈퇴한 사용자",
                roomService.getRoomByID(d.roomID)?.roomName ?: "알 수 없음",
                seatService.getSeatByID(d.seatID)?.seatID.toString() ?: "알 수 없음",
                d.content,
                d.wrote,
                d.edited,
                commentService.getComments(d.docID).size,
                likeService.getLikedCountOfDocument(d.docID),
                if (userId != null) likeService.getLikedOfDocument(d.docID, userId) != null else false
            )

            docVOs.add(docVO)
        }

        return ResponseBodyBuilder<ArrayList<DocumentVO>>().data(docVOs).toString()
    }

    @GetMapping("/doc/{docID}/vo")
    fun getDocVO(request: HttpServletRequest, @RequestParam("docID") docID: Int): String {
        val user = request.session.getAttribute(SessionConstants.KEY_USER) as? User
        val userId = user?.userID
        val d = documentService.getDocumentById(docID)
            ?: return ResponseBodyBuilder<DocumentVO>(Errors.Companion.DatabaseError.notExists).toString()

        val docVO = DocumentVO(
            d.docID,
            d.userID,
            userService.getUser(d.userID)?.nickname ?: "탈퇴한 사용자",
            roomService.getRoomByID(d.roomID)?.roomName ?: "알 수 없음",
            seatService.getSeatByID(d.seatID)?.seatID.toString() ?: "알 수 없음",
            d.content,
            d.wrote,
            d.edited,
            commentService.getComments(d.docID).size,
            likeService.getLikedCountOfDocument(d.docID),
            if (userId != null) likeService.getLikedOfDocument(d.docID, userId) != null else false
        )

        return ResponseBodyBuilder<DocumentVO>().data(docVO).toString()
    }

    @GetMapping("/doc/{docID}")
    fun getDoc(
        @PathVariable("docID") docID: Int
    ): String {
        val document = documentService.getDocumentById(docID)
            ?: return ResponseBodyBuilder<Void>(Errors.Companion.DatabaseError.notExists).toString()

        return ResponseBodyBuilder<Document>().data(document).toString()
    }

    @GetMapping("/doc/{roomID}/{seatID}/vo")
    fun getDocVOList(
        request: HttpServletRequest,
        @PathVariable("roomID") roomID: Int,
        @PathVariable("seatID") seatID: Int
    ): String {
        val user = request.session.getAttribute(SessionConstants.KEY_USER) as? User
        val userId = user?.userID
        val documents = documentService.getDocumentsByRoomAndSeatId(roomID, seatID)
        val docVOs = ArrayList<DocumentVO>()

        for (d in documents) {
            val docVO = DocumentVO(
                d.docID,
                d.userID,
                userService.getUser(d.userID)?.nickname ?: "탈퇴한 사용자",
                roomService.getRoomByID(d.roomID)?.roomName ?: "알 수 없음",
                seatService.getSeatByID(d.seatID)?.seatID.toString() ?: "알 수 없음",
                d.content,
                d.wrote,
                d.edited,
                commentService.getComments(d.docID).size,
                likeService.getLikedCountOfDocument(d.docID),
                if (userId != null) likeService.getLikedOfDocument(d.docID, userId) != null else false
            )

            docVOs.add(docVO)
        }

        return ResponseBodyBuilder<ArrayList<DocumentVO>>().data(docVOs).toString()
    }

    @GetMapping("/doc/{roomID}/{seatID}")
    fun getDocList(@PathVariable("roomID") roomID: Int, @PathVariable("seatID") seatID: Int): String {
        val documentList = documentService.getDocumentsByRoomAndSeatId(roomID, seatID)

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
            ?: return ResponseBodyBuilder<Void>(Errors.Companion.UserError.notSignedIn).toString()

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
            ?: return ResponseBodyBuilder<Void>(Errors.Companion.UserError.notSignedIn).toString()

        val doc = documentService.getDocumentById(docID)
            ?: return ResponseBodyBuilder<Void>(Errors.Companion.DatabaseError.notExists).toString()

        if (user.userID != doc.userID)
            return ResponseBodyBuilder<Void>(Errors.Companion.UserError.notAuthorized).toString()

        documentService.deleteDocument(docID)

        return ResponseBodyBuilder<Void>().toString()
    }

}
