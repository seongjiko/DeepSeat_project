//
//  Comment.swift
//  DeepSeat
//
//  Created by Jisoo Woo on 2022/05/20.
//

import Foundation

class Comment{
    var commentID: Int
    var userID: String
    var docID: Int
    var content: String
    var wrote: String
    var edited: Bool
    
    init(commentID: Int, userID: String, docID: Int, content: String, wrote:String, edited: Bool){
        self.commentID = commentID
        self.userID = userID
        self.docID = docID
        self.content = content
        self.wrote = wrote
        self.edited = edited
    }
}
