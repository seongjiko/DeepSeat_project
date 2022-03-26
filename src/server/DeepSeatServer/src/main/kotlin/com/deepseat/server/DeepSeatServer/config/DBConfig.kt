package com.deepseat.server.DeepSeatServer.config

import lombok.Getter
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Getter
@Component
class DBConfig {

    companion object {
        private var instance: DBConfig? = null

        fun getInstance(): DBConfig {
            if (instance == null) {
                instance = DBConfig()
            }
            return instance as DBConfig
        }
    }

    @Value("\${spring.datasource.url}")
    lateinit var url: String

    @Value("\${spring.datasource.driver-class-name}")
    lateinit var driverClassName: String

    @Value("\${spring.datasource.username}")
    lateinit var username: String

    @Value("\${spring.datasource.password}")
    lateinit var password: String

}