package com.deepseat.server.DeepSeatServer.error

abstract class Errors {
    companion object {

        // Success
        val success = Error(200, "Success")

        // DB Errors (2000)
        abstract class DatabaseError {
            companion object {
                // DB Insert Failed
                val dbInsertFailure = Error(2001, "Database Insertion Failed")
                val dbDeleteFailure = Error(2001, "Database Deletion Failed")
                val dbSelectFailure = Error(2001, "Database Selection Failed")
                val dbUpdateFailure = Error(2001, "Database Update Failed")
                val notExists = Error(2001, "Not Exists")
            }
        }

        // User Errors (4000)
        abstract class UserError {
            companion object {
                val notRegistered = Error(4001, "User ID Not Registered")
                val wrongPassword = Error(4002, "Wrong Password")
                val notSignedIn = Error(4003, "Not Logged In")
                val notAuthorized = Error(4003, "Not Authorized")
            }
        }

        // Registration Errors (5000)
        abstract class RegistrationError {
            companion object {
                val registerPWCheckNotMatch = Error(5001, "Password Check Failed")
            }
        }
    }
}