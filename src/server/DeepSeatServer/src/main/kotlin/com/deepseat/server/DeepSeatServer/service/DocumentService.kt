package com.deepseat.server.DeepSeatServer.service

import com.deepseat.server.DeepSeatServer.vo.Document

interface DocumentService {
    fun inertDocument(document: Document)
    fun getDocuments(): List<Document>
    fun getDocumentsBySeatId(seatID: Int): List<Document>
    fun getDocumentById(docID: Int): Document?
    fun deleteDocument(docID: Int)
    fun updateDocument(document: Document)
}