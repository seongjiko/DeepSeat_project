//
//  Liked.swift
//  DeepSeat
//
//  Created by Jisoo Woo on 2022/05/20.
//

import Foundation

class Liked{
    var likedID: Int
    var userID: String
    var docID: Int?
    var commentID: Int?
    
    init(likedID: Int, userID: String, docID: Int?, commentID: Int?){
        self.likedID = likedID
        self.userID = userID
        self.docID = docID
        self.commentID = commentID
    }
    
}
