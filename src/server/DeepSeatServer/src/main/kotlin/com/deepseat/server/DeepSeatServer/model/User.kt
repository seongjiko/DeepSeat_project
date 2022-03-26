package com.deepseat.server.DeepSeatServer.model

class User(userID: String, userPW: String, salt: String, nickname: String) {
    var userID: String
    var userPW: String
    var salt: String
    var nickname: String

    init{
        this.userID = userID
        this.userPW = userPW
        this.salt = salt
        this.nickname = nickname
    }
}