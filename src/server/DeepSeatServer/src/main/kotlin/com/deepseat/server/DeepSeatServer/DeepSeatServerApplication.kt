package com.deepseat.server.DeepSeatServer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DeepSeatServerApplication

fun main(args: Array<String>) {
	runApplication<DeepSeatServerApplication>(*args)
}
