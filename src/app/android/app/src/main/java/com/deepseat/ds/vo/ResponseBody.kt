package com.deepseat.ds.vo

data class ResponseBody<T>(
    var responseCode: Int,
    var message: String,
    var totalCnt: Int? = null,
    var hasMore: Boolean? = null,
    var page: Int? = null,
    var data: T? = null
)