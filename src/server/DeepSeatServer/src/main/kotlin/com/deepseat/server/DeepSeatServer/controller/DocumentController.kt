package com.deepseat.server.DeepSeatServer.controller

import com.deepseat.server.DeepSeatServer.dao.DocumentDao
import com.deepseat.server.DeepSeatServer.model.Document
import com.google.gson.Gson
import lombok.Getter
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class DocumentController {

    @ResponseBody
    @PostMapping("/{roomID}/{seatID}")
    fun writeDoc(@PathVariable("roomID") roomID: Int, @PathVariable("seatID") seatID: Int, @RequestParam userID: String, @RequestParam content: String): String? {
        val documentDao = DocumentDao()
        val document = Document(0, userID, roomID, seatID, content, "", false)
        return documentDao.add(document).toString()
    }

    @ResponseBody
    @GetMapping("/{roomID}/{seatID}/{docID}")
    fun getDoc(@PathVariable("roomID") roomID: Int, @PathVariable("seatID") seatID: Int, @PathVariable("docID") docID: Int): String {
        val documentDao = DocumentDao()
        val document = documentDao.get(docID)
        return Gson().toJson(document)
    }

    @ResponseBody
    @GetMapping("/{roomID}/{seatID}")
    fun getDocList(@PathVariable("roomID") roomID: Int, @PathVariable("seatID") seatID: Int): String {
        val documentDao = DocumentDao()
        val documentList = documentDao.getList()
        return Gson().toJson(documentList)
    }

    @PutMapping("/{roomID}/{seatID}/{docID}")
    fun editDoc(@PathVariable("roomID") roomID: Int, @PathVariable("seatID") seatID: Int, @PathVariable("docID") docID: Int, @RequestParam content: String) {
        val documentDao = DocumentDao()
        val document = Document(0, "", roomID, seatID, content, "", true)
        documentDao.update(document)
    }

    @DeleteMapping("/{roomID}/{seatID}/{docID}")
    fun deleteDoc(@PathVariable("roomID") roomID: Int, @PathVariable("seatID") seatID: Int, @PathVariable("docID") docID: Int){
        val documentDao = DocumentDao()
        documentDao.delete(docID)
    }

}
