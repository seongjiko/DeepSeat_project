package com.deepseat.server.DeepSeatServer.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class DBConfig {

    @Value("\${spring.datasource.url}")
    val url: String? = null

    @Value("\${spring.datasource.driver-class-name}")
    val driverClassName: String? = null

    @Value("\${spring.datasource.username}")
    val username: String? = null

    @Value("\${spring.datasource.password}")
    val password: String? = null

}