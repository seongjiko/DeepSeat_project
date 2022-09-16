package com.deepseat.server.DeepSeatServer

import org.apache.catalina.connector.Connector
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory

import org.springframework.boot.web.server.WebServerFactoryCustomizer
import org.springframework.context.annotation.Configuration


@SpringBootApplication
class DeepSeatServerApplication

fun main(args: Array<String>) {
	runApplication<DeepSeatServerApplication>(*args)
}

@Configuration
class TomcatWebServerCustomizer : WebServerFactoryCustomizer<TomcatServletWebServerFactory> {
	/**
	 * 톰캣에 옵션 추가.
	 *
	 * @param factory
	 */
	override fun customize(factory: TomcatServletWebServerFactory) {
		factory.addConnectorCustomizers(TomcatConnectorCustomizer { connector: Connector ->
			connector.setProperty("relaxedQueryChars", "<>[\\]^`{|}")
		})
	}
}