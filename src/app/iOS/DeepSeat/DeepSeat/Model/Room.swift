//
//  Room.swift
//  DeepSeat
//
//  Created by Jisoo Woo on 2022/05/20.
//

import Foundation

class Room{
    var roomID: Int
    var roomName: String
    var apiKey: String
    
    init(roomID: Int, roomName: String, apiKey: String){
        self.roomID = roomID
        self.roomName = roomName
        self.apiKey = apiKey
    }
}
