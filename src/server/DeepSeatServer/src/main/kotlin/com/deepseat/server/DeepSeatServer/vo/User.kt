package com.deepseat.server.DeepSeatServer.vo

import lombok.Getter
import lombok.Setter

@Getter
@Setter
class User(
    var userID: String,
    var userPW: String,
    var salt: String,
    var nickname: String,
    var email: String,
    var verified: Boolean = false
)