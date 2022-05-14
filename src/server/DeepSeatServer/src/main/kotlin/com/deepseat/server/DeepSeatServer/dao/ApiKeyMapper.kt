package com.deepseat.server.DeepSeatServer.dao

import org.springframework.stereotype.Repository

@Repository
interface ApiKeyMapper {

    public fun insertApiKey(apiKey: String)
    public fun authenticateApiKey(apiKey: String): String?

}