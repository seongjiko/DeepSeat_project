package com.deepseat.ds.vo

class UserVO(
    var userID: String,
    var userPW: String,
    var salt: String,
    var nickname: String,
    var email: String,
    var verified: Boolean = false
)