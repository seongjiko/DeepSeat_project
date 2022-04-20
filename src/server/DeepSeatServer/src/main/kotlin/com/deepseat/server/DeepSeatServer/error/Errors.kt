package com.deepseat.server.DeepSeatServer.error

abstract class Errors {
    companion object {

        // DB Errors (2000)
        abstract class DatabaseError {
            companion object {
                // DB Insert Failed
                val dbInsertFailure = Error(2001, "Database Insertion Failed")
            }
        }

        // User Errors (4000)
        abstract class UserError {
            companion object {
                val notRegistered = Error(4001, "User ID Not Registered")
                val wrongPassword = Error(4002, "Wrong Password")
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