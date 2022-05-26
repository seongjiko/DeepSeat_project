package com.deepseat.server.DeepSeatServer.service

interface ApiKeyService {

    public fun insertApiKey(apiKey: String)
    public fun authenticateApiKey(apiKey: String): String?

}