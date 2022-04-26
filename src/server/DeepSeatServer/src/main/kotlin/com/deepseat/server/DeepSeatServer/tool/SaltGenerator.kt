package com.deepseat.server.DeepSeatServer.tool

import kotlin.random.Random


class SaltGenerator {

    companion object {
        private val charPool: List<Char> =
            ('a'..'z') + ('A'..'Z') + ('0'..'9') + listOf<Char>('!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '_')

        fun generate(length: Int = 10): String {
            val builder = StringBuilder()

            for (i in 0 until length) {
                val index = Random.nextInt(0, charPool.size)
                builder.append(charPool[index])
            }

            return builder.toString()
        }
    }

}