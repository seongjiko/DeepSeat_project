package com.deepseat.server.DeepSeatServer.tool

import com.google.gson.Gson
import com.google.gson.JsonObject

class ResponseBody<T>(
    var responseCode: Int = 200,
    var message: String = "Success",
    var totalCnt: Int? = null,
    var hasMore: Boolean? = null,
    var page: Int? = null,
    var data: T? = null
)

class ResponseBodyBuilder<T> {

    private var responseBody = ResponseBody<T>()

    constructor()

    constructor(error: com.deepseat.server.DeepSeatServer.error.Error) : this(error.errorCode, error.message)

    constructor(responseCode: Int, message: String) {
        responseBody.responseCode = responseCode
        responseBody.message = message
    }

    fun responseCode(responseCode: Int): ResponseBodyBuilder<T> {
        responseBody.responseCode = responseCode
        return this
    }

    fun message(message: String): ResponseBodyBuilder<T> {
        responseBody.message = message
        return this
    }

    fun totalCnt(totalCount: Int): ResponseBodyBuilder<T> {
        responseBody.totalCnt = totalCount
        return this
    }

    fun hasMore(hasMore: Boolean = true): ResponseBodyBuilder<T> {
        responseBody.hasMore = hasMore
        return this
    }

    fun page(page: Int): ResponseBodyBuilder<T> {
        responseBody.page = page
        return this
    }

    fun data(data: T): ResponseBodyBuilder<T> {
        responseBody.data = data
        return this
    }

    override fun toString(): String {
        return Gson().toJson(responseBody)
    }
}