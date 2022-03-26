package com.deepseat.server.DeepSeatServer.model

class User(userID: String, userPW: String, nickname: String) {
    var userID: String
    var userPW: String
    var nickname: String

    init{
        this.userID = userID
        this.userPW = userPW
        this.nickname = nickname

    }
}