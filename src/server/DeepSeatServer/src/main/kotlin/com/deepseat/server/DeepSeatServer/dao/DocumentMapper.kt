package com.deepseat.server.DeepSeatServer.dao

import com.deepseat.server.DeepSeatServer.vo.Document
import org.apache.ibatis.annotations.Param
import org.springframework.stereotype.Repository

@Repository
interface DocumentMapper {

    fun insertDocument(document: Document)
    fun getDocuments(): List<Document>
    fun getDocumentsBySeatId(@Param("roomID") roomID: Int, @Param("seatID") seatID: Int): List<Document>
    fun getDocumentById(docID: Int): Document?
    fun deleteDocument(docID: Int)
    fun updateDocument(document: Document)

}
