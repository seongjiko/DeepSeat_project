package com.deepseat.server.DeepSeatServer.tool

import java.security.MessageDigest

class PasswordTool {

    companion object {

        fun encryptPassword(userPW: String, salt: String): String {
            val messageDigest = MessageDigest.getInstance("SHA-256")

            var result = userPW + salt

            for (i in 0 until 5) {
                messageDigest.update(result.toByteArray())
                result = bytesToHex(messageDigest.digest())
            }

            return result
        }

        private fun bytesToHex(bytes: ByteArray): String {
            val builder = StringBuilder()
            for (b in bytes) {
                builder.append(String.format("%02x", b))
            }
            return builder.toString()
        }

    }

}