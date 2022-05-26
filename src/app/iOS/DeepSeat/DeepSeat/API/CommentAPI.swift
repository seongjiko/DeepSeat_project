//
//  CommentAPI.swift
//  DeepSeat
//
//  Created by Jisoo Woo on 2022/05/23.
//

import Foundation
import SwiftHTTP

class CommentAPI{
    func getComments(docID:Int,handler:(([Comment]?) -> Void)?){
        var comments: [Comment] = []
        var array : Array = [Dictionary<String, Any>]()
        var result: Dictionary<String, Any> = [String : Any]()
        HTTP.GET("http://soc06212.iptime.org:8080/comment/\(docID)"){
            response in
            var dicData : Dictionary<String, Any> = [String : Any]()
            
            do {

                dicData = try JSONSerialization.jsonObject(with: response.data, options: []) as! [String:Any]
                
            } catch {
                print(error.localizedDescription)
            }
            array  = dicData["data"] as! [[String:Any]]
            
            for i in 0..<array.count{
                result = array[i]
                let commentID = result["commentID"] as! Int
                let userID = result["userID"] as! String
                let docID = result["docID"] as! Int
                let content = result["content"] as! String
                let wrote = result["wrote"] as! String
                let edited = result["edited"] as! Bool

                let comment = Comment(commentID: commentID, userID: userID, docID: docID, content: content, wrote: wrote, edited: edited)
                comments.append(comment)
            }
            if let handler = handler {
                handler(comments)
            }
        }
        
    }
    func writeComments(docID:Int, comment: String, session: String,handler:((Int?) -> Void)?){
        HTTP.POST("http://soc06212.iptime.org:8080/comment/\(docID)",parameters: ["content":comment],headers: ["Set-Cookie":session]){
            response in
            var dicData : Dictionary<String, Any> = [String : Any]()
            do {

                dicData = try JSONSerialization.jsonObject(with: response.data, options: []) as! [String:Any]
                
            } catch {
                print(error.localizedDescription)
            }
            
            if let handler = handler {
                handler(dicData["responseCode"] as? Int)
            }
        }
    }
    
    func deleteComment(commnetID: Int, session: String,handler:((Int?) -> Void)?){
        HTTP.DELETE("http://soc06212.iptime.org:8080/comment/\(commnetID)",headers: ["Set-Cookie":session]){
            response in
            var dicData : Dictionary<String, Any> = [String : Any]()
            do {

                dicData = try JSONSerialization.jsonObject(with: response.data, options: []) as! [String:Any]
                
            } catch {
                print(error.localizedDescription)
            }
            
            if let handler = handler {
                handler(dicData["responseCode"] as? Int)
            }
        }
    }
}
