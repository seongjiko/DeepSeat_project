package com.deepseat.server.DeepSeatServer.tool

import com.google.gson.Gson
import com.google.gson.JsonObject

class ResponseBody {
    companion object {
        val RESPONSE_CODE = "responseCode"
        val MESSAGE = "message"
        val TOTAL_CNT = "totalCnt"
        val HAS_MORE = "hasMore"
        val PAGE = "page"
        val DATA = "data"
    }
}

class ResponseBodyBuilder<T> {

    private var responseCode: Int = 200
    private var message: String = "Success"
    private var totalCnt: Int? = null
    private var hasMore: Boolean? = null
    private var page: Int? = null
    private var data: T? = null

    constructor()

    constructor(error: com.deepseat.server.DeepSeatServer.error.Error) : this(error.errorCode, error.message)

    constructor(responseCode: Int, message: String) {
        this.responseCode = responseCode
        this.message = message
    }

    fun responseCode(responseCode: Int): ResponseBodyBuilder<T> {
        this.responseCode = responseCode
        return this
    }

    fun message(message: String): ResponseBodyBuilder<T> {
        this.message = message
        return this
    }

    fun totalCnt(totalCount: Int): ResponseBodyBuilder<T> {
        this.totalCnt = totalCount
        return this
    }

    fun hasMore(hasMore: Boolean = true): ResponseBodyBuilder<T> {
        this.hasMore = hasMore
        return this
    }

    fun page(page: Int): ResponseBodyBuilder<T> {
        this.page = page
        return this
    }

    fun data(data: T): ResponseBodyBuilder<T> {
        this.data = data
        return this
    }

    override fun toString(): String {
        val json = JsonObject()
        json.addProperty(ResponseBody.RESPONSE_CODE, this.responseCode)
        json.addProperty(ResponseBody.MESSAGE, this.message)

        this.totalCnt?.let {
            json.addProperty(ResponseBody.TOTAL_CNT, it)
        }

        this.hasMore?.let {
            json.addProperty(ResponseBody.HAS_MORE, it)
        }

        this.page?.let {
            json.addProperty(ResponseBody.PAGE, it)
        }

        this.data?.let {
            json.add(ResponseBody.DATA, Gson().toJsonTree(it))
        }

        return json.toString()
    }
}