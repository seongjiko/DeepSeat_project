//
//  Document.swift
//  DeepSeat
//
//  Created by Jisoo Woo on 2022/05/20.
//

import Foundation

class Document{
    var docID: Int
    var userID: String
    var nickname: String
    var roomName: String
    var seatName: String
    var content: String
    var wrote: String
    var edited: Bool
    var comments: Int
    var liked: Int
    var iLiked: Bool
    
    init(docID: Int, userID: String, nickname: String , roomName:String, seatName: String,
         content: String, wrote: String, edited: Bool, comments: Int, liked: Int, iLiked: Bool){
        self.docID = docID
        self.userID = userID
        self.content = content
        self.wrote = wrote
        self.edited = edited
        self.nickname = nickname
        self.roomName = roomName
        self.seatName = seatName
        self.comments = comments
        self.liked = liked
        self.iLiked = iLiked
    }
    
    
}

