package com.deepseat.server.DeepSeatServer.service

import com.deepseat.server.DeepSeatServer.dao.ApiKeyMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ApiKeyServiceImpl : ApiKeyService {

    @Autowired
    private lateinit var mapper: ApiKeyMapper

    override fun insertApiKey(apiKey: String) {
        mapper.insertApiKey(apiKey)
    }

    override fun authenticateApiKey(apiKey: String): String? {
        return mapper.authenticateApiKey(apiKey)
    }

}