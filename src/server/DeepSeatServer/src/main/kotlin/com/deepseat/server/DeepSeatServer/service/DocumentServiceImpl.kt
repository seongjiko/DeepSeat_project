package com.deepseat.server.DeepSeatServer.service

import com.deepseat.server.DeepSeatServer.dao.DocumentMapper
import com.deepseat.server.DeepSeatServer.vo.Document
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class DocumentServiceImpl : DocumentService{

    @Autowired
    private lateinit var mapper: DocumentMapper

    override fun inertDocument(document: Document) {
        mapper.insertDocument(document)
    }

    override fun getDocuments(): List<Document> {
        return mapper.getDocuments()
    }

    override fun getDocumentsByRoomAndSeatId(roomID: Int, seatID: Int): List<Document> {
        return mapper.getDocumentsBySeatId(roomID, seatID)
    }

    override fun getDocumentById(docID: Int): Document? {
        return mapper.getDocumentById(docID)
    }

    override fun deleteDocument(docID: Int) {
        mapper.deleteDocument(docID)
    }

    override fun updateDocument(document: Document) {
        mapper.updateDocument(document)
    }
}